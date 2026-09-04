package riot.lcgs.riotlcgsbe.jpa.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "LCG_Info_Champion")
public class LCG_Info_Champion {

    @Id
    @NotNull
    @Column(name = "lcg_champion_id")
    private Long lcgChampionId;

    @NotNull
    @Column(name = "lcg_champion_name")
    private String lcgChampionName;

    @NotNull
    @Column(name = "lcg_champion_name_ko")
    private String lcgChampionNameKo;

    @NotNull
    @Column(name = "lcg_update_date")
    private String lcgUpdateDate;

    @Builder
    public LCG_Info_Champion(Long lcgChampionId, String lcgChampionName, String lcgChampionNameKo, String lcgUpdateDate) {
        this.lcgChampionId = lcgChampionId;
        this.lcgChampionName = lcgChampionName;
        this.lcgChampionNameKo = lcgChampionNameKo;
        this.lcgUpdateDate = lcgUpdateDate;
    }
}
