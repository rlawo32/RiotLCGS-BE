package riot.lcgs.riotlcgsbe.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PlayerPointId implements Serializable {

    @Column(name = "lcg_game_id")
    private Long lcgGameId;

    @Column(name = "lcg_summoner_puuid")
    private String lcgSummonerPuuid;
}
