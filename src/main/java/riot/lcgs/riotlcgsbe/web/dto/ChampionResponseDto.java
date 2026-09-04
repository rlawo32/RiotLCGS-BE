package riot.lcgs.riotlcgsbe.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChampionResponseDto {
    private String version;
    private Map<String, ChampionData> data;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ChampionData {
        private String id;      // 챔피언 영문 이름
        private String key;     // 챔피언 고유 ID
        private String name;    // 챔피언 한글 이름
    }
}
