package riot.lcgs.riotlcgsbe.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import riot.lcgs.riotlcgsbe.web.dto.CommonResponseDto;
import riot.lcgs.riotlcgsbe.web.dto.object.MainVersion;
import riot.lcgs.riotlcgsbe.web.dto.object.Perk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class HttpService {

    private static String verMain;
    private static String verChampion;
    private static String verSummoner;

    public static CommonResponseDto<Map<String, String>> DataDragonAPIVersion() {
        Map<String, String> result = new HashMap<>();
        StringBuffer sb = new StringBuffer();

        try {
            URL url = new URL("https://ddragon.leagueoflegends.com/realms/na.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setDoOutput(true);

            BufferedReader br;
            if(conn.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            }

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            conn.disconnect();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node1 = objectMapper.readTree(sb.toString());
            MainVersion mainVersion = objectMapper.treeToValue(node1, MainVersion.class);

            result.put("ver", mainVersion.getV());
            result.put("cdn", mainVersion.getCdn());
            result.put("lang", mainVersion.getL());
            result.put("item", mainVersion.getN().getItem());
            result.put("rune", mainVersion.getN().getRune());
            result.put("mastery", mainVersion.getN().getMastery());
            result.put("summoner", mainVersion.getN().getSummoner());
            result.put("champion", mainVersion.getN().getChampion());

            verMain = mainVersion.getV();
            verChampion = mainVersion.getN().getChampion();
            verSummoner = mainVersion.getN().getSummoner();

        } catch (IOException ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed(ex.getMessage());
        }

        return CommonResponseDto.setSuccess("Success", result);
    }

    public static CommonResponseDto<String> DataDragonAPIChampion() {
        StringBuffer sb = new StringBuffer();

        try {
            URL url = new URL("https://ddragon.leagueoflegends.com/cdn/" + verChampion + "/data/en_US/champion.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setDoOutput(true);

            BufferedReader br;
            if(conn.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            }

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            conn.disconnect();

        } catch (IOException ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed(ex.getMessage());
        }

        return CommonResponseDto.setSuccess("Success", sb.toString());
    }

    public static CommonResponseDto<?> DataDragonAPISummoner() {

        try {
            URL url = new URL("https://ddragon.leagueoflegends.com/cdn/" + verSummoner + "/data/en_US/summoner.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setDoOutput(true);

            BufferedReader br;
            if(conn.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            }

            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            conn.disconnect();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node1 = objectMapper.readTree(sb.toString());
            MainVersion mainVersion = objectMapper.treeToValue(node1, MainVersion.class);

        } catch (IOException ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed(ex.getMessage());
        }

        return CommonResponseDto.setSuccess("Success", "");
    }

    public static CommonResponseDto<List<Perk>> DataDragonAPIPerk() {
        List<Perk> perk = new ArrayList<>();
        StringBuffer sb = new StringBuffer();

        try {
            URL url = new URL("https://ddragon.leagueoflegends.com/cdn/" + verMain + "/data/en_US/runesReforged.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setDoOutput(true);

            BufferedReader br;
            if(conn.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            }

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            conn.disconnect();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node1 = objectMapper.readTree(sb.toString());
            perk = List.of(objectMapper.treeToValue(node1, Perk[].class));

        } catch (IOException ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed(ex.getMessage());
        }

        return CommonResponseDto.setSuccess("Success", perk);
    }
}
