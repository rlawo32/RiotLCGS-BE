package riot.lcgs.riotlcgsbe.jpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "LCG_Match_Sub")
public class LCG_Match_Sub {

    @Id
    @Column(name = "row_num")
    private Long rowNum;

    @Column(name = "lcg_game_id")
    private Long lgcGameId;

    @Column(name = "lcg_participant_id")
    private int lgcParticipantId;

    @Column(name = "lcg_first_kill")
    private boolean lgcFirstKill;

    @Column(name = "lcg_first_tower")
    private boolean lgcFirstTower;

    @Column(name = "lcg_double_kill")
    private int lgcDoubleKill;

    @Column(name = "lcg_triple_kill")
    private int lgcTripleKill;

    @Column(name = "lcg_quadra_kill")
    private int lgcQuadraKill;

    @Column(name = "lcg_penta_kill")
    private int lgcPentaKill;

    @Column(name = "lcg_destroy_tower")
    private int lgcDestroyTower;

    @Column(name = "lcg_normal_ward")
    private int lgcNormalWard;

    @Column(name = "lcg_vision_ward")
    private int lgcVisionWard;

    @Column(name = "lcg_gold_total")
    private int lgcGoldTotal;

    @Column(name = "lcg_heal_total")
    private int lgcHealTotal;

    @Column(name = "lcg_damage_tower")
    private int lgcDamageTower;

    @Column(name = "lcg_crowd_time")
    private int lgcCrowdTime;
}
