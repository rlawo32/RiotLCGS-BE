package riot.lcgs.riotlcgsbe.jpa.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "LCG_Player_Relative")
public class LCG_Player_Relative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_num")
    private Long rowNum;

    @NotNull
    @Column(name = "lcg_person_puuid")
    private String lcgPersonPuuid;

    @NotNull
    @Column(name = "lcg_match_line")
    private String lcgMatchLine;

    @NotNull
    @Column(name = "lcg_opponent_puuid")
    private String lcgOpponentPuuid;

    @NotNull
    @Column(name = "lcg_play_count")
    private Long lcgPlayCount;

    @NotNull
    @Column(name = "lcg_win_count")
    private Long lcgWinCount;

    @NotNull
    @Column(name = "lcg_fail_count")
    private Long lcgFailCount;

    @Builder
    public LCG_Player_Relative(String lcgPersonPuuid, String lcgMatchLine, String lcgOpponentPuuid,
                               Long lcgPlayCount, Long lcgWinCount, Long lcgFailCount) {
        this.lcgPersonPuuid = lcgPersonPuuid;
        this.lcgMatchLine = lcgMatchLine;
        this.lcgOpponentPuuid = lcgOpponentPuuid;
        this.lcgPlayCount = lcgPlayCount;
        this.lcgWinCount = lcgWinCount;
        this.lcgFailCount = lcgFailCount;
    }
}
