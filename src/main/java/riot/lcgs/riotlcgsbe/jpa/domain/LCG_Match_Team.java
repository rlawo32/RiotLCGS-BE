package riot.lcgs.riotlcgsbe.jpa.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "LCG_Match_Team")
public class LCG_Match_Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_num")
    private Long rowNum;

    @Column(name = "lcg_game_id")
    private Long lgcGameId;

    @Column(name = "lcg_team_id")
    private int lgcTeamId;

    @Column(name = "lcg_team_win", length = 1)
    private String lgcTeamWin;

    @Column(name = "lcg_first_dragon", length = 1)
    private String lgcFirstDragon;

    @Column(name = "lcg_first_baron", length = 1)
    private String lgcFirstBaron;

    @Column(name = "lcg_first_kill", length = 1)
    private String lgcFirstKill;

    @Column(name = "lcg_first_tower", length = 1)
    private String lgcFirstTower;

    @Column(name = "lcg_first_inhibitor", length = 1)
    private String lgcFirstInhibitor;

    @Column(name = "lcg_dragon_total")
    private int lgcDragonTotal;

    @Column(name = "lcg_baron_total")
    private int lgcBaronTotal;

    @Column(name = "lcg_tower_total")
    private int lgcTowerTotal;

    @Column(name = "lcg_horde_total")
    private int lgcHordeTotal;

    @Column(name = "lcg_herald_total")
    private int lgcHeraldTotal;

    @Column(name = "lcg_bans_name_1")
    private String lgcBansName1;

    @Column(name = "lcg_bans_name_2")
    private String lgcBansName2;

    @Column(name = "lcg_bans_name_3")
    private String lgcBansName3;

    @Column(name = "lcg_bans_name_4")
    private String lgcBansName4;

    @Column(name = "lcg_bans_name_5")
    private String lgcBansName5;

    @Builder
    public LCG_Match_Team(Long lgcGameId, int lgcTeamId, String lgcTeamWin, String lgcFirstDragon, String lgcFirstBaron,
                          String lgcFirstKill, String lgcFirstTower, String lgcFirstInhibitor, int lgcDragonTotal,
                          int lgcBaronTotal, int lgcTowerTotal, int lgcHordeTotal, int lgcHeraldTotal, String lgcBansName1,
                          String lgcBansName2, String lgcBansName3, String lgcBansName4, String lgcBansName5) {
        this.lgcGameId = lgcGameId;
        this.lgcTeamId = lgcTeamId;
        this.lgcTeamWin = lgcTeamWin;
        this.lgcFirstDragon = lgcFirstDragon;
        this.lgcFirstBaron = lgcFirstBaron;
        this.lgcFirstKill = lgcFirstKill;
        this.lgcFirstTower = lgcFirstTower;
        this.lgcFirstInhibitor = lgcFirstInhibitor;
        this.lgcDragonTotal = lgcDragonTotal;
        this.lgcBaronTotal = lgcBaronTotal;
        this.lgcTowerTotal = lgcTowerTotal;
        this.lgcHordeTotal = lgcHordeTotal;
        this.lgcHeraldTotal = lgcHeraldTotal;
        this.lgcBansName1 = lgcBansName1;
        this.lgcBansName2 = lgcBansName2;
        this.lgcBansName3 = lgcBansName3;
        this.lgcBansName4 = lgcBansName4;
        this.lgcBansName5 = lgcBansName5;
    }
}
