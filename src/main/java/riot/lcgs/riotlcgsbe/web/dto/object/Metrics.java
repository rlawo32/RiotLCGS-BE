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


    public Metrics(String puuid, int kill, int death, int assist, int gold, int cs, int damage,
                   int visionScore, int objectScore, int multiKillScore, int demolisherScore,
                   int totalKill, int totalScore, boolean firstKill, boolean firstTower,
                   boolean firstDragon, boolean firstBaron, boolean firstInhibitor, boolean win) {
        this.puuid = puuid;
        this.kill = kill;
        this.death = death;
        this.assist = assist;
        this.gold = gold;
        this.cs = cs;
        this.damage = damage;
        this.visionScore = visionScore;
        this.objectScore = objectScore;
        this.multiKillScore = multiKillScore;
        this.demolisherScore = demolisherScore;
        this.totalKill = totalKill;
        this.totalScore = totalScore;
        this.firstKill = firstKill;
        this.firstTower = firstTower;
        this.firstDragon = firstDragon;
        this.firstBaron = firstBaron;
        this.firstInhibitor = firstInhibitor;
        this.win = win;
    }
}
