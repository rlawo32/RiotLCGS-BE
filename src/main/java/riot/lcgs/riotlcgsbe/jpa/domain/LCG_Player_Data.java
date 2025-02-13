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
    @Column(name = "lcg_rank_play")
    private String lcgRankPlay;

    @NotNull
    @Column(name = "lcg_rank_tier")
    private String lcgRankTier;

    @NotNull
    @Column(name = "lcg_rank_point")
    private int lcgRankPoint;

    @NotNull
    @Column(name = "lcg_rank_win")
    private int lcgRankWin;

    @NotNull
    @Column(name = "lcg_rank_fail")
    private int lcgRankFail;


    public LCG_Player_Data playerDataUpdate(Player playerData) {
        this.lcgSummonerNickname = playerData.getGameName() + "#" + playerData.getTagLine();
        this.lcgSummonerId = playerData.getSummonerId();
        this.lcgSummonerName = playerData.getGameName();
        this.lcgSummonerTag = playerData.getTagLine();
        this.lcgSummonerIcon = playerData.getProfileIcon();
        this.lcgRankPlay = "";
        this.lcgRankTier = "";
        this.lcgRankPoint = 0;
        this.lcgRankWin = 0;
        this.lcgRankFail = 0;
        return this;
    }

    @Builder
    public LCG_Player_Data(String lcgSummonerPuuid, String lcgPlayer, String lcgSummonerNickname,
                           Long lcgSummonerId, String lcgSummonerName, String lcgSummonerTag,
                           int lcgSummonerIcon, String lcgRankPlay, String lcgRankTier,
                           int lcgRankPoint, int lcgRankWin, int lcgRankFail) {
        this.lcgSummonerPuuid = lcgSummonerPuuid;
        this.lcgPlayer = lcgPlayer;
        this.lcgSummonerNickname = lcgSummonerNickname;
        this.lcgSummonerId = lcgSummonerId;
        this.lcgSummonerName = lcgSummonerName;
        this.lcgSummonerTag = lcgSummonerTag;
        this.lcgSummonerIcon = lcgSummonerIcon;
        this.lcgRankPlay = lcgRankPlay;
        this.lcgRankTier = lcgRankTier;
        this.lcgRankPoint = lcgRankPoint;
        this.lcgRankWin = lcgRankWin;
        this.lcgRankFail = lcgRankFail;
    }
}
