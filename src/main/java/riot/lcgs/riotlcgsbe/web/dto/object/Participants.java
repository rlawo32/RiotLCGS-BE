package riot.lcgs.riotlcgsbe.web.dto.object;

import lombok.Data;

@Data
public class Participants {
    private int championId;
    private String highestAchievedSeasonTier;
    private int participantId;
    private int spell1Id;
    private int spell2Id;
    private Stats stats;
    private int teamId;
    private Object timeline;
}
