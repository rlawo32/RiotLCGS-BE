package riot.lcgs.riotlcgsbe.jpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import riot.lcgs.riotlcgsbe.web.dto.object.Stats;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "LCG_Player_Statistics")
public class LCG_Player_Statistics {

    @Id
    @Column(name = "lcg_puuid")
    private String lcgPuuid;

    @NotNull
    @Column(name = "lcg_player")
    private String lcgPlayer;

    @NotNull
    @Column(name = "lcg_nickname")
    private String lcgNickname;

    @NotNull
    @Column(name = "lcg_count_kill")
    private Long lcgCountKill;

    @NotNull
    @Column(name = "lcg_count_death")
    private Long lcgCountDeath;

    @NotNull
    @Column(name = "lcg_count_assist")
    private Long lcgCountAssist;

    @NotNull
    @Column(name = "lcg_count_tower")
    private Long lcgCountTower;

    @NotNull
    @Column(name = "lcg_count_inhibitor")
    private Long lcgCountInhibitor;

    @NotNull
    @Column(name = "lcg_count_dragon")
    private Long lcgCountDragon;

    @NotNull
    @Column(name = "lcg_count_baron")
    private Long lcgCountBaron;

    @NotNull
    @Column(name = "lcg_count_damage")
    private Long lcgCountDamage;

    @NotNull
    @Column(name = "lcg_count_taken")
    private Long lcgCountTaken;

    @NotNull
    @Column(name = "lcg_count_gold")
    private Long lcgCountGold;

    @NotNull
    @Column(name = "lcg_count_ccing")
    private Long lcgCountCCing;

    @NotNull
    @Column(name = "lcg_count_minion")
    private Long lcgCountMinion;

    @NotNull
    @Column(name = "lcg_count_jungle")
    private Long lcgCountJungle;

    @NotNull
    @Column(name = "lcg_count_ward_kill")
    private Long lcgCountWardKill;

    @NotNull
    @Column(name = "lcg_count_ward_placed")
    private Long lcgCountWardPlaced;

    @NotNull
    @Column(name = "lcg_count_vision_ward")
    private Long lcgCountVisionWard;

    @NotNull
    @Column(name = "lcg_count_vision_score")
    private Long lcgCountVisionScore;

    @NotNull
    @Column(name = "lcg_count_double_kill")
    private Long lcgCountDoubleKill;

    @NotNull
    @Column(name = "lcg_count_triple_kill")
    private Long lcgCountTripleKill;

    @NotNull
    @Column(name = "lcg_count_quadra_kill")
    private Long lcgCountQuadraKill;

    @NotNull
    @Column(name = "lcg_count_penta_kill")
    private Long lcgCountPentaKill;

    public LCG_Player_Statistics playerDataCounting(Stats statsData) {
        this.lcgCountKill += (long) statsData.getKills();
        this.lcgCountDeath += (long) statsData.getDeaths();
        this.lcgCountAssist += (long) statsData.getAssists();
        this.lcgCountTower += (long) statsData.getTurretKills();
        this.lcgCountInhibitor += (long) statsData.getInhibitorKills();
//        this.lcgCountDragon += (long) statsData.;
//        this.lcgCountBaron += (long) statsData.;
        this.lcgCountDamage += (long) statsData.getTotalDamageDealtToChampions();
        this.lcgCountTaken += (long) statsData.getTotalDamageTaken();
        this.lcgCountGold += (long) statsData.getGoldEarned();
        this.lcgCountCCing += (long) statsData.getTimeCCingOthers();
        this.lcgCountMinion += (long) statsData.getTotalMinionsKilled();
        this.lcgCountJungle += (long) statsData.getNeutralMinionsKilled();
        this.lcgCountWardKill += (long) statsData.getWardsKilled();
        this.lcgCountWardPlaced += (long) statsData.getWardsPlaced();
        this.lcgCountVisionWard += (long) statsData.getVisionWardsBoughtInGame();
        this.lcgCountVisionScore += (long) statsData.getVisionScore();
        this.lcgCountDoubleKill += (long) statsData.getDoubleKills();
        this.lcgCountTripleKill += (long) statsData.getTripleKills();
        this.lcgCountQuadraKill += (long) statsData.getQuadraKills();
        this.lcgCountPentaKill += (long) statsData.getPentaKills();
        return this;
    }

    @Builder
    public LCG_Player_Statistics(String lcgPuuid, String lcgPlayer, String lcgNickname, Long lcgCountKill,
                                 Long lcgCountDeath, Long lcgCountAssist, Long lcgCountTower, Long lcgCountInhibitor,
                                 Long lcgCountDragon, Long lcgCountBaron, Long lcgCountDamage, Long lcgCountTaken,
                                 Long lcgCountGold, Long lcgCountCCing, Long lcgCountMinion, Long lcgCountJungle,
                                 Long lcgCountWardKill, Long lcgCountWardPlaced, Long lcgCountVisionWard,
                                 Long lcgCountVisionScore, Long lcgCountDoubleKill, Long lcgCountTripleKill,
                                 Long lcgCountQuadraKill, Long lcgCountPentaKill) {
        this.lcgPuuid = lcgPuuid;
        this.lcgPlayer = lcgPlayer;
        this.lcgNickname = lcgNickname;
        this.lcgCountKill = lcgCountKill;
        this.lcgCountDeath = lcgCountDeath;
        this.lcgCountAssist = lcgCountAssist;
        this.lcgCountTower = lcgCountTower;
        this.lcgCountInhibitor = lcgCountInhibitor;
        this.lcgCountDragon = lcgCountDragon;
        this.lcgCountBaron = lcgCountBaron;
        this.lcgCountDamage = lcgCountDamage;
        this.lcgCountTaken = lcgCountTaken;
        this.lcgCountGold = lcgCountGold;
        this.lcgCountCCing = lcgCountCCing;
        this.lcgCountMinion = lcgCountMinion;
        this.lcgCountJungle = lcgCountJungle;
        this.lcgCountWardKill = lcgCountWardKill;
        this.lcgCountWardPlaced = lcgCountWardPlaced;
        this.lcgCountVisionWard = lcgCountVisionWard;
        this.lcgCountVisionScore = lcgCountVisionScore;
        this.lcgCountDoubleKill = lcgCountDoubleKill;
        this.lcgCountTripleKill = lcgCountTripleKill;
        this.lcgCountQuadraKill = lcgCountQuadraKill;
        this.lcgCountPentaKill = lcgCountPentaKill;
    }
}
