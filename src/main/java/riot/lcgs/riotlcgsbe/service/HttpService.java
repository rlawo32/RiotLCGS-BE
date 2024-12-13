package riot.lcgs.riotlcgsbe.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import riot.lcgs.riotlcgsbe.web.dto.CommonResponseDto;
import riot.lcgs.riotlcgsbe.web.dto.object.MainVersion;
import riot.lcgs.riotlcgsbe.web.dto.object.SubVersion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class HttpService {

    public static CommonResponseDto<?> DataDragonAPIVersion() {
        Map<String, String> result = new HashMap<>();

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

            result.put("cdn", mainVersion.getCdn());
            result.put("ver", mainVersion.getV());
            result.put("lang", mainVersion.getL());
            result.put("item", mainVersion.getN().getItem());
            result.put("rune", mainVersion.getN().getRune());
            result.put("mastery", mainVersion.getN().getMastery());
            result.put("summoner", mainVersion.getN().getSummoner());
            result.put("champion", mainVersion.getN().getChampion());

        } catch (IOException ex1) {
            ex1.printStackTrace();
            return CommonResponseDto.setSuccess("Failed", ex1.getMessage());
        }

        return CommonResponseDto.setSuccess("Success", result);
    }
}
