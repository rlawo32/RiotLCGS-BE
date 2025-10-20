package riot.lcgs.riotlcgsbe.jpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import riot.lcgs.riotlcgsbe.web.dto.object.Player;
import riot.lcgs.riotlcgsbe.web.dto.object.RankData;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "LCG_Player_Data")
public class LCG_Player_Data {

    @Id
    @Column(name = "lcg_summoner_puuid")
    private String lcgSummonerPuuid;

    @NotNull
    @Column(name = "lcg_winning_streak")
    private int lcgWinningStreak;
    
    @NotNull
    @Column(name = "lcg_player")
    private String lcgPlayer;

    @NotNull
    @Column(name = "lcg_summoner_nickname")
    private String lcgSummonerNickname;

    @NotNull
    @Column(name = "lcg_summoner_Id")
    private Long lcgSummonerId;

    @NotNull
    @Column(name = "lcg_summoner_name")
    private String lcgSummonerName;

    @NotNull
    @Column(name = "lcg_summoner_tag")
    private String lcgSummonerTag;

    @NotNull
    @Column(name = "lcg_summoner_icon")
    private int lcgSummonerIcon;

    @NotNull
    @Column(name = "lcg_rank_win")
    private int lcgRankWin;

    @NotNull
    @Column(name = "lcg_rank_point")
    private int lcgRankPoint;

    @NotNull
    @Column(name = "lcg_present_tier")
    private String lcgPresentTier;

    @NotNull
    @Column(name = "lcg_present_division")
    private String lcgPresentDivision;

    @NotNull
    @Column(name = "lcg_present_high_tier")
    private String lcgPresentHighTier;

    @NotNull
    @Column(name = "lcg_present_high_division")
    private String lcgPresentHighDivision;

    @NotNull
    @Column(name = "lcg_previous_tier")
    private String lcgPreviousTier;

    @NotNull
    @Column(name = "lcg_previous_division")
    private String lcgPreviousDivision;

    @NotNull
    @Column(name = "lcg_previous_high_tier")
    private String lcgPreviousHighTier;

    @NotNull
    @Column(name = "lcg_previous_high_division")
    private String lcgPreviousHighDivision;

    @NotNull
    @Column(name = "lcg_ai_summary_content")
    private String lcgAiSummaryContent;

    @NotNull
    @Column(name = "lcg_ai_summary_verify")
    private String lcgAiSummaryVerify;

    @NotNull
    @Column(name = "lcg_player_hide")
    private String lcgPlayerHide;

    public LCG_Player_Data playerDataUpdate(Player playerData, RankData rankData) {
        this.lcgSummonerNickname = playerData.getGameName() + "#" + playerData.getTagLine();
        this.lcgSummonerId = playerData.getSummonerId();
        this.lcgSummonerName = playerData.getGameName();
        this.lcgSummonerTag = playerData.getTagLine();
        this.lcgSummonerIcon = playerData.getProfileIcon();
        this.lcgRankWin = rankData.getWins();
        this.lcgRankPoint = rankData.getPoints();
        this.lcgPresentTier = rankData.getPresentTier();
        this.lcgPresentDivision = rankData.getPresentDivision();
        this.lcgPresentHighTier = rankData.getPresentHighestTier();
        this.lcgPresentHighDivision = rankData.getPresentHighestDivision();
        this.lcgPreviousTier = rankData.getPreviousTier();
        this.lcgPreviousDivision = rankData.getPreviousDivision();
        this.lcgPreviousHighTier = rankData.getPreviousHighestTier();
        this.lcgPreviousHighDivision = rankData.getPreviousHighestDivision();
        return this;
    }

    public void aiSummaryUpdate() {
        this.lcgAiSummaryVerify = "N";
    }

    public LCG_Player_Data winningStreakUpdate(boolean win, int cnt) {
        if(win) {
            if(cnt < 0) {
                this.lcgWinningStreak = 1;
            } else {
                this.lcgWinningStreak += 1;
            }
        } else {
            if(cnt > 0) {
                this.lcgWinningStreak = -1;
            } else {
                this.lcgWinningStreak -= 1;
            }
        }
        return this;
    }

    @Builder
    public LCG_Player_Data(String lcgSummonerPuuid, String lcgPlayer, String lcgSummonerNickname,
                           Long lcgSummonerId, String lcgSummonerName, String lcgSummonerTag,
                           int lcgSummonerIcon, int lcgRankPoint, int lcgRankWin,
                           String lcgPresentTier, String lcgPresentDivision, String lcgPresentHighTier,
                           String lcgPresentHighDivision, String lcgPreviousTier, String lcgPreviousDivision,
                           String lcgPreviousHighTier, String lcgPreviousHighDivision,
                           String lcgAiSummaryContent, String lcgAiSummaryVerify, String lcgPlayerHide) {
        this.lcgSummonerPuuid = lcgSummonerPuuid;
        this.lcgPlayer = lcgPlayer;
        this.lcgSummonerNickname = lcgSummonerNickname;
        this.lcgSummonerId = lcgSummonerId;
        this.lcgSummonerName = lcgSummonerName;
        this.lcgSummonerTag = lcgSummonerTag;
        this.lcgSummonerIcon = lcgSummonerIcon;
        this.lcgRankWin = lcgRankWin;
        this.lcgRankPoint = lcgRankPoint;
        this.lcgPresentTier = lcgPresentTier;
        this.lcgPresentDivision = lcgPresentDivision;
        this.lcgPresentHighTier = lcgPresentHighTier;
        this.lcgPresentHighDivision = lcgPresentHighDivision;
        this.lcgPreviousTier = lcgPreviousTier;
        this.lcgPreviousDivision = lcgPreviousDivision;
        this.lcgPreviousHighTier = lcgPreviousHighTier;
        this.lcgPreviousHighDivision = lcgPreviousHighDivision;
        this.lcgAiSummaryContent = lcgAiSummaryContent;
        this.lcgAiSummaryVerify = lcgAiSummaryVerify;
        this.lcgPlayerHide = lcgPlayerHide;
    }
}
