package riot.lcgs.riotlcgsbe.web.dto.object;

import lombok.Data;

@Data
public class Participants {
    private Integer championId;
    private String highestAchievedSeasonTier;
    private Integer participantId;
    private Integer spell1Id;
    private Integer spell2Id;
    private Stats stats;
    private Integer teamId;
    private Object timeline;
}
