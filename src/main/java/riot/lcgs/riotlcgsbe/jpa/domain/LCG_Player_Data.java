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
import riot.lcgs.riotlcgsbe.web.dto.object.Stats;
import riot.lcgs.riotlcgsbe.web.dto.object.Teams;

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

    public LCG_Player_Data playerDataUpdate(Player playerData, String player) {
        this.lcgPlayer = player;
        this.lcgSummonerNickname = playerData.getGameName() + "#" + playerData.getTagLine();
        this.lcgSummonerId = playerData.getSummonerId();
        this.lcgSummonerName = playerData.getGameName();
        this.lcgSummonerTag = playerData.getTagLine();
        return this;
    }

    @Builder
    public LCG_Player_Data(String lcgSummonerPuuid, String lcgPlayer, String lcgSummonerNickname,
                           Long lcgSummonerId, String lcgSummonerName, String lcgSummonerTag) {
        this.lcgSummonerPuuid = lcgSummonerPuuid;
        this.lcgPlayer = lcgPlayer;
        this.lcgSummonerNickname = lcgSummonerNickname;
        this.lcgSummonerId = lcgSummonerId;
        this.lcgSummonerName = lcgSummonerName;
        this.lcgSummonerTag = lcgSummonerTag;
    }
}
