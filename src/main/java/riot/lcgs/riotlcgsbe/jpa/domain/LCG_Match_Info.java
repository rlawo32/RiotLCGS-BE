package riot.lcgs.riotlcgsbe.jpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "LCG_Match_Info")
public class LCG_Match_Info {

    @Id
    @Column(name = "lcg_game_id")
    private Long lgcGameId;

    @Column(name = "lcg_game_date")
    private String lgcGameDate;

    @Column(name = "lcg_game_mode")
    private String lgcGameMode;

    @Column(name = "lcg_game_type")
    private String lgcGameType;

    @Column(name = "lcg_game_duration")
    private int lgcGameDuration;

    @Column(name = "lcg_game_map")
    private int lgcGameMap;

    @Builder
    public LCG_Match_Info(Long lgcGameId, String lgcGameDate, String lgcGameMode,
                          String lgcGameType, int lgcGameDuration, int lgcGameMap) {
        this.lgcGameId = lgcGameId;
        this.lgcGameDate = lgcGameDate;
        this.lgcGameMode = lgcGameMode;
        this.lgcGameType = lgcGameType;
        this.lgcGameDuration = lgcGameDuration;
        this.lgcGameMap = lgcGameMap;
    }
}
