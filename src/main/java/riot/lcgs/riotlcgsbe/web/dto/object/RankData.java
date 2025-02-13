package riot.lcgs.riotlcgsbe.web.dto.object;

import lombok.Data;

@Data
public class RankData {
    private String puuid;
    private Integer wins;
    private Integer points;
    private String presentTier;
    private String presentDivision;
    private String presentHighestTier;
    private String presentHighestDivision;
    private String previousTier;
    private String previousDivision;
    private String previousHighestTier;
    private String previousHighestDivision;
}
