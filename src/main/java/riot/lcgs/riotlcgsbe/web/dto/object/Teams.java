package riot.lcgs.riotlcgsbe.web.dto.object;

import lombok.Data;

import java.util.List;

@Data
public class Teams {
    private List<Bans> bans;
    private Integer baronKills;
    private Integer dominionVictoryScore;
    private Integer dragonKills;
    private Boolean firstBaron;
    private Boolean firstBlood;
    private Boolean firstDargon;
    private Boolean firstInhibitor;
    private Boolean firstTower;
    private Integer hordeKills;
    private Integer inhibitorKills;
    private Integer riftHeraldKills;
    private Integer teamId;
    private Integer towerKills;
    private Integer vilemawKills;
    private String win;
}
