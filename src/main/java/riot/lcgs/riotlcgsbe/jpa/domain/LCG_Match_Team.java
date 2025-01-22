package riot.lcgs.riotlcgsbe.jpa.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Column(name = "lcg_game_id")
    private Long lcgGameId;

    @NotNull
    @Column(name = "lcg_team_id")
    private int lcgTeamId;

    @NotNull
    @Column(name = "lcg_team_win", length = 1)
    private String lcgTeamWin;

    @NotNull
    @Column(name = "lcg_first_dragon", length = 1)
    private String lcgFirstDragon;

    @NotNull
    @Column(name = "lcg_first_baron", length = 1)
    private String lcgFirstBaron;

    @NotNull
    @Column(name = "lcg_first_kill", length = 1)
    private String lcgFirstKill;

    @NotNull
    @Column(name = "lcg_first_tower", length = 1)
    private String lcgFirstTower;

    @NotNull
    @Column(name = "lcg_first_inhibitor", length = 1)
    private String lcgFirstInhibitor;

    @NotNull
    @Column(name = "lcg_total_gold")
    private int lcgTotalGold;

    @NotNull
    @Column(name = "lcg_total_kill")
    private int lcgTotalKill;

    @NotNull
    @Column(name = "lcg_total_death")
    private int lcgTotalDeath;

    @NotNull
    @Column(name = "lcg_total_assist")
    private int lcgTotalAssist;

    @NotNull
    @Column(name = "lcg_total_dragon")
    private int lcgTotalDragon;

    @NotNull
    @Column(name = "lcg_total_baron")
    private int lcgTotalBaron;

    @NotNull
    @Column(name = "lcg_total_horde")
    private int lcgTotalHorde;

    @NotNull
    @Column(name = "lcg_total_herald")
    private int lcgTotalHerald;
    
    @NotNull
    @Column(name = "lcg_total_atakhan")
    private int lcgTotalAtakhan;

    @NotNull
    @Column(name = "lcg_total_tower")
    private int lcgTotalTower;

    @NotNull
    @Column(name = "lcg_total_inhibitor")
    private int lcgTotalInhibitor;

    @NotNull
    @Column(name = "lcg_bans_name_1")
    private String lcgBansName1;

    @NotNull
    @Column(name = "lcg_bans_name_2")
    private String lcgBansName2;

    @NotNull
    @Column(name = "lcg_bans_name_3")
    private String lcgBansName3;

    @NotNull
    @Column(name = "lcg_bans_name_4")
    private String lcgBansName4;

    @NotNull
    @Column(name = "lcg_bans_name_5")
    private String lcgBansName5;

    @Builder
    public LCG_Match_Team(Long lcgGameId, int lcgTeamId, String lcgTeamWin, String lcgFirstDragon, String lcgFirstBaron,
                          String lcgFirstKill, String lcgFirstTower, String lcgFirstInhibitor, int lcgTotalGold,
                          int lcgTotalKill, int lcgTotalDeath, int lcgTotalAssist, int lcgTotalDragon, int lcgTotalBaron,
                          int lcgTotalHorde, int lcgTotalHerald, int lcgTotalAtakhan, int lcgTotalTower, int lcgTotalInhibitor, 
                          String lcgBansName1, String lcgBansName2, String lcgBansName3, String lcgBansName4, String lcgBansName5) {
        this.lcgGameId = lcgGameId;
        this.lcgTeamId = lcgTeamId;
        this.lcgTeamWin = lcgTeamWin;
        this.lcgFirstDragon = lcgFirstDragon;
        this.lcgFirstBaron = lcgFirstBaron;
        this.lcgFirstKill = lcgFirstKill;
        this.lcgFirstTower = lcgFirstTower;
        this.lcgFirstInhibitor = lcgFirstInhibitor;
        this.lcgTotalGold = lcgTotalGold;
        this.lcgTotalKill = lcgTotalKill;
        this.lcgTotalDeath = lcgTotalDeath;
        this.lcgTotalAssist = lcgTotalAssist;
        this.lcgTotalDragon = lcgTotalDragon;
        this.lcgTotalBaron = lcgTotalBaron;
        this.lcgTotalHorde = lcgTotalHorde;
        this.lcgTotalHerald = lcgTotalHerald;
        this.lcgTotalAtakhan = lcgTotalAtakhan;
        this.lcgTotalTower = lcgTotalTower;
        this.lcgTotalInhibitor = lcgTotalInhibitor;
        this.lcgBansName1 = lcgBansName1;
        this.lcgBansName2 = lcgBansName2;
        this.lcgBansName3 = lcgBansName3;
        this.lcgBansName4 = lcgBansName4;
        this.lcgBansName5 = lcgBansName5;
    }
}
