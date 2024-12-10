package riot.lcgs.riotlcgsbe.jpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "LCG_Match_Team")
public class LCG_Match_Team {

    @Id
    @Column(name = "row_num")
    private Long rowNum;

    @Column(name = "lcg_game_id")
    private Long lgcGameId;

    @Column(name = "lcg_team_id")
    private String lgcTeamId;

    @Column(name = "lcg_first_dragon")
    private boolean lgcFirstDragon;

    @Column(name = "lcg_first_baron")
    private boolean lgcFirstBaron;

    @Column(name = "lcg_first_kill")
    private boolean lgcFirstKill;

    @Column(name = "lcg_first_tower")
    private boolean lgcFirstTower;

    @Column(name = "lcg_first_inhibitor")
    private boolean lgcFirstInhibitor;

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

}
