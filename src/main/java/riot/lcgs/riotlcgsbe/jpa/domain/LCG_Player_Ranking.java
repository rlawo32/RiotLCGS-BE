package riot.lcgs.riotlcgsbe.jpa.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "lcg_ranking_current_rank")
    private Integer lcgRankingCurrentRank;

    @Column(name = "lcg_ranking_previous_rank")
    private Integer lcgRankingPreviousRank;

    @Column(name = "lcg_ranking_grade")
    private Integer lcgRankingGrade;

    @Column(name = "lcg_ranking_current_score")
    private Integer lcgRankingCurrentScore;

    @Column(name = "lcg_ranking_previous_score")
    private Integer lcgRankingPreviousScore;

    @Column(name = "lcg_ranking_count")
    private int lcgRankingCount;

    @NotNull
    @Column(name = "lcg_ranking_active")
    private String lcgRankingActive;

    public LCG_Player_Ranking playerRankingUpdate(int currentRank, int previousRank, int grade, int currentScore, int previousScore) {
        this.lcgRankingCurrentRank = currentRank;
        this.lcgRankingPreviousRank = previousRank;
        this.lcgRankingGrade = grade;
        this.lcgRankingCurrentScore = currentScore;
        this.lcgRankingPreviousScore = previousScore;
        this.lcgRankingCount += 1;
        return this;
    }

    @Builder
    public LCG_Player_Ranking(String lcgSummonerPuuid, String lcgPlayerName, String lcgSummonerNickname,
                              int lcgRankingCurrentRank, int lcgRankingPreviousRank, int lcgRankingGrade,
                              int lcgRankingCurrentScore, int lcgRankingPreviousScore, int lcgRankingCount,
                              String lcgRankingActive) {
        this.lcgSummonerPuuid = lcgSummonerPuuid;
        this.lcgPlayerName = lcgPlayerName;
        this.lcgSummonerNickname = lcgSummonerNickname;
        this.lcgRankingCurrentRank = lcgRankingCurrentRank;
        this.lcgRankingPreviousRank = lcgRankingPreviousRank;
        this.lcgRankingGrade = lcgRankingGrade;
        this.lcgRankingCurrentScore = lcgRankingCurrentScore;
        this.lcgRankingPreviousScore = lcgRankingPreviousScore;
        this.lcgRankingCount = lcgRankingCount;
        this.lcgRankingActive = lcgRankingActive;
    }
}
