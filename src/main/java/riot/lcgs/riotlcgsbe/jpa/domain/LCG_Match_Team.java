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
    private Long lcgGameId;

    @Column(name = "lcg_team_id")
    private int lcgTeamId;

    @Column(name = "lcg_team_win", length = 1)
    private String lcgTeamWin;

    @Column(name = "lcg_first_dragon", length = 1)
    private String lcgFirstDragon;

    @Column(name = "lcg_first_baron", length = 1)
    private String lcgFirstBaron;

    @Column(name = "lcg_first_kill", length = 1)
    private String lcgFirstKill;

    @Column(name = "lcg_first_tower", length = 1)
    private String lcgFirstTower;

    @Column(name = "lcg_first_inhibitor", length = 1)
    private String lcgFirstInhibitor;

    @Column(name = "lcg_dragon_total")
    private int lcgDragonTotal;

    @Column(name = "lcg_baron_total")
    private int lcgBaronTotal;

    @Column(name = "lcg_tower_total")
    private int lcgTowerTotal;

    @Column(name = "lcg_horde_total")
    private int lcgHordeTotal;

    @Column(name = "lcg_herald_total")
    private int lcgHeraldTotal;

    @Column(name = "lcg_bans_name_1")
    private String lcgBansName1;

    @Column(name = "lcg_bans_name_2")
    private String lcgBansName2;

    @Column(name = "lcg_bans_name_3")
    private String lcgBansName3;

    @Column(name = "lcg_bans_name_4")
    private String lcgBansName4;

    @Column(name = "lcg_bans_name_5")
    private String lcgBansName5;

    @Builder
    public LCG_Match_Team(Long lcgGameId, int lcgTeamId, String lcgTeamWin, String lcgFirstDragon, String lcgFirstBaron,
                          String lcgFirstKill, String lcgFirstTower, String lcgFirstInhibitor, int lcgDragonTotal,
                          int lcgBaronTotal, int lcgTowerTotal, int lcgHordeTotal, int lcgHeraldTotal, String lcgBansName1,
                          String lcgBansName2, String lcgBansName3, String lcgBansName4, String lcgBansName5) {
        this.lcgGameId = lcgGameId;
        this.lcgTeamId = lcgTeamId;
        this.lcgTeamWin = lcgTeamWin;
        this.lcgFirstDragon = lcgFirstDragon;
        this.lcgFirstBaron = lcgFirstBaron;
        this.lcgFirstKill = lcgFirstKill;
        this.lcgFirstTower = lcgFirstTower;
        this.lcgFirstInhibitor = lcgFirstInhibitor;
        this.lcgDragonTotal = lcgDragonTotal;
        this.lcgBaronTotal = lcgBaronTotal;
        this.lcgTowerTotal = lcgTowerTotal;
        this.lcgHordeTotal = lcgHordeTotal;
        this.lcgHeraldTotal = lcgHeraldTotal;
        this.lcgBansName1 = lcgBansName1;
        this.lcgBansName2 = lcgBansName2;
        this.lcgBansName3 = lcgBansName3;
        this.lcgBansName4 = lcgBansName4;
        this.lcgBansName5 = lcgBansName5;
    }
}
