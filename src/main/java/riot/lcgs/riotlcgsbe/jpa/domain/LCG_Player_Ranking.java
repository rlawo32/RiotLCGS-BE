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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_num")
    private Long rowNum;

    @NotNull
    @Column(name = "lcg_ranking_count")
    private Long lcgRankingCount;

    @NotNull
    @Column(name = "lcg_ranking_rank")
    private Long lcgRankingRank;

    @NotNull
    @Column(name = "lcg_ranking_score")
    private Long lcgRankingScore;

    @NotNull
    @Column(name = "lcg_summoner_puuid")
    private String lcgSummonerPuuid;

    @NotNull
    @Column(name = "lcg_player_name")
    private String lcgPlayerName;

    @NotNull
    @Column(name = "lcg_summoner_nickname")
    private String lcgSummonerNickname;

    @Builder
    public LCG_Player_Ranking(Long lcgRankingCount, Long lcgRankingRank, Long lcgRankingScore,
                              String lcgSummonerPuuid, String lcgPlayerName, String lcgSummonerNickname) {
        this.lcgRankingCount = lcgRankingCount;
        this.lcgRankingRank = lcgRankingRank;
        this.lcgRankingScore = lcgRankingScore;
        this.lcgSummonerPuuid = lcgSummonerPuuid;
        this.lcgPlayerName = lcgPlayerName;
        this.lcgSummonerNickname = lcgSummonerNickname;
    }
}
