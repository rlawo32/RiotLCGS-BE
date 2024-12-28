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
    private Long lcgGameId;

    @Column(name = "lcg_participant_id")
    private int lcgParticipantId;

    @Column(name = "lcg_first_kill", length = 1)
    private String lcgFirstKill;

    @Column(name = "lcg_first_tower", length = 1)
    private String lcgFirstTower;

    @Column(name = "lcg_double_kill")
    private int lcgDoubleKill;

    @Column(name = "lcg_triple_kill")
    private int lcgTripleKill;

    @Column(name = "lcg_quadra_kill")
    private int lcgQuadraKill;

    @Column(name = "lcg_penta_kill")
    private int lcgPentaKill;

    @Column(name = "lcg_normal_ward")
    private int lcgNormalWard;

    @Column(name = "lcg_vision_ward")
    private int lcgVisionWard;

    @Column(name = "lcg_destroy_ward")
    private int lcgDestroyWard;

    @Column(name = "lcg_gold_total")
    private int lcgGoldTotal;

    @Column(name = "lcg_heal_total")
    private int lcgHealTotal;

    @Column(name = "lcg_crowd_time")
    private int lcgCrowdTime;

    @Column(name = "lcg_destroy_tower")
    private int lcgDestroyTower;

    @Column(name = "lcg_damage_tower")
    private int lcgDamageTower;

    @Builder
    public LCG_Match_Sub(Long lcgGameId, int lcgParticipantId, String lcgFirstKill, String lcgFirstTower,
                         int lcgDoubleKill, int lcgTripleKill, int lcgQuadraKill, int lcgPentaKill,
                         int lcgNormalWard, int lcgVisionWard, int lcgDestroyWard, int lcgGoldTotal,
                         int lcgHealTotal, int lcgCrowdTime, int lcgDestroyTower, int lcgDamageTower) {
        this.lcgGameId = lcgGameId;
        this.lcgParticipantId = lcgParticipantId;
        this.lcgFirstKill = lcgFirstKill;
        this.lcgFirstTower = lcgFirstTower;
        this.lcgDoubleKill = lcgDoubleKill;
        this.lcgTripleKill = lcgTripleKill;
        this.lcgQuadraKill = lcgQuadraKill;
        this.lcgPentaKill = lcgPentaKill;
        this.lcgNormalWard = lcgNormalWard;
        this.lcgVisionWard = lcgVisionWard;
        this.lcgDestroyWard = lcgDestroyWard;
        this.lcgGoldTotal = lcgGoldTotal;
        this.lcgHealTotal = lcgHealTotal;
        this.lcgCrowdTime = lcgCrowdTime;
        this.lcgDestroyTower = lcgDestroyTower;
        this.lcgDamageTower = lcgDamageTower;
    }
}
