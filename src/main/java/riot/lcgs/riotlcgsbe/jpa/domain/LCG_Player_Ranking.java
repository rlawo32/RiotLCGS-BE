package riot.lcgs.riotlcgsbe.jpa.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import riot.lcgs.riotlcgsbe.web.dto.object.Player;
import riot.lcgs.riotlcgsbe.web.dto.object.RankData;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "LCG_Player_Ranking")
public class LCG_Player_Ranking {

    @Id
    @NotNull
    @Column(name = "lcg_summoner_puuid")
    private String lcgSummonerPuuid;

    @NotNull
    @Column(name = "lcg_player_name")
    private String lcgPlayerName;

    @NotNull
    @Column(name = "lcg_summoner_nickname")
    private String lcgSummonerNickname;

    @NotNull
    @Column(name = "lcg_ranking_current")
    private int lcgRankingCurrent;

    @NotNull
    @Column(name = "lcg_ranking_previous")
    private int lcgRankingPrevious;

    @NotNull
    @Column(name = "lcg_ranking_score")
    private int lcgRankingScore;

    @NotNull
    @Column(name = "lcg_ranking_count")
    private int lcgRankingCount;

    @Builder
    public LCG_Player_Ranking(String lcgSummonerPuuid, String lcgPlayerName, String lcgSummonerNickname,
                              int lcgRankingCurrent, int lcgRankingPrevious, int lcgRankingScore, int lcgRankingCount) {
        this.lcgSummonerPuuid = lcgSummonerPuuid;
        this.lcgPlayerName = lcgPlayerName;
        this.lcgSummonerNickname = lcgSummonerNickname;
        this.lcgRankingCurrent = lcgRankingCurrent;
        this.lcgRankingPrevious = lcgRankingPrevious;
        this.lcgRankingScore = lcgRankingScore;
        this.lcgRankingCount = lcgRankingCount;
    }
}
