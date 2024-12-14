package riot.lcgs.riotlcgsbe.web.dto.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Rune {

    @JsonProperty("id")
    private int id;

    @JsonProperty("key")
    private String key;

    @JsonProperty("icon")
    private String icon;

    @JsonProperty("name")
    private String name;

    @JsonProperty("shortDesc")
    private String shortDesc;

    @JsonProperty("longDesc")
    private String longDesc;
}
