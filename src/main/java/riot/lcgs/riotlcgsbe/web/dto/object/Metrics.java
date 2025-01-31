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
    private int crowdScore;
    private long multiKillScore;
    private long demolisherScore;
    private long objectScore;
    private int totalKill;
    private int totalScore;
    private boolean win;
    private boolean firstKill;
    private boolean firstTower;
    private boolean firstInhibitor;
    private boolean firstDragon;
    private boolean firstBaron;


    public Metrics(String puuid, int kill, int death, int assist, int gold, int cs, int damage,
                   int visionScore, int crowdScore, long multiKillScore, long demolisherScore,
                   long objectScore, int totalKill, int totalScore, boolean win, boolean firstKill,
                   boolean firstTower, boolean firstInhibitor, boolean firstDragon, boolean firstBaron) {
        this.puuid = puuid;
        this.kill = kill;
        this.death = death;
        this.assist = assist;
        this.gold = gold;
        this.cs = cs;
        this.damage = damage;
        this.visionScore = visionScore;
        this.crowdScore = crowdScore;
        this.multiKillScore = multiKillScore;
        this.demolisherScore = demolisherScore;
        this.objectScore = objectScore;
        this.totalKill = totalKill;
        this.totalScore = totalScore;
        this.win = win;
        this.firstKill = firstKill;
        this.firstTower = firstTower;
        this.firstInhibitor = firstInhibitor;
        this.firstDragon = firstDragon;
        this.firstBaron = firstBaron;
    }
}
