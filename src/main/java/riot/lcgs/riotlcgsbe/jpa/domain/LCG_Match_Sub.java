package riot.lcgs.riotlcgsbe.jpa.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "LCG_Match_Sub")
public class LCG_Match_Sub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_num")
    private Long rowNum;

    @Column(name = "lcg_game_id")
    private Long lgcGameId;

    @Column(name = "lcg_participant_id")
    private int lgcParticipantId;

    @Column(name = "lcg_first_kill", length = 1)
    private String lgcFirstKill;

    @Column(name = "lcg_first_tower", length = 1)
    private String lgcFirstTower;

    @Column(name = "lcg_double_kill")
    private int lgcDoubleKill;

    @Column(name = "lcg_triple_kill")
    private int lgcTripleKill;

    @Column(name = "lcg_quadra_kill")
    private int lgcQuadraKill;

    @Column(name = "lcg_penta_kill")
    private int lgcPentaKill;

    @Column(name = "lcg_normal_ward")
    private int lgcNormalWard;

    @Column(name = "lcg_vision_ward")
    private int lgcVisionWard;

    @Column(name = "lcg_gold_total")
    private int lgcGoldTotal;

    @Column(name = "lcg_heal_total")
    private int lgcHealTotal;

    @Column(name = "lcg_crowd_time")
    private int lgcCrowdTime;

    @Column(name = "lcg_destroy_tower")
    private int lgcDestroyTower;

    @Column(name = "lcg_damage_tower")
    private int lgcDamageTower;

    @Builder
    public LCG_Match_Sub(Long lgcGameId, int lgcParticipantId, String lgcFirstKill, String lgcFirstTower,
                         int lgcDoubleKill, int lgcTripleKill, int lgcQuadraKill, int lgcPentaKill,
                         int lgcNormalWard, int lgcVisionWard, int lgcGoldTotal, int lgcHealTotal,
                         int lgcCrowdTime, int lgcDestroyTower, int lgcDamageTower) {
        this.lgcGameId = lgcGameId;
        this.lgcParticipantId = lgcParticipantId;
        this.lgcFirstKill = lgcFirstKill;
        this.lgcFirstTower = lgcFirstTower;
        this.lgcDoubleKill = lgcDoubleKill;
        this.lgcTripleKill = lgcTripleKill;
        this.lgcQuadraKill = lgcQuadraKill;
        this.lgcPentaKill = lgcPentaKill;
        this.lgcNormalWard = lgcNormalWard;
        this.lgcVisionWard = lgcVisionWard;
        this.lgcGoldTotal = lgcGoldTotal;
        this.lgcHealTotal = lgcHealTotal;
        this.lgcCrowdTime = lgcCrowdTime;
        this.lgcDestroyTower = lgcDestroyTower;
        this.lgcDamageTower = lgcDamageTower;
    }
}
