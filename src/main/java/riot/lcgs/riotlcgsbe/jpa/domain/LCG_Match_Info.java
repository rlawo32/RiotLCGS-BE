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

    @Column(name = "lcg_ver_main")
    private String lgcVerMain;

    @Column(name = "lcg_ver_cdn")
    private String lgcVerCdn;

    @Column(name = "lcg_ver_lang")
    private String lgcVerLang;

    @Column(name = "lcg_ver_item")
    private String lgcVerItem;

    @Column(name = "lcg_ver_rune")
    private String lgcVerRune;

    @Column(name = "lcg_ver_mastery")
    private String lgcVerMastery;

    @Column(name = "lcg_ver_summoner")
    private String lgcVerSummoner;

    @Column(name = "lcg_ver_champion")
    private String lgcVerChampion;

    @Builder
    public LCG_Match_Info(Long lgcGameId, String lgcGameDate, String lgcGameMode,
                          String lgcGameType, int lgcGameDuration, int lgcGameMap,
                          String lgcVerMain, String lgcVerCdn, String lgcVerLang,
                          String lgcVerItem, String lgcVerRune, String lgcVerMastery,
                          String lgcVerSummoner, String lgcVerChampion) {
        this.lgcGameId = lgcGameId;
        this.lgcGameDate = lgcGameDate;
        this.lgcGameMode = lgcGameMode;
        this.lgcGameType = lgcGameType;
        this.lgcGameDuration = lgcGameDuration;
        this.lgcGameMap = lgcGameMap;
        this.lgcVerMain = lgcVerMain;
        this.lgcVerCdn = lgcVerCdn;
        this.lgcVerLang = lgcVerLang;
        this.lgcVerItem = lgcVerItem;
        this.lgcVerRune = lgcVerRune;
        this.lgcVerMastery = lgcVerMastery;
        this.lgcVerSummoner = lgcVerSummoner;
        this.lgcVerChampion = lgcVerChampion;
    }
}
