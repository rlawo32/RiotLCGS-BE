package riot.lcgs.riotlcgsbe.jpa.domain;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "LCG_Match_Etc")
public class LCG_Match_Etc {

    @Id
    @NotNull
    @Column(name = "lcg_version")
    private String lcgVersion;

    @NotNull
    @Column(name = "lcg_update_date")
    private String lcgUpdateDate;

    @NotNull
    @Column(name = "lcg_cdn")
    private String lcgCdn;

    @NotNull
    @Column(name = "lcg_lang")
    private String lcgLang;

    @NotNull
    @Column(name = "lcg_main_ver")
    private String lcgMainVer;

    @NotNull
    @Column(name = "lcg_item_ver")
    private String lcgItemVer;

    @NotNull
    @Column(name = "lcg_rune_ver")
    private String lcgRuneVer;

    @NotNull
    @Column(name = "lcg_mastery_ver")
    private String lcgMasteryVer;

    @NotNull
    @Column(name = "lcg_summoner_ver")
    private String lcgSummonerVer;

    @NotNull
    @Column(name = "lcg_champion_ver")
    private String lcgChampionVer;

    @NotNull
    @Column(name = "lcg_image_main")
    private String lcgImageMain;

    @NotNull
    @Column(name = "lcg_image_sub")
    private String lcgImageSub;

    @Builder
    public LCG_Match_Etc(Long lcgVersion, String lcgUpdateDate, String lcgCdn, String lcgLang, 
                         int lcgMainVer, int lcgItemVer, String lcgRuneVer, String lcgMasteryVer, 
                         String lcgSummonerVer, String lcgChampionVer, String lcgImageMain, String lcgImageSub) {
        this.lcgVersion = lcgVersion;
        this.lcgUpdateDate = lcgUpdateDate;
        this.lcgCdn = lcgCdn;
        this.lcgLang = lcgLang;
        this.lcgMainVer = lcgMainVer;
        this.lcgItemVer = lcgItemVer;
        this.lcgRuneVer = lcgRuneVer;
        this.lcgMasteryVer = lcgMasteryVer;
        this.lcgSummonerVer = lcgSummonerVer;
        this.lcgChampionVer = lcgChampionVer;
        this.lcgImageMain = lcgImageMain;
        this.lcgVerMastery = lcgVerMastery;
        this.lcgImageSub = lcgImageSub;
    }
}
