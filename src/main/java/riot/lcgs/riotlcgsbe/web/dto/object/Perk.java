package riot.lcgs.riotlcgsbe.web.dto.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Perk {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("key")
    private String key;

    @JsonProperty("icon")
    private String icon;

    @JsonProperty("name")
    private String name;

    @JsonProperty("slots")
    private List<PerkSlot> slots;
}
