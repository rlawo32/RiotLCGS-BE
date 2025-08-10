package riot.lcgs.riotlcgsbe.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.zip.GZIPInputStream;

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

    private S3Client getS3Client() {
        return S3Client.builder()
                .endpointOverride(URI.create(R2_ENDPOINT))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(R2_ACCESS_KEY, R2_SECRET_KEY)))
                .region(Region.US_EAST_1)
                .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
                .build();
    }

    private void uploadToR2(String flag, S3Client s3, String fileName, byte[] data) throws IOException {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(R2_BUCKET_NAME)
                .key(flag + fileName)
                .contentType("image/png")
                .build();

        try (InputStream input = new ByteArrayInputStream(data)) {
            s3.putObject(request, software.amazon.awssdk.core.sync.RequestBody.fromInputStream(input, data.length));
            System.out.println("Uploaded to R2: " + fileName);
        }
    }

    private void deleteToR2(String flag, S3Client s3, String fileName) {
        DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                .bucket(R2_BUCKET_NAME)
                .key(flag + fileName)
                .build();

        s3.deleteObject(deleteRequest);
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

    public void DataDragonImageUpload(String ver) {
        String downloadUrl = TGZ_URL + "dragontail-" + ver + ".tgz";
        System.out.println("Image upload start : " + downloadUrl);

        try (
                InputStream tgzStream = new URL(downloadUrl).openStream();
                GZIPInputStream gzipIn = new GZIPInputStream(tgzStream);
                TarArchiveInputStream tarIn = new TarArchiveInputStream(gzipIn)
        ) {
            TarArchiveEntry entry;
            S3Client s3 = getS3Client();

            while ((entry = tarIn.getNextTarEntry()) != null) {
                String entryName = entry.getName();

                if (!entry.isDirectory() && entryName.startsWith(ver + TARGET_DIR_CHAMPION)) {
                    String fileName = entryName.substring((ver + TARGET_DIR_CHAMPION).length() + 1);
                    System.out.println("Champion Uploading: " + fileName);

                    byte[] fileData = readBytesFromZipEntry(tarIn);

                    uploadToR2("champion/", s3, fileName, fileData);
                }

                if (!entry.isDirectory() && entryName.startsWith(ver + TARGET_DIR_SPELL)) {
                    String fileName = entryName.substring((ver + TARGET_DIR_SPELL).length() + 1);

                    if(fileName.startsWith("Summoner")) {
                        System.out.println("Spell Uploading: " + fileName);

                        byte[] fileData = readBytesFromZipEntry(tarIn);

                        uploadToR2("spell/", s3, fileName, fileData);
                    }
                }

                if (!entry.isDirectory() && entryName.startsWith(ver + TARGET_DIR_ITEM)) {
                    String fileName = entryName.substring((ver + TARGET_DIR_ITEM).length() + 1);

                    System.out.println("Item Uploading: " + fileName);

                    byte[] fileData = readBytesFromZipEntry(tarIn);

                    uploadToR2("item/", s3, fileName, fileData);
                }

                if (!entry.isDirectory() && entryName.startsWith(TARGET_DIR_PERKS)) {
                    String fileName = entryName.substring((TARGET_DIR_PERKS).length() + 1);

                    System.out.println("Perks Uploading: " + fileName);

                    byte[] fileData = readBytesFromZipEntry(tarIn);

                    if(!fileName.contains("/")) {
//                        System.out.println("Perks Origin Uploading: " + fileName);
                        uploadToR2("perk-images/Styles/", s3, fileName, fileData);
                    } else {
                        String filePerkName = fileName.substring(fileName.lastIndexOf("/") + 1);
                        String filePerkPath = fileName.substring(0, fileName.lastIndexOf("/") + 1);
//                        System.out.println("Perks fullName : " + fileName);
//                        System.out.println("Perks name : " + filePerkName + " / Path : " + filePerkPath);
                        uploadToR2("perk-images/Styles/" + filePerkPath, s3, filePerkName, fileData);
                    }
                }

                if (!entry.isDirectory() && entryName.startsWith(TARGET_DIR_CHAMPION_LOADING)) {
                    String fileName = entryName.substring((TARGET_DIR_CHAMPION_LOADING).length() + 1);

                    if(fileName.endsWith("_0.jpg")) {
                        System.out.println("LoadingImage Uploading: " + fileName);

                        byte[] fileData = readBytesFromZipEntry(tarIn);

                        uploadToR2("loading/", s3, fileName, fileData);
                    }
                }

                if (!entry.isDirectory() && entryName.startsWith(ver + TARGET_DIR_PROFILE_ICON)) {
                    String fileName = entryName.substring((ver + TARGET_DIR_PROFILE_ICON).length() + 1);
                    System.out.println("Icon Uploading: " + fileName);

                    byte[] fileData = readBytesFromZipEntry(tarIn);

                    uploadToR2("profileIcon/", s3, fileName, fileData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
