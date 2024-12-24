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
    private Long lcgGameId;

    @Column(name = "lcg_game_date")
    private String lcgGameDate;

    @Column(name = "lcg_game_mode")
    private String lcgGameMode;

    @Column(name = "lcg_game_type")
    private String lcgGameType;

    @Column(name = "lcg_game_duration")
    private int lcgGameDuration;

    @Column(name = "lcg_game_map")
    private int lcgGameMap;

    @Column(name = "lcg_ver_main")
    private String lcgVerMain;

    @Column(name = "lcg_ver_cdn")
    private String lcgVerCdn;

    @Column(name = "lcg_ver_lang")
    private String lcgVerLang;

    @Column(name = "lcg_ver_item")
    private String lcgVerItem;

    @Column(name = "lcg_ver_rune")
    private String lcgVerRune;

    @Column(name = "lcg_ver_mastery")
    private String lcgVerMastery;

    @Column(name = "lcg_ver_summoner")
    private String lcgVerSummoner;

    @Column(name = "lcg_ver_champion")
    private String lcgVerChampion;

    @Column(name = "lcg_max_damage_total")
    private int lcgMaxDamageTotal;

    @Column(name = "lcg_max_damage_taken")
    private int lcgMaxDamageTaken;

    @Builder
    public LCG_Match_Info(Long lcgGameId, String lcgGameDate, String lcgGameMode,
                          String lcgGameType, int lcgGameDuration, int lcgGameMap,
                          String lcgVerMain, String lcgVerCdn, String lcgVerLang,
                          String lcgVerItem, String lcgVerRune, String lcgVerMastery,
                          String lcgVerSummoner, String lcgVerChampion,
                          int lcgMaxDamageTotal, int lcgMaxDamageTaken) {
        this.lcgGameId = lcgGameId;
        this.lcgGameDate = lcgGameDate;
        this.lcgGameMode = lcgGameMode;
        this.lcgGameType = lcgGameType;
        this.lcgGameDuration = lcgGameDuration;
        this.lcgGameMap = lcgGameMap;
        this.lcgVerMain = lcgVerMain;
        this.lcgVerCdn = lcgVerCdn;
        this.lcgVerLang = lcgVerLang;
        this.lcgVerItem = lcgVerItem;
        this.lcgVerRune = lcgVerRune;
        this.lcgVerMastery = lcgVerMastery;
        this.lcgVerSummoner = lcgVerSummoner;
        this.lcgVerChampion = lcgVerChampion;
        this.lcgMaxDamageTotal = lcgMaxDamageTotal;
        this.lcgMaxDamageTaken = lcgMaxDamageTaken;
    }
}
