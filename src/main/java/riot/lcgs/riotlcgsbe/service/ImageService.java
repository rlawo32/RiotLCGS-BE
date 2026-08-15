package riot.lcgs.riotlcgsbe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import riot.lcgs.riotlcgsbe.util.EmailTool;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.core.retry.RetryPolicy;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.paginators.ListObjectsV2Iterable;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.GZIPInputStream;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageService {

    @Value("${cloud.r2.access-key}")
    private String R2_ACCESS_KEY;

    @Value("${cloud.r2.secret-key}")
    private String R2_SECRET_KEY;

    @Value("${cloud.r2.endpoint}")
    private String R2_ENDPOINT;

    @Value("${cloud.r2.bucket.name}")
    private String R2_BUCKET_NAME;

    private static final String TGZ_URL = "https://ddragon.leagueoflegends.com/cdn/";
    private static final String TARGET_DIR_PROFILE_ICON = "/img/profileicon";
    private static final String TARGET_DIR_CHAMPION = "/img/champion";
    private static final String TARGET_DIR_SPELL = "/img/spell";
    private static final String TARGET_DIR_ITEM = "/img/item";
    private static final String TARGET_DIR_CHAMPION_LOADING = "img/champion/loading";
    private static final String TARGET_DIR_PERKS = "img/perk-images/Styles";
    private static final String R2_PREFIX_PROFILE_ICON = "profileicon/";
    private static final int DOWNLOAD_CONNECT_TIMEOUT_MS = 30_000;
    private static final int DOWNLOAD_READ_TIMEOUT_MS = 5 * 60 * 1000;
    private static final int DOWNLOAD_MAX_RETRY = 3;
    private static final DateTimeFormatter LOG_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd_HH:mm:ss");

    private S3Client getS3Client() {
        ClientOverrideConfiguration overrideConfig = ClientOverrideConfiguration.builder()
                .apiCallTimeout(Duration.ofMinutes(2))
                .apiCallAttemptTimeout(Duration.ofSeconds(30))
                .retryPolicy(RetryPolicy.builder().numRetries(3).build())
                .build();

        return S3Client.builder()
                .endpointOverride(URI.create(R2_ENDPOINT))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(R2_ACCESS_KEY, R2_SECRET_KEY)))
                .region(Region.US_EAST_1)
                .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
                .overrideConfiguration(overrideConfig)
                .build();
    }

    private Set<String> listExistingR2FileNames(S3Client s3, String prefix) {
        Set<String> existing = new HashSet<>();

        try {
            ListObjectsV2Request request = ListObjectsV2Request.builder()
                    .bucket(R2_BUCKET_NAME)
                    .prefix(prefix)
                    .build();

            ListObjectsV2Iterable pages = s3.listObjectsV2Paginator(request);
            for (ListObjectsV2Response page : pages) {
                for (S3Object obj : page.contents()) {
                    existing.add(obj.key().substring(prefix.length()));
                }
            }

            log.info("R2 기존 파일 조회 완료 : prefix={} / 개수={}", prefix, existing.size());
        } catch (Exception e) {
            log.warn("R2 기존 파일 조회 실패 (전체 업로드로 진행됨) : prefix={} - {}", prefix, e.getMessage());
        }

        return existing;
    }

    private void uploadToR2(String flag, S3Client s3, String fileName, byte[] data) throws IOException {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(R2_BUCKET_NAME)
                .key(flag + fileName)
                .contentType("image/webp")
                .build();

        try (InputStream input = new ByteArrayInputStream(data)) {
            s3.putObject(request, software.amazon.awssdk.core.sync.RequestBody.fromInputStream(input, data.length));
        }
    }

    private void deleteToR2(String flag, S3Client s3, String fileName) {
        DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                .bucket(R2_BUCKET_NAME)
                .key(flag + fileName)
                .build();

        s3.deleteObject(deleteRequest);
    }

    private byte[] convertToWebp(byte[] originalData, boolean lossless, float quality) throws IOException {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(originalData));
        if (image == null) {
            throw new IOException("이미지를 디코딩할 수 없습니다. (지원하지 않는 포맷이거나 손상된 파일)");
        }

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByMIMEType("image/webp");
        if (!writers.hasNext()) {
            throw new IOException("WebP ImageWriter를 찾을 수 없습니다. webp-imageio 의존성이 classpath에 있는지 확인하세요.");
        }
        ImageWriter writer = writers.next();

        try {
            ImageWriteParam writeParam = writer.getDefaultWriteParam();
            if (writeParam.canWriteCompressed()) {
                writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                writeParam.setCompressionType(lossless ? "Lossless" : "Lossy");
                if (!lossless) {
                    writeParam.setCompressionQuality(quality);
                }
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (ImageOutputStream ios = ImageIO.createImageOutputStream(baos)) {
                writer.setOutput(ios);
                writer.write(null, new IIOImage(image, null, null), writeParam);
            }

            return baos.toByteArray();
        } finally {
            writer.dispose();
        }
    }

    private static String toWebpFileName(String fileName) {
        int dotIdx = fileName.lastIndexOf('.');
        return dotIdx == -1 ? fileName + ".webp" : fileName.substring(0, dotIdx) + ".webp";
    }

    private boolean convertAndUpload(String flag, S3Client s3, String fileName, byte[] fileData) {
        try {
            byte[] webpData = convertToWebp(fileData, false, 0.85f);
            uploadToR2(flag, s3, toWebpFileName(fileName), webpData);
            return true;
        } catch (IOException e) {
            log.warn("WebP 변환/업로드 실패: {}{} - {}", flag, fileName, e.getMessage());
            return false;
        }
    }

    private void downloadWithRetry(String downloadUrl, File destFile) throws IOException {
        IOException lastException = null;

        for (int attempt = 1; attempt <= DOWNLOAD_MAX_RETRY; attempt++) {
            try {
                log.info("Download attempt {}/{} : {}", attempt, DOWNLOAD_MAX_RETRY, downloadUrl);
                downloadToFile(downloadUrl, destFile);
                log.info("Download completed: {} ({} bytes)", destFile.getAbsolutePath(), destFile.length());
                return;
            } catch (IOException e) {
                lastException = e;
                log.warn("다운로드 실패 ({}/{}): {}", attempt, DOWNLOAD_MAX_RETRY, e.getMessage());
                Files.deleteIfExists(destFile.toPath());

                if (attempt < DOWNLOAD_MAX_RETRY) {
                    try {
                        Thread.sleep(5_000L * attempt);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new IOException("다운로드 재시도 대기 중 인터럽트됨", ie);
                    }
                }
            }
        }

        throw new IOException("다운로드 최종 실패 (" + DOWNLOAD_MAX_RETRY + "회 시도)", lastException);
    }

    private void downloadToFile(String downloadUrl, File destFile) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(downloadUrl).openConnection();
        conn.setConnectTimeout(DOWNLOAD_CONNECT_TIMEOUT_MS);
        conn.setReadTimeout(DOWNLOAD_READ_TIMEOUT_MS);
        conn.setRequestMethod("GET");

        try {
            long expectedLength = conn.getContentLengthLong();

            try (InputStream in = conn.getInputStream();
                 OutputStream out = new BufferedOutputStream(new FileOutputStream(destFile))) {
                byte[] buffer = new byte[8192];
                long totalRead = 0;
                int len;
                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                    totalRead += len;
                }

                if (expectedLength > 0 && totalRead != expectedLength) {
                    throw new IOException("다운로드 불완전: 예상 " + expectedLength + " byte, 실제 " + totalRead + " byte");
                }
            }
        } finally {
            conn.disconnect();
        }
    }

    private static byte[] readBytesFromZipEntry(TarArchiveInputStream tis) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int len;
        while ((len = tis.read(buffer)) > 0) {
            baos.write(buffer, 0, len);
        }
        return baos.toByteArray();
    }

    public Map<String, Object> DataDragonImageUpload(String ver) {
        String downloadUrl = TGZ_URL + "dragontail-" + ver + ".tgz";
        File tempFile = new File(System.getProperty("java.io.tmpdir"), "dragontail-" + ver + ".tgz");
        AtomicInteger uploadedCount = new AtomicInteger(0);
        AtomicInteger skippedCount = new AtomicInteger(0);

        Map<String, Object> startInfo = new HashMap<>();
        startInfo.put("version", ver);
        EmailTool.sendMessage_Image("S", startInfo);

        long startTime = System.currentTimeMillis();
        log.info("[START] Image upload start : {} / startTime={}", downloadUrl, LocalDateTime.now().format(LOG_TIME_FORMATTER));

        try {
            downloadWithRetry(downloadUrl, tempFile);

            S3Client s3 = getS3Client();
            Set<String> existingProfileIcons = listExistingR2FileNames(s3, R2_PREFIX_PROFILE_ICON);

            try (
                    InputStream fis = new FileInputStream(tempFile);
                    GZIPInputStream gzipIn = new GZIPInputStream(fis);
                    TarArchiveInputStream tarIn = new TarArchiveInputStream(gzipIn)
            ) {
                processEntries(tarIn, ver, s3, uploadedCount, skippedCount, existingProfileIcons);
            }
        } catch (Exception e) {
            log.error("Image upload failed", e);
        } finally {
            try {
                Files.deleteIfExists(tempFile.toPath());
            } catch (IOException e) {
                log.warn("임시 파일 삭제 실패: {} - {}", tempFile.getAbsolutePath(), e.getMessage());
            }
        }

        long endTime = System.currentTimeMillis();
        long elapsedMillis = endTime - startTime;
        log.info("[END] Image upload end : endTime={} / elapsedMillis={} / uploadedCount={} / skippedCount={}",
                LocalDateTime.now().format(LOG_TIME_FORMATTER), elapsedMillis, uploadedCount.get(), skippedCount.get());

        Map<String, Object> result = new HashMap<>();
        result.put("elapsedMillis", elapsedMillis);
        result.put("uploadedCount", uploadedCount.get());
        EmailTool.sendMessage_Image("E", result);
        return result;
    }

    private void processEntries(TarArchiveInputStream tarIn, String ver, S3Client s3, AtomicInteger uploadedCount,
                                AtomicInteger skippedCount, Set<String> existingProfileIcons) throws IOException {
        TarArchiveEntry entry;

        while ((entry = tarIn.getNextTarEntry()) != null) {
            String entryName = entry.getName();

            if (!entry.isDirectory() && entryName.startsWith(ver + TARGET_DIR_CHAMPION)) {
                String fileName = entryName.substring((ver + TARGET_DIR_CHAMPION).length() + 1);
                log.debug("Champion Uploading: {}", fileName);

                byte[] fileData = readBytesFromZipEntry(tarIn);

                if (convertAndUpload("champion/", s3, fileName, fileData)) {
                    uploadedCount.incrementAndGet();
                }
            }

            if (!entry.isDirectory() && entryName.startsWith(ver + TARGET_DIR_SPELL)) {
                String fileName = entryName.substring((ver + TARGET_DIR_SPELL).length() + 1);

                if(fileName.startsWith("Summoner")) {
                    byte[] fileData = readBytesFromZipEntry(tarIn);
                    log.debug("Spell Uploading: {}", fileName);

                    if (convertAndUpload("spell/", s3, fileName, fileData)) {
                        uploadedCount.incrementAndGet();
                    }
                }
            }

            if (!entry.isDirectory() && entryName.startsWith(ver + TARGET_DIR_ITEM)) {
                String fileName = entryName.substring((ver + TARGET_DIR_ITEM).length() + 1);
                log.debug("Item Uploading: {}", fileName);

                byte[] fileData = readBytesFromZipEntry(tarIn);

                if (convertAndUpload("item/", s3, fileName, fileData)) {
                    uploadedCount.incrementAndGet();
                }
            }

            if (!entry.isDirectory() && entryName.startsWith(TARGET_DIR_PERKS)) {
                String fileName = entryName.substring((TARGET_DIR_PERKS).length() + 1);
                log.debug("Perks Uploading: {}", fileName);

                byte[] fileData = readBytesFromZipEntry(tarIn);

                if(!fileName.contains("/")) {
                    if (convertAndUpload("perk-images/Styles/", s3, fileName, fileData)) {
                        uploadedCount.incrementAndGet();
                    }
                } else {
                    String filePerkName = fileName.substring(fileName.lastIndexOf("/") + 1);
                    String filePerkPath = fileName.substring(0, fileName.lastIndexOf("/") + 1);
                    if (convertAndUpload("perk-images/Styles/" + filePerkPath, s3, filePerkName, fileData)) {
                        uploadedCount.incrementAndGet();
                    }
                }
            }

            if (!entry.isDirectory() && entryName.startsWith(TARGET_DIR_CHAMPION_LOADING)) {
                String fileName = entryName.substring((TARGET_DIR_CHAMPION_LOADING).length() + 1);

                if(fileName.endsWith("_0.jpg")) {
                    byte[] fileData = readBytesFromZipEntry(tarIn);
                    log.debug("LoadingImage Uploading: {}", fileName);

                    if (convertAndUpload("loading/", s3, fileName, fileData)) {
                        uploadedCount.incrementAndGet();
                    }
                }
            }

            if (!entry.isDirectory() && entryName.startsWith(ver + TARGET_DIR_PROFILE_ICON)) {
                String fileName = entryName.substring((ver + TARGET_DIR_PROFILE_ICON).length() + 1);

                if (existingProfileIcons.contains(toWebpFileName(fileName))) {
                    skippedCount.incrementAndGet();
                } else {
                    byte[] fileData = readBytesFromZipEntry(tarIn);
                    log.debug("ProfileIcon Uploading: {}", fileName);

                    if (convertAndUpload(R2_PREFIX_PROFILE_ICON, s3, fileName, fileData)) {
                        uploadedCount.incrementAndGet();
                    }
                }
            }
        }
    }
}
