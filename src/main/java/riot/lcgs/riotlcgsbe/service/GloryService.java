package riot.lcgs.riotlcgsbe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Info_Champion;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Player_Data;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Player_Glory;
import riot.lcgs.riotlcgsbe.jpa.repository.*;
import riot.lcgs.riotlcgsbe.util.GloryTool;

import java.math.BigDecimal;
import java.util.*;

import static riot.lcgs.riotlcgsbe.util.DateTimeTool.dateTimeCurrent;

@RequiredArgsConstructor
@Service
public class GloryService {

    private final LCG_Match_Info_Repository lcgMatchInfoRepository;
    private final LCG_Match_Sub_Repository lcgMatchSubRepository;
    private final LCG_Player_Data_Repository lcgPlayerDataRepository;
    private final LCG_Player_Statistics_Repository lcgPlayerStatisticsRepository;
    private final LCG_Player_Ranking_Repository lcgPlayerRankingRepository;
    private final LCG_Player_Position_Repository lcgPlayerPositionRepository;
    private final LCG_Player_Champion_Repository lcgPlayerChampionRepository;
    private final LCG_Player_Glory_Repository lcgPlayerGloryRepository;
    private final LCG_Info_Maximum_Repository lcgInfoMaximumRepository;
    private final LCG_Info_Champion_Repository lcgInfoChampionRepository;

    // gloryGrade
    // S등급 : 5 , A등급 : 4 , B등급 : 3 , C등급 : 2 , D등급 : 1
    // ***** gloryTitle : gloryId (gloryGrade) - gloryInfo *****
    // Unique True
    // 바론 슬레이어 : baron (3) - 전체 플레이 중 바론 평균 1등
    // 드래곤 슬레이어 : dragon (3) - 전체 플레이 중 드래곤 평균 1등
    // 유일무이 : only (5) - 전체 플레이어 중 유일
    // 승리자 : winner (4) - 전체 플레이 70% 기준 승률 1등
    // CS 장인 : cs (3) - 전체 플레이 중 CS 평균 1등
    // 철거반장 : demolisher (3) - 타워 + 억제기 철거 개수, 타워 피해 데미지 1등
    // 골드 부자 : gold (3) - 전체 플레이 중 골드 평균 1등
    // 킬링머신 : kill (3), 데스 왕 : death (2), 어시스트 왕 : assist (3) - 각 집계 1등 (평균x)
    // 와드 사냥꾼 : ward (3) - 전체 플레이 중 와드 파괴 평균 1등
    // 핑와 애호가 : pink (3) - 비전 와드 구매 1등
    // CC성애자 : crowd (3) - 전체 플레이 중 CC기 시간 평균 1등
    // 멀티킬 기계 : multi (4) - 전체 플레이 중 멀티킬 스코어 평균 1등
    // TOPKING : top (4), JUGKING : jug (4), MIDKING : mid (4), ADCKING : adc (4), SUPKING : sup (4) - 각 라인 승률 1등
    // Unique False
    // 개근상 : attendance (2) - 총 판수 기준 참가 횟수 100%
    // 펜타킬 기계 : penta (4) - 펜타킬 유저
    // 절대자 : perfect (5) - 동일 챔피언 플레이 5회 이상 승률 100%
    // 장인 : master (4) - 동일 챔피언 플레이 50회 이상, 승률 50% 이상

    public void LCGPlayerGlorySave(String puuid, String nickname, String gloryId,
                                   String gloryTitle, String gloryInfo, int gloryGrade,
                                   String gloryUnique, String gloryActive, String gloryHide) {
        String now = dateTimeCurrent().getData();

        lcgPlayerGloryRepository.save(LCG_Player_Glory.builder()
                .lcgSummonerPuuid(puuid)
                .lcgSummonerNickname(nickname)
                .lcgGloryId(gloryId)
                .lcgGloryTitle(gloryTitle)
                .lcgGloryInfo(gloryInfo)
                .lcgGloryGrade(gloryGrade)
                .lcgGloryUnique(gloryUnique)
                .lcgGloryActive(gloryActive)
                .lcgGloryHide(gloryHide)
                .lcgUpdateDate(now)
                .build());
    }

