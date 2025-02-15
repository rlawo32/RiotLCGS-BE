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

import static riot.lcgs.riotlcgsbe.util.ValidationTool.*;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "LCG_Player_Data")
public class LCG_Player_Data {

    @Id
    @Column(name = "lcg_summoner_puuid")
    private String lcgSummonerPuuid;

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


    public LCG_Player_Data playerDataUpdate(Player playerData, RankData rankData) {
        this.lcgSummonerNickname = validationChkString(playerData.getGameName()) + "#" + validationChkString(playerData.getTagLine());
        this.lcgSummonerId = validationChkLong(playerData.getSummonerId());
        this.lcgSummonerName = validationChkString(playerData.getGameName());
        this.lcgSummonerTag = validationChkString(playerData.getTagLine());
        this.lcgSummonerIcon = validationChkInteger(playerData.getProfileIcon());
        this.lcgRankWin = validationChkInteger(rankData.getWins());
        this.lcgRankPoint = validationChkInteger(rankData.getPoints());
        this.lcgPresentTier = validationChkString(rankData.getPresentTier());
        this.lcgPresentDivision = validationChkString(rankData.getPresentDivision());
        this.lcgPresentHighTier = validationChkString(rankData.getPresentHighestTier());
        this.lcgPresentHighDivision = validationChkString(rankData.getPresentHighestDivision());
        this.lcgPreviousTier = validationChkString(rankData.getPreviousTier());
        this.lcgPreviousDivision = validationChkString(rankData.getPreviousDivision());
        this.lcgPreviousHighTier = validationChkString(rankData.getPreviousHighestTier());
        this.lcgPreviousHighDivision = validationChkString(rankData.getPreviousHighestDivision());
        return this;
    }

    @Builder
    public LCG_Player_Data(String lcgSummonerPuuid, String lcgPlayer, String lcgSummonerNickname,
                           Long lcgSummonerId, String lcgSummonerName, String lcgSummonerTag,
                           int lcgSummonerIcon, int lcgRankPoint, int lcgRankWin,
                           String lcgPresentTier, String lcgPresentDivision, String lcgPresentHighTier,
                           String lcgPresentHighDivision, String lcgPreviousTier, String lcgPreviousDivision,
                           String lcgPreviousHighTier, String lcgPreviousHighDivision) {
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
    }
}
