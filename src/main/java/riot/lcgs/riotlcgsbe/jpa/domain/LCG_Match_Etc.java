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
    @Column(name = "lcg_update_player")
    private String lcgUpdatePlayer;

    @NotNull
    @Column(name = "lcg_update_data")
    private String lcgUpdateData;

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
    @Column(name = "lcg_main_image")
    private String lcgMainImage;

    @NotNull
    @Column(name = "lcg_sub_image")
    private String lcgSubImage;

    @Builder
    public LCG_Match_Etc(String lcgVersion, String lcgUpdateDate, String lcgUpdatePlayer, String lcgUpdateData,
                         String lcgCdn, String lcgLang, String lcgMainVer, String lcgItemVer, String lcgRuneVer, 
                         String lcgMasteryVer, String lcgSummonerVer, String lcgChampionVer, String lcgMainImage, String lcgSubImage) {
        this.lcgVersion = lcgVersion;
        this.lcgUpdateDate = lcgUpdateDate;
        this.lcgUpdatePlayer = lcgUpdatePlayer;
        this.lcgUpdateData = lcgUpdateData;
        this.lcgCdn = lcgCdn;
        this.lcgLang = lcgLang;
        this.lcgMainVer = lcgMainVer;
        this.lcgItemVer = lcgItemVer;
        this.lcgRuneVer = lcgRuneVer;
        this.lcgMasteryVer = lcgMasteryVer;
        this.lcgSummonerVer = lcgSummonerVer;
        this.lcgChampionVer = lcgChampionVer;
        this.lcgMainImage = lcgMainImage;
        this.lcgSubImage = lcgSubImage;
    }
}