    public String LCGPlayerDataSelectNickname(String puuid) {
        if(puuid.isEmpty()) return "";
        LCG_Player_Data lcgPlayerData = lcgPlayerDataRepository.findById(puuid)
                .orElseThrow(() -> new IllegalArgumentException("해당 플레이어가 없습니다. puuid : " + puuid));
        return lcgPlayerData.getLcgSummonerNickname();
    }

    public boolean LCGPlayerGloryDuplicationCheck(String puuid, String gloryId) {
        if (puuid == null || puuid.isBlank()) return false;
        return !lcgPlayerGloryRepository.existsLCG_Player_GloryByLcgSummonerPuuidAndLcgGloryIdAndLcgGloryActive(puuid, gloryId, "Y");
    }

    public boolean LCGPlayerGloryUniqueCheck(String gloryUnique) {
        return "Y".equals(gloryUnique);
    }

    public void LCGPlayerGloryActiveUpdate(String gloryId) {
        String now = dateTimeCurrent().getData();
        lcgPlayerGloryRepository
                .findByLcgGloryIdAndLcgGloryUniqueAndLcgGloryActive(gloryId, "Y", "Y")
                .ifPresent(glory -> glory.playerGloryActiveUpdate(now));
    }

    public void LCGPlayerGloryInfoUpdate(String puuid, String gloryId, String gloryInfo) {
        String now = dateTimeCurrent().getData();
        LCG_Player_Glory lcgPlayerGlory = lcgPlayerGloryRepository
                .findByLcgSummonerPuuidAndLcgGloryIdAndLcgGloryActive(puuid, gloryId, "Y")
                .orElse(null);

        if (lcgPlayerGlory != null) {
            lcgPlayerGlory.playerGloryInfoUpdate(gloryInfo, now);
        }
    }

    public void LCGPlayerGloryTitleUpdate(String puuid, String gloryId, String gloryTitle) {
        String now = dateTimeCurrent().getData();
        LCG_Player_Glory lcgPlayerGlory = lcgPlayerGloryRepository
                .findByLcgSummonerPuuidAndLcgGloryIdAndLcgGloryActive(puuid, gloryId, "Y")
                .orElse(null);

        if (lcgPlayerGlory != null) {
            lcgPlayerGlory.playerGloryTitleUpdate(gloryTitle, now);
        }
    }

