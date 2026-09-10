package riot.lcgs.riotlcgsbe.jpa.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import riot.lcgs.riotlcgsbe.jpa.PlayerPointId;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "LCG_Player_Point")
public class LCG_Player_Point {

    @EmbeddedId
    private PlayerPointId id;

    @NotNull
    @Column(name = "lcg_game_set")
    private String lcgGameSet;

    @Column(name = "lcg_point_change")
    private Long lcgPointChange;

    @Column(name = "lcg_point_total")
    private Long lcgPointTotal;

    @NotNull
    @Column(name = "lcg_point_result")
    private String lcgPointResult;

    @NotNull
    @Column(name = "lcg_update_date")
    private String lcgUpdateDate;

    @Builder
    public LCG_Player_Point(Long lcgGameId, String lcgSummonerPuuid, String lcgGameSet, Long lcgPointChange,
                            Long lcgPointTotal, String lcgPointResult, String lcgUpdateDate) {
        this.id = new PlayerPointId(lcgGameId, lcgSummonerPuuid);
        this.lcgGameSet = lcgGameSet;
        this.lcgPointChange = lcgPointChange;
        this.lcgPointTotal = lcgPointTotal;
        this.lcgPointResult = lcgPointResult;
        this.lcgUpdateDate = lcgUpdateDate;
    }
}
