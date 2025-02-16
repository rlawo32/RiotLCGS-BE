package riot.lcgs.riotlcgsbe.web.dto.object;

import lombok.Data;

@Data
public class TimeLine {
    private Object creepsPerMinDeltas;
    private Object csDiffPerMinDeltas;
    private Object damageTakenDiffPerMinDeltas;
    private Object damageTakenPerMinDeltas;
    private Object goldPerMinDeltas;
    private String lane;
    private Integer participantId;
    private String role;
    private Object xpDiffPerMinDeltas;
    private Object xpPerMinDeltas;
}
