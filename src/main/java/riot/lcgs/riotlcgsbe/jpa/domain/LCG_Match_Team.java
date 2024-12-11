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

    @Column(name = "lcg_bans_champ_1")
    private int lgcBansChamp1;

    @Column(name = "lcg_bans_name_1")
    private String lgcBansName1;

    @Column(name = "lcg_bans_champ_2")
    private int lgcBansChamp2;

    @Column(name = "lcg_bans_name_2")
    private String lgcBansName2;

    @Column(name = "lcg_bans_champ_3")
    private int lgcBansChamp3;

    @Column(name = "lcg_bans_name_3")
    private String lgcBansName3;

    @Column(name = "lcg_bans_champ_4")
    private int lgcBansChamp4;

    @Column(name = "lcg_bans_name_4")
    private String lgcBansName4;

    @Column(name = "lcg_bans_champ_5")
    private int lgcBansChamp5;

    @Column(name = "lcg_bans_name_5")
    private String lgcBansName5;

    @Column(name = "lcg_bans_champ_6")
    private int lgcBansChamp6;

    @Column(name = "lcg_bans_name_6")
    private String lgcBansName6;

    @Column(name = "lcg_bans_champ_7")
    private int lgcBansChamp7;

    @Column(name = "lcg_bans_name_7")
    private String lgcBansName7;

    @Column(name = "lcg_bans_champ_8")
    private int lgcBansChamp8;

    @Column(name = "lcg_bans_name_8")
    private String lgcBansName8;

    @Column(name = "lcg_bans_champ_9")
    private int lgcBansChamp9;

    @Column(name = "lcg_bans_name_9")
    private String lgcBansName9;

    @Column(name = "lcg_bans_champ_10")
    private int lgcBansChamp10;

    @Column(name = "lcg_bans_name_10")
    private String lgcBansName10;

    @Builder
    public LCG_Match_Team(Long lgcGameId, int lgcTeamId, String lgcFirstDragon, String lgcFirstBaron,
                          String lgcFirstKill, String lgcFirstTower, String lgcFirstInhibitor, int lgcDragonTotal,
                          int lgcBaronTotal, int lgcTowerTotal, int lgcHordeTotal, int lgcHeraldTotal,
                          int lgcBansChamp1, String lgcBansName1, int lgcBansChamp2, String lgcBansName2,
                          int lgcBansChamp3, String lgcBansName3, int lgcBansChamp4, String lgcBansName4,
                          int lgcBansChamp5, String lgcBansName5, int lgcBansChamp6, String lgcBansName6,
                          int lgcBansChamp7, String lgcBansName7, int lgcBansChamp8, String lgcBansName8,
                          int lgcBansChamp9, String lgcBansName9, int lgcBansChamp10, String lgcBansName10) {
        this.lgcGameId = lgcGameId;
        this.lgcTeamId = lgcTeamId;
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
        this.lgcBansChamp1 = lgcBansChamp1;
        this.lgcBansName1 = lgcBansName1;
        this.lgcBansChamp2 = lgcBansChamp2;
        this.lgcBansName2 = lgcBansName2;
        this.lgcBansChamp3 = lgcBansChamp3;
        this.lgcBansName3 = lgcBansName3;
        this.lgcBansChamp4 = lgcBansChamp4;
        this.lgcBansName4 = lgcBansName4;
        this.lgcBansChamp5 = lgcBansChamp5;
        this.lgcBansName5 = lgcBansName5;
        this.lgcBansChamp6 = lgcBansChamp6;
        this.lgcBansName6 = lgcBansName6;
        this.lgcBansChamp7 = lgcBansChamp7;
        this.lgcBansName7 = lgcBansName7;
        this.lgcBansChamp8 = lgcBansChamp8;
        this.lgcBansName8 = lgcBansName8;
        this.lgcBansChamp9 = lgcBansChamp9;
        this.lgcBansName9 = lgcBansName9;
        this.lgcBansChamp10 = lgcBansChamp10;
        this.lgcBansName10 = lgcBansName10;
    }
}
