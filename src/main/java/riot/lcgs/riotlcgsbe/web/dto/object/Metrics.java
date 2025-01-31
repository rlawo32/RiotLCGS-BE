package riot.lcgs.riotlcgsbe.web.dto.object;

import lombok.Data;

@Data
public class Metrics {
    private String puuid;
    private String nickname;
    private int totalScore;
    private int kill;
    private int death;
    private int assist;
    private double kda;
    private int engagementRate;
    private int gold;
    private int damage;
    private int cs;
    private double dpm;
    private double gpm;
    private double dpg;
    private int visionScore;
    private int crowdScore;
    private long multiKillScore;
    private long demolisherScore;
    private long objectScore;
    private boolean win;
    private boolean firstKill;
    private boolean firstTower;
    private boolean firstInhibitor;
    private boolean firstDragon;
    private boolean firstBaron;


    public Metrics(String puuid, String nickname, int totalScore, int kill, int death, int assist, double kda,
                   int engagementRate, int gold, int damage, int cs, double dpm, double gpm, double dpg, int visionScore,
                   int crowdScore, long multiKillScore, long demolisherScore, long objectScore, boolean win,
                   boolean firstKill, boolean firstTower, boolean firstInhibitor, boolean firstDragon, boolean firstBaron) {
        this.puuid = puuid;
        this.nickname = nickname;
        this.totalScore = totalScore;
        this.kill = kill;
        this.death = death;
        this.assist = assist;
        this.kda = kda;
        this.engagementRate = engagementRate;
        this.gold = gold;
        this.damage = damage;
        this.cs = cs;
        this.dpm = dpm;
        this.gpm = gpm;
        this.dpg = dpg;
        this.visionScore = visionScore;
        this.crowdScore = crowdScore;
        this.multiKillScore = multiKillScore;
        this.demolisherScore = demolisherScore;
        this.objectScore = objectScore;
        this.win = win;
        this.firstKill = firstKill;
        this.firstTower = firstTower;
        this.firstInhibitor = firstInhibitor;
        this.firstDragon = firstDragon;
        this.firstBaron = firstBaron;
    }
}
