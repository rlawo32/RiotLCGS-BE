package riot.lcgs.riotlcgsbe.jpa.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "LCG_Info_Maximum")
public class LCG_Info_Maximum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_num")
    private Long rowNum;

    @Column(name = "lcg_max_kill")
    private Integer lcgMaxKill;

    @Column(name = "lcg_max_death")
    private Integer lcgMaxDeath;

    @Column(name = "lcg_max_assist")
    private Integer lcgMaxAssist;

    @Column(name = "lcg_max_mvp")
    private Integer lcgMaxMvp;

    @Column(name = "lcg_max_ace")
    private Integer lcgMaxAce;

    @Column(name = "lcg_max_gold")
    private Integer lcgMaxGold;

    @Column(name = "lcg_max_cs")
    private Integer lcgMaxCs;

    @Column(name = "lcg_max_demolisher")
    private Integer lcgMaxDemolisher;

    @Column(name = "lcg_max_pinkward")
    private Integer lcgMaxPinkward;

    @Column(name = "lcg_max_vision")
    private Integer lcgMaxVision;

    @Column(name = "lcg_max_multikill")
    private Integer lcgMaxMultikill;

    @Column(name = "lcg_max_object")
    private Integer lcgMaxObject;

    @NotNull
    @Column(name = "lcg_max_top")
    private String lcgMaxTop;

    @NotNull
    @Column(name = "lcg_max_jug")
    private String lcgMaxJug;

    @NotNull
    @Column(name = "lcg_max_mid")
    private String lcgMaxMid;

    @NotNull
    @Column(name = "lcg_max_adc")
    private String lcgMaxAdc;

    @NotNull
    @Column(name = "lcg_max_sup")
    private String lcgMaxSup;

    @NotNull
    @Column(name = "lcg_update_date")
    private String lcgUpdateDate;

    @Builder
    public LCG_Info_Maximum(int lcgMaxKill, int lcgMaxDeath, int lcgMaxAssist, int lcgMaxMvp, int lcgMaxAce,
                            int lcgMaxGold, int lcgMaxCs, int lcgMaxDemolisher, int lcgMaxPinkward, int lcgMaxVision,
                            int lcgMaxMultikill, int lcgMaxObject, String lcgMaxTop, String lcgMaxJug, String lcgMaxMid,
                            String lcgMaxAdc, String lcgMaxSup, String lcgUpdateDate) {
        this.lcgMaxKill = lcgMaxKill;
        this.lcgMaxDeath = lcgMaxDeath;
        this.lcgMaxAssist = lcgMaxAssist;
        this.lcgMaxMvp = lcgMaxMvp;
        this.lcgMaxAce = lcgMaxAce;
        this.lcgMaxGold = lcgMaxGold;
        this.lcgMaxCs = lcgMaxCs;
        this.lcgMaxDemolisher = lcgMaxDemolisher;
        this.lcgMaxPinkward = lcgMaxPinkward;
        this.lcgMaxVision = lcgMaxVision;
        this.lcgMaxMultikill = lcgMaxMultikill;
        this.lcgMaxObject = lcgMaxObject;
        this.lcgMaxTop = lcgMaxTop;
        this.lcgMaxJug = lcgMaxJug;
        this.lcgMaxMid = lcgMaxMid;
        this.lcgMaxAdc = lcgMaxAdc;
        this.lcgMaxSup = lcgMaxSup;
        this.lcgUpdateDate = lcgUpdateDate;
    }
}
