package riot.lcgs.riotlcgsbe.jpa.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Column(name = "lcg_game_id")
    private Long lcgGameId;

    @NotNull
    @Column(name = "lcg_participant_id")
    private int lcgParticipantId;

    @NotNull
    @Column(name = "lcg_summoner_puuid")
    private String lcgSummonerPuuid;

    @NotNull
    @Column(name = "lcg_first_kill", length = 1)
    private String lcgFirstKill;

    @NotNull
    @Column(name = "lcg_first_tower", length = 1)
    private String lcgFirstTower;

    @NotNull
    @Column(name = "lcg_double_kill")
    private int lcgDoubleKill;

    @NotNull
    @Column(name = "lcg_triple_kill")
    private int lcgTripleKill;

    @NotNull
    @Column(name = "lcg_quadra_kill")
    private int lcgQuadraKill;

    @NotNull
    @Column(name = "lcg_penta_kill")
    private int lcgPentaKill;

    @NotNull
    @Column(name = "lcg_normal_ward")
    private int lcgNormalWard;

    @NotNull
    @Column(name = "lcg_vision_ward")
    private int lcgVisionWard;

    @NotNull
    @Column(name = "lcg_destroy_ward")
    private int lcgDestroyWard;

    @NotNull
    @Column(name = "lcg_gold_total")
    private int lcgGoldTotal;

    @NotNull
    @Column(name = "lcg_heal_total")
    private int lcgHealTotal;

    @NotNull
    @Column(name = "lcg_crowd_time")
    private int lcgCrowdTime;

    @NotNull
    @Column(name = "lcg_destroy_tower")
    private int lcgDestroyTower;

    @NotNull
    @Column(name = "lcg_damage_tower")
    private int lcgDamageTower;

    @NotNull
    @Column(name = "lcg_destroy_inhibitor")
    private int lcgDestroyInhibitor;

    @NotNull
    @Column(name = "lcg_damage_per_minute")
    private double lcgDamagePerMinute;

    @NotNull
    @Column(name = "lcg_gold_per_minute")
    private double lcgGoldPerMinute;

    @NotNull
    @Column(name = "lcg_damage_per_gold")
    private double lcgDamagePerGold;

    @NotNull
    @Column(name = "lcg_mvp_rank")
    private int lcgMvpRank;

    @Builder
    public LCG_Match_Sub(Long lcgGameId, int lcgParticipantId, String lcgSummonerPuuid, String lcgFirstKill, String lcgFirstTower,
                         int lcgDoubleKill, int lcgTripleKill, int lcgQuadraKill, int lcgPentaKill, int lcgNormalWard, 
                         int lcgVisionWard, int lcgDestroyWard, int lcgGoldTotal, int lcgHealTotal, int lcgCrowdTime, 
                         int lcgDestroyTower, int lcgDamageTower, int lcgDestroyInhibitor, double lcgDamagePerMinute,
                         double lcgGoldPerMinute, double lcgDamagePerGold, int lcgMvpRank) {
        this.lcgGameId = lcgGameId;
        this.lcgParticipantId = lcgParticipantId;
        this.lcgSummonerPuuid = lcgSummonerPuuid;
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
        this.lcgDestroyInhibitor = lcgDestroyInhibitor;
        this.lcgDamagePerMinute = lcgDamagePerMinute;
        this.lcgGoldPerMinute = lcgGoldPerMinute;
        this.lcgDamagePerGold = lcgDamagePerGold;
        this.lcgMvpRank = lcgMvpRank;
    }
}