    public Map<String, List<Map<String, Object>>> LCGPlayerPositionSelectMax() {

        Map<String, List<Map<String, Object>>> result = new HashMap<>();

        try {
            List<Map<String, Object>> listLane = lcgPlayerPositionRepository.findAllLaneRate();

            List<Map<String, Object>> listLaneTop = new ArrayList<>(listLane);
            List<Map<String, Object>> listLaneJug = new ArrayList<>(listLane);
            List<Map<String, Object>> listLaneMid = new ArrayList<>(listLane);
            List<Map<String, Object>> listLaneAdc = new ArrayList<>(listLane);
            List<Map<String, Object>> listLaneSup = new ArrayList<>(listLane);

            listLaneTop.removeIf(map -> ((Long) map.get("play_top")) <= 20);
            listLaneTop.sort(Comparator.comparing((Map<String, Object> map) -> (Double)map.get("rate_top")).reversed());
            listLaneJug.removeIf(map -> ((Long) map.get("play_jug")) <= 20);
            listLaneJug.sort(Comparator.comparing((Map<String, Object> map) -> (Double)map.get("rate_jug")).reversed());
            listLaneMid.removeIf(map -> ((Long) map.get("play_mid")) <= 20);
            listLaneMid.sort(Comparator.comparing((Map<String, Object> map) -> (Double)map.get("rate_mid")).reversed());
            listLaneAdc.removeIf(map -> ((Long) map.get("play_adc")) <= 20);
            listLaneAdc.sort(Comparator.comparing((Map<String, Object> map) -> (Double)map.get("rate_adc")).reversed());
            listLaneSup.removeIf(map -> ((Long) map.get("play_sup")) <= 20);
            listLaneSup.sort(Comparator.comparing((Map<String, Object> map) -> (Double)map.get("rate_sup")).reversed());

            result.put("top", listLaneTop);
            result.put("jug", listLaneJug);
            result.put("mid", listLaneMid);
            result.put("adc", listLaneAdc);
            result.put("sup", listLaneSup);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public void LCGPlayerGloryLaneKing() {

        try {
            // 라인별 KING
            Map<String, List<Map<String, Object>>> listLane = LCGPlayerPositionSelectMax();

            String gloryId = "";
            String puuid = "";
            String nickname = "";
            String info = "";

            gloryId = "top";
            List<Map<String, Object>> listLaneTop = listLane.get(gloryId);
            GloryTool gloryTop = GloryTool.findById(gloryId);
            puuid = (String) listLaneTop.get(0).get("puuid");
            nickname = LCGPlayerDataSelectNickname(puuid);
            info = "승률 " + listLaneTop.get(0).get("rate_top") + "%";
            if(LCGPlayerGloryDuplicationCheck(puuid, gloryId)) {
                if(LCGPlayerGloryUniqueCheck(gloryTop.getUnique())) {
                    LCGPlayerGloryActiveUpdate(gloryId);
                }
                LCGPlayerGlorySave(puuid, nickname, gloryId, gloryTop.getTitle(), info, gloryTop.getGrade(), gloryTop.getUnique(), "Y", "N");
            } else {
                LCGPlayerGloryInfoUpdate(puuid, gloryId, info);
            }

            gloryId = "jug";
            List<Map<String, Object>> listLaneJug = listLane.get(gloryId);
            GloryTool gloryJug = GloryTool.findById(gloryId);
            puuid = (String) listLaneJug.get(0).get("puuid");
            nickname = LCGPlayerDataSelectNickname(puuid);
            info = "승률 " + listLaneJug.get(0).get("rate_jug") + "%";
            if(LCGPlayerGloryDuplicationCheck(puuid, gloryId)) {
                if(LCGPlayerGloryUniqueCheck(gloryJug.getUnique())) {
                    LCGPlayerGloryActiveUpdate(gloryId);
                }
                LCGPlayerGlorySave(puuid, nickname, gloryId, gloryJug.getTitle(), info, gloryJug.getGrade(), gloryJug.getUnique(), "Y", "N");
            } else {
                LCGPlayerGloryInfoUpdate(puuid, gloryId, info);
            }

            gloryId = "mid";
            List<Map<String, Object>> listLaneMid = listLane.get(gloryId);
            GloryTool gloryMid = GloryTool.findById(gloryId);
            puuid = (String) listLaneMid.get(0).get("puuid");
            nickname = LCGPlayerDataSelectNickname(puuid);
            info = "승률 " + listLaneMid.get(0).get("rate_mid") + "%";
            if(LCGPlayerGloryDuplicationCheck(puuid, gloryId)) {
                if(LCGPlayerGloryUniqueCheck(gloryMid.getUnique())) {
                    LCGPlayerGloryActiveUpdate(gloryId);
                }
                LCGPlayerGlorySave(puuid, nickname, gloryId, gloryMid.getTitle(), info, gloryMid.getGrade(), gloryMid.getUnique(), "Y", "N");
            } else {
                LCGPlayerGloryInfoUpdate(puuid, gloryId, info);
            }

            gloryId = "adc";
            List<Map<String, Object>> listLaneAdc = listLane.get(gloryId);
            GloryTool gloryAdc = GloryTool.findById(gloryId);
            puuid = (String) listLaneAdc.get(0).get("puuid");
            nickname = LCGPlayerDataSelectNickname(puuid);
            info = "승률 " + listLaneAdc.get(0).get("rate_adc") + "%";
            if(LCGPlayerGloryDuplicationCheck(puuid, gloryId)) {
                if(LCGPlayerGloryUniqueCheck(gloryAdc.getUnique())) {
                    LCGPlayerGloryActiveUpdate(gloryId);
                }
                LCGPlayerGlorySave(puuid, nickname, gloryId, gloryAdc.getTitle(), info, gloryAdc.getGrade(), gloryAdc.getUnique(), "Y", "N");
            } else {
                LCGPlayerGloryInfoUpdate(puuid, gloryId, info);
            }

            gloryId = "sup";
            List<Map<String, Object>> listLaneSup = listLane.get(gloryId);
            GloryTool glorySup = GloryTool.findById(gloryId);
            puuid = (String) listLaneSup.get(0).get("puuid");
            nickname = LCGPlayerDataSelectNickname(puuid);
            info = "승률 " + listLaneSup.get(0).get("rate_sup") + "%";
            if(LCGPlayerGloryDuplicationCheck(puuid, gloryId)) {
                if(LCGPlayerGloryUniqueCheck(glorySup.getUnique())) {
                    LCGPlayerGloryActiveUpdate(gloryId);
                }
                LCGPlayerGlorySave(puuid, nickname, gloryId, glorySup.getTitle(), info, glorySup.getGrade(), glorySup.getUnique(), "Y", "N");
            } else {
                LCGPlayerGloryInfoUpdate(puuid, gloryId, info);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void LCGPlayerGloryPerfect() {

        try {
            // 절대자
            List<Map<String, Object>> listPerfect = lcgPlayerChampionRepository.findPerfectWinner();

            String gloryId = "perfect";
            GloryTool gloryPerfect = GloryTool.findById(gloryId);

            for(Map<String, Object> map : listPerfect) {
                String title = gloryPerfect.getTitle();
                if(listPerfect.size() == 1) { title = "유일무이 절대자"; }
                String puuid = (String) map.get("puuid");
                String champion = (String) map.get("champion");
                Long play = (Long) map.get("play");
                String info = champion + "-" + play + "회 승률 100%";

                String nickname = LCGPlayerDataSelectNickname(puuid);

                if(LCGPlayerGloryDuplicationCheck(puuid, gloryId)) {
                    if(LCGPlayerGloryUniqueCheck(gloryPerfect.getUnique())) {
                        LCGPlayerGloryActiveUpdate(gloryId);
                    }
                    LCGPlayerGlorySave(puuid, nickname, gloryId, title, info, gloryPerfect.getGrade(), gloryPerfect.getUnique(), "Y", "N");
                } else {
                    LCGPlayerGloryInfoUpdate(puuid, gloryId, info);
                    LCGPlayerGloryTitleUpdate(puuid, gloryId, title);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void LCGPlayerGloryWinningRate() {

        try {
            Long gameCount = lcgMatchInfoRepository.count();
            List<Map<String, Object>> listWinningRate = lcgPlayerStatisticsRepository.findByAllWinningRate();
            listWinningRate.removeIf(map -> ((Number) map.get("countPlay")).intValue() < (gameCount/2));
            listWinningRate.sort(Comparator.comparing(
                            map -> ((Number) map.get("grade")).doubleValue(),
                            Comparator.reverseOrder()));

            String gloryId = "winner";
            GloryTool gloryWinner = GloryTool.findById(gloryId);

            if(!listWinningRate.isEmpty()) {
                Map<String, Object> map = listWinningRate.get(0);
                String title = gloryWinner.getTitle();
                String puuid = (String) map.get("puuid");
                Long play = (Long) map.get("countPlay");
                double grade = (double) map.get("grade");
                String info = play + "판, 승률 " + grade + "%";

                String nickname = LCGPlayerDataSelectNickname(puuid);

                if(LCGPlayerGloryDuplicationCheck(puuid, gloryId)) {
                    if(LCGPlayerGloryUniqueCheck(gloryWinner.getUnique())) {
                        LCGPlayerGloryActiveUpdate(gloryId);
                    }
                    LCGPlayerGlorySave(puuid, nickname, gloryId, title, info, gloryWinner.getGrade(), gloryWinner.getUnique(), "Y", "N");
                } else {
                    LCGPlayerGloryInfoUpdate(puuid, gloryId, info);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void LCGPlayerGloryAttendanceRate() {

        try {
            Long gameCount = lcgMatchInfoRepository.count();
            List<Map<String, Object>> listAttendanceRate = lcgPlayerStatisticsRepository.findByAllAttendanceRate(gameCount);

            String gloryId = "attendance";
            GloryTool gloryAttendance = GloryTool.findById(gloryId);

            for(Map<String, Object> map : listAttendanceRate) {
                String title = gloryAttendance.getTitle();
                if(listAttendanceRate.size() == 1) { title = "유일무이 개근상"; }
                String puuid = (String) map.get("puuid");
                String nickname = (String) map.get("nickname");
                String info = "출석률 100%";

                if(LCGPlayerGloryDuplicationCheck(puuid, gloryId)) {
                    if(LCGPlayerGloryUniqueCheck(gloryAttendance.getUnique())) {
                        LCGPlayerGloryActiveUpdate(gloryId);
                    }
                    LCGPlayerGlorySave(puuid, nickname, gloryId, title, info, gloryAttendance.getGrade(), gloryAttendance.getUnique(), "Y", "N");
                } else {
                    LCGPlayerGloryInfoUpdate(puuid, gloryId, info);
                    LCGPlayerGloryTitleUpdate(puuid, gloryId, title);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void LCGPlayerGloryPentakill() {

        try {
            List<Map<String, Object>> listPentakill = lcgPlayerStatisticsRepository.findByAllPentaKill();

            String gloryId = "penta";
            GloryTool gloryPenta = GloryTool.findById(gloryId);

            for(Map<String, Object> map : listPentakill) {
                String title = gloryPenta.getTitle();
                if(listPentakill.size() == 1) { title = "유일무이 펜타킬"; }
                String puuid = (String) map.get("puuid");
                String nickname = (String) map.get("nickname");
                Long pentakill = (Long) map.get("pentakill");
                String info = "펜타킬 " + pentakill + "회";

                if(LCGPlayerGloryDuplicationCheck(puuid, gloryId)) {
                    if(LCGPlayerGloryUniqueCheck(gloryPenta.getUnique())) {
                        LCGPlayerGloryActiveUpdate(gloryId);
                    }
                    LCGPlayerGlorySave(puuid, nickname, gloryId, title, info, gloryPenta.getGrade(), gloryPenta.getUnique(), "Y", "N");
                } else {
                    LCGPlayerGloryInfoUpdate(puuid, gloryId, info);
                    LCGPlayerGloryTitleUpdate(puuid, gloryId, title);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void LCGPlayerGloryMaster() {

        try {
            List<Map<String, Object>> listMaster = lcgPlayerChampionRepository.findChampionMaster();

            String gloryId = "master";
            GloryTool gloryMaster = GloryTool.findById(gloryId);

            for(Map<String, Object> map : listMaster) {
                String title = gloryMaster.getTitle();
                String puuid = (String) map.get("puuid");
                String nickname = (String) map.get("nickname");
                String champion = (String) map.get("champion");
                LCG_Info_Champion lcgInfoChampion = lcgInfoChampionRepository
                        .findByLcgChampionName(champion)
                        .orElse(null);
                String championKo = lcgInfoChampion.getLcgChampionNameKo();
                title = championKo + " " + title;
                Long play = (Long) map.get("play");
                double rate = (double) map.get("rate");
                String info = play + "판, 승률" + rate + "%";

                boolean existsCheck = lcgPlayerGloryRepository.existsByLcgSummonerPuuidAndLcgGloryIdAndLcgGloryActiveAndLcgGloryTitleContaining(puuid, gloryId, "Y", championKo);

                if(!existsCheck) {
                    if(LCGPlayerGloryUniqueCheck(gloryMaster.getUnique())) {
                        LCGPlayerGloryActiveUpdate(gloryId);
                    }
                    LCGPlayerGlorySave(puuid, nickname, gloryId, title, info, gloryMaster.getGrade(), gloryMaster.getUnique(), "Y", "N");
                } else {
                    LCGPlayerGloryInfoUpdate(puuid, gloryId, info);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void LCGPlayerGloryStatisticsTopRank() {

        try {
            List<Map<String, Object>> listTopRank = lcgPlayerStatisticsRepository.findAllTopRank();

            for(Map<String, Object> map : listTopRank) {
                String gloryId = (String) map.get("title");
                GloryTool gloryTopRank = GloryTool.findById(gloryId);
                String title = gloryTopRank.getTitle();
                String puuid = (String) map.get("puuid");
                String nickname = (String) map.get("nickname");
                String info = "";

                if(gloryId.equals("kill")) {
                    int val = ((BigDecimal) map.get("value")).intValue();
                    info = "총 누적 " + val + "킬 달성!";
                } else if (gloryId.equals("death")) {
                    int val = ((BigDecimal) map.get("value")).intValue();
                    info = "총 누적 " + val + "데스 달성!";
                } else if (gloryId.equals("assist")) {
                    int val = ((BigDecimal) map.get("value")).intValue();
                    info = "총 누적 " + val + "어시 달성!";
                } else if (gloryId.equals("cs")) {
                    double val = ((BigDecimal) map.get("value")).doubleValue();
                    info = "평균 CS " + val + "개";
                } else if (gloryId.equals("gold")) {
                    int val = ((BigDecimal) map.get("value")).intValue();
                    info = "평균 골드 " + val + "원";
                } else if (gloryId.equals("ward")) {
                    double val = ((BigDecimal) map.get("value")).doubleValue();
                    info = "평균 와드킬 " + val + "개";
                } else if (gloryId.equals("crowd")) {
                    double val = ((BigDecimal) map.get("value")).doubleValue();
                    info = "평균 CC시간 " + val + "초";
                } else if (gloryId.equals("multi")) {
                    int val = ((BigDecimal) map.get("value")).intValue();
                    info = "멀티킬 스코어 " + val + "점 달성!";
                } else if (gloryId.equals("pink")) {
                    int val = ((BigDecimal) map.get("value")).intValue();
                    info = "총 누적 " + val + "개 구매";
                } else if (gloryId.equals("demolisher")) {
                    int val = ((BigDecimal) map.get("value")).intValue();
                    info = "총 누적 " + val + "개 철거";
                } else if (gloryId.equals("dragon")) {
                    int val = ((BigDecimal) map.get("value")).intValue();
                    info = "총 누적 " + val + "마리 처치";
                } else if (gloryId.equals("baron")) {
                    int val = ((BigDecimal) map.get("value")).intValue();
                    info = "총 누적 " + val + "마리 처치";
                }

                if(LCGPlayerGloryDuplicationCheck(puuid, gloryId)) {
                    if(LCGPlayerGloryUniqueCheck(gloryTopRank.getUnique())) {
                        LCGPlayerGloryActiveUpdate(gloryId);
                    }
                    LCGPlayerGlorySave(puuid, nickname, gloryId, title, info, gloryTopRank.getGrade(), gloryTopRank.getUnique(), "Y", "N");
                } else {
                    LCGPlayerGloryInfoUpdate(puuid, gloryId, info);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
