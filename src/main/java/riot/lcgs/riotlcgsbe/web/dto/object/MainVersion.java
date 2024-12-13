package riot.lcgs.riotlcgsbe.web.dto.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MainVersion {

    @JsonProperty("n")
    private SubVersion n;

    @JsonProperty("v")
    private String v;

    @JsonProperty("l")
    private String l;

    @JsonProperty("cdn")
    private String cdn;

    @JsonProperty("dd")
    private String dd;

    @JsonProperty("lg")
    private String lg;

    @JsonProperty("css")
    private String css;

    @JsonProperty("profileiconmax")
    private String profileiconmax;

    @JsonProperty("store")
    private String store;
}
