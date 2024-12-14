package riot.lcgs.riotlcgsbe.web.dto.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PerkSlot {

    @JsonProperty("runes")
    private List<Rune> runes;
}
