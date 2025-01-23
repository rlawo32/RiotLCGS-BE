package riot.lcgs.riotlcgsbe.web.dto.object;

import lombok.Data;

@Data
public class Metrics {
    private String puuid;
    private int kill;
    private int death;
    private int assist;
    private int gold;
    private int cs;
    private int damage;
    private int visionScore;
    private int objectScore;
    private int multiKillScore;
    private int demolisherScore;
    private int totalKill;
    private int totalScore;
    private boolean firstKill;
    private boolean firstTower;
    private boolean firstDragon;
    private boolean firstBaron;
    private boolean firstInhibitor;
    private boolean win;
}
