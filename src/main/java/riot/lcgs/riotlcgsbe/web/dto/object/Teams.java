package riot.lcgs.riotlcgsbe.web.dto.object;

import lombok.Data;

import java.util.List;

@Data
public class Teams {
    private List<Bans> bans;
    private int baronKills;
    private int dominionVictoryScore;
    private int dragonKills;
    private boolean firstBaron;
    private boolean firstBlood;
    private boolean firstDargon;
    private boolean firstInhibitor;
    private boolean firstTower;
    private int hordeKills;
    private int inhibitorKills;
    private int riftHeraldKills;
    private int teamId;
    private int towerKills;
    private int vilemawKills;
    private String win;
}
