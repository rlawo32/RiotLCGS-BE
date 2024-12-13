package riot.lcgs.riotlcgsbe.web.dto.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SubVersion {

    @JsonProperty("item")
    private String item;

    @JsonProperty("rune")
    private String rune;

    @JsonProperty("mastery")
    private String mastery;

    @JsonProperty("summoner")
    private String summoner;

    @JsonProperty("champion")
    private String champion;

    @JsonProperty("profileicon")
    private String profileicon;

    @JsonProperty("map")
    private String map;

    @JsonProperty("language")
    private String language;

    @JsonProperty("sticker")
    private String sticker;
}
