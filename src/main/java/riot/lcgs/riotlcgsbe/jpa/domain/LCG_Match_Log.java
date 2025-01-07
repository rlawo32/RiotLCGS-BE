package riot.lcgs.riotlcgsbe.jpa.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "LCG_Match_Log")
public class LCG_Match_Log {

    @Id
    @Column(name = "lcg_game_id")
    private Long lcgGameId;

    @NotNull
    @Column(name = "lcg_game_win")
    private int lcgGameWin;

    @NotNull
    @Column(name = "lcg_game_date")
    private String lcgGameDate;

    @NotNull
    @Column(name = "lcg_game_ver")
    private String lcgGameVer;

    @NotNull
    @Column(name = "lcg_game_duration")
    private int lcgGameDuration;

    @NotNull
    @Column(name = "team_A_name_1")
    private String teamAName1;

    @NotNull
    @Column(name = "team_A_name_2")
    private String teamAName2;

    @NotNull
    @Column(name = "team_A_name_3")
    private String teamAName3;

    @NotNull
    @Column(name = "team_A_name_4")
    private String teamAName4;

    @NotNull
    @Column(name = "team_A_name_5")
    private String teamAName5;

    @NotNull
    @Column(name = "team_B_name_1")
    private String teamBName1;

    @NotNull
    @Column(name = "team_B_name_2")
    private String teamBName2;

    @NotNull
    @Column(name = "team_B_name_3")
    private String teamBName3;

    @NotNull
    @Column(name = "team_B_name_4")
    private String teamBName4;

    @NotNull
    @Column(name = "team_B_name_5")
    private String teamBName5;

    @Builder
    public LCG_Match_Log(Long lcgGameId, int lcgGameWin, String lcgGameDate,
                        String lcgGameVer, int lcgGameDuration, String teamAName1,
                        String teamAName2, String teamAName3, String teamAName4,
                        String teamAName5, String teamBName1, String teamBName2,
                        String teamBName3, String teamBName4, String teamBName5) {
        this.lcgGameId = lcgGameId;
        this.lcgGameWin = lcgGameWin;
        this.lcgGameDate = lcgGameDate;
        this.lcgGameVer = lcgGameVer;
        this.lcgGameDuration = lcgGameDuration;
        this.teamAName1 = teamAName1;
        this.teamAName2 = teamAName2;
        this.teamAName3 = teamAName3;
        this.teamAName4 = teamAName4;
        this.teamAName5 = teamAName5;
        this.teamBName1 = teamBName1;
        this.teamBName2 = teamBName2;
        this.teamBName3 = teamBName3;
        this.teamBName4 = teamBName4;
        this.teamBName5 = teamBName5;
    }
}
