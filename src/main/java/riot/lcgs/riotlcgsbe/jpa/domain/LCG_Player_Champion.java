package riot.lcgs.riotlcgsbe.jpa.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import riot.lcgs.riotlcgsbe.web.dto.object.Player;
import riot.lcgs.riotlcgsbe.web.dto.object.RankData;
import riot.lcgs.riotlcgsbe.web.dto.object.Stats;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "LCG_Player_Champion")
public class LCG_Player_Champion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_num")
    private Long rowNum;

    @NotNull
    @Column(name = "lcg_puuid")
    private String lcgPuuid;

    @NotNull
    @Column(name = "lcg_champion_id")
    private int lcgChampionId;

    @NotNull
    @Column(name = "lcg_champion_name")
    private String lcgChampionName;

    @NotNull
    @Column(name = "lcg_kill_count")
    private Long lcgKillCount;

    @NotNull
    @Column(name = "lcg_death_count")
    private Long lcgDeathCount;

    @NotNull
    @Column(name = "lcg_assist_count")
    private Long lcgAssistCount;

    @NotNull
    @Column(name = "lcg_play_count")
    private Long lcgPlayCount;

    @NotNull
    @Column(name = "lcg_win_count")
    private Long lcgWinCount;

    @NotNull
    @Column(name = "lcg_fail_count")
    private Long lcgFailCount;

    public LCG_Player_Champion playerChampionUpdate(Stats statsData) {
        this.lcgKillCount += (long) statsData.getKills();
        this.lcgDeathCount += (long) statsData.getDeaths();
        this.lcgAssistCount += (long) statsData.getAssists();
        this.lcgPlayCount += 1L;
        this.lcgWinCount += statsData.getWin() ? 1L : 0L;
        this.lcgFailCount += statsData.getWin() ? 0L : 1L;
        return this;
    }

    @Builder
    public LCG_Player_Champion(String lcgPuuid, int lcgChampionId, String lcgChampionName,
                               Long lcgKillCount, Long lcgDeathCount, Long lcgAssistCount,
                               Long lcgPlayCount, Long lcgWinCount, Long lcgFailCount) {
        this.lcgPuuid = lcgPuuid;
        this.lcgChampionId = lcgChampionId;
        this.lcgChampionName = lcgChampionName;
        this.lcgKillCount = lcgKillCount;
        this.lcgDeathCount = lcgDeathCount;
        this.lcgAssistCount = lcgAssistCount;
        this.lcgPlayCount = lcgPlayCount;
        this.lcgWinCount = lcgWinCount;
        this.lcgFailCount = lcgFailCount;
    }
}
