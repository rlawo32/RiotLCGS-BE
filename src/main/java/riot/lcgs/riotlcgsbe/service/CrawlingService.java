package riot.lcgs.riotlcgsbe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Patch_Note;
import riot.lcgs.riotlcgsbe.jpa.repository.LCG_Patch_Note_Repository;
import riot.lcgs.riotlcgsbe.util.EmailTool;
import riot.lcgs.riotlcgsbe.web.dto.CommonResponseDto;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.*;

import static riot.lcgs.riotlcgsbe.util.DateTimeTool.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrawlingService {
    private final OkHttpClient client;

    private final LCG_Patch_Note_Repository lcgPatchNoteRepository;

    private String searchUrl;

    private static final Set<String> IGNORE_HEADERS = Set.of(
            "aram",
            "arena",
            "clash",
            "classic",
            "tft"
    );

    private static final Set<String> SKIP_START_HEADERS = Set.of(
            "items",
            "runes",
            "systems"
    );

    private static boolean isTargetSection(String h2Id) {
        return h2Id.contains("items") || h2Id.contains("runes") || h2Id.contains("systems");
    }

    public Map<String, String> patchNoteCrawling(String version) {
        Map<String, String> result = new LinkedHashMap<>();
        String htmlResponse = "";
        try {
            String[] tmpArr = version.split("-");
            version = tmpArr[0] + "-" + Integer.parseInt(tmpArr[1]);

            String fullUrl = "https://www.leagueoflegends.com/ko-kr/news/game-updates/league-of-legends-patch-" + version +"-notes/";

            Request searchRequest = new Request.Builder()
                    .url(fullUrl)
                    .method("GET", null)
                    .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                    .addHeader("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                    .addHeader("Referer", "https://www.google.com/")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/151.0.0.0 Safari/537.36")
                    .addHeader("Sec-Fetch-Dest", "document")
                    .addHeader("Sec-Fetch-Mode", "navigate")
                    .addHeader("Sec-Fetch-Site", "same-origin")
                    .build();

            try (Response response = client.newCall(searchRequest).execute()) {
                if (response.code() == 404) {
                    String fallbackUrl = "https://www.leagueoflegends.com/ko-kr/news/game-updates/patch-" + version + "-notes/";

                    Request fallbackRequest = searchRequest.newBuilder().url(fallbackUrl).build();

                    try (Response fallbackResponse = client.newCall(fallbackRequest).execute()) {
                        if (!fallbackResponse.isSuccessful()) {
                            log.error("서버 에러: {} - {}", fallbackResponse.code(), fallbackResponse.message());
                        } else {
                            htmlResponse = (fallbackResponse.body() != null) ? fallbackResponse.body().string() : "";
                        }
                        searchUrl = fallbackUrl;
                    }
                } else {
                    if (!response.isSuccessful()) {
                        log.error("서버 에러: {} - {}", response.code(), response.message());
                    } else {
                        htmlResponse = (response.body() != null) ? response.body().string() : "";
                    }
                    searchUrl = fullUrl;
                }
            }
        } catch (SocketTimeoutException e) {
            log.error("타임아웃 발생: {}", e.getMessage());
        } catch (IOException e) {
            log.error("네트워크 연결 실패: {}", e.getMessage());
        }

        try {
            if (htmlResponse.isBlank()) {
                log.error("htmlResponse 빈 응답 : {}", htmlResponse);
                return result;
            }

            Document doc = Jsoup.parse(htmlResponse);

            Elements headers = doc.select("#patch-notes-container .header-primary");

            boolean restricted = false;
            int patchNoteSeq = 0;

            for (Element header : headers) {
                Element h2 = header.selectFirst("h2");

                if (h2 == null) { continue; }

                String h2Id = h2.id().toLowerCase();

                if (IGNORE_HEADERS.stream().anyMatch(h2Id::contains)) { continue; }

                boolean isTarget = isTargetSection(h2Id);
                boolean isBugfix = h2Id.contains("bugfix");
                boolean isSkin = h2Id.contains("skin");

                if (isBugfix) { restricted = false; }

                if (restricted && !isBugfix && !isSkin && !isTarget) {
                    continue;
                }

                StringBuilder html = new StringBuilder();

                Element current = header.nextElementSibling();

                if (h2Id.contains("skins")) {
                    if (current != null && current.is("div")) {
                        html.append(current.outerHtml());
                    }
                } else {
                    while (current != null) {
                        if (current.is(".header-primary")) {
                            break;
                        }
                        html.append(current.outerHtml());
                        current = current.nextElementSibling();
                    }
                }

                result.put( String.format("%02d#%s", ++patchNoteSeq, h2Id), html.toString());

                if (isTarget) {
                    restricted = true;
                }
            }
        } catch (Exception e) {
            log.error("알수없는 에러: {}", e.getMessage());
        }

        return result;
    }

    @Transactional
    public CommonResponseDto<String> LCGPatchNoteSave(String version) {

        try {
            String now = dateTimeCurrent().getData();

            Map<String, Object> startInfo = new HashMap<>();
            startInfo.put("version", version);
            EmailTool.sendMessage_Image("PatchNote", startInfo);

            Map<String, String> patchNoteData = patchNoteCrawling(version);

            if(patchNoteData.isEmpty()) {
                return CommonResponseDto.setFailed("PatchNote 저장 실패!");
            } else {
                patchNoteData.forEach((section, html) -> {
                    lcgPatchNoteRepository.save(LCG_Patch_Note.builder()
                            .lcgPatchVersion(version)
                            .lcgPatchSection(section)
                            .lcgPatchHtml(html)
                            .lcgPatchUrl(searchUrl)
                            .lcgCreatedDate(now)
                            .build());
                });

                return CommonResponseDto.setSuccess("PatchNote 저장 완료!", "Y");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Database Insert Failed !");
        }
    }
}
