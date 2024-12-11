package riot.lcgs.riotlcgsbe.jpa.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "LCG_Match_Main")
public class LCG_Match_Main {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_num")
    private Long rowNum;

    @Column(name = "lcg_game_id")
    private Long lgcGameId;

    @Column(name = "lcg_participant_id")
    private int lgcParticipantId;

    @Column(name = "lcg_team_id")
    private int lgcTeamId;

    @Column(name = "lcg_summoner_id")
    private Long lgcSummonerId;

    @Column(name = "lcg_summoner_name")
    private String lgcSummonerName;

    @Column(name = "lcg_summoner_tag")
    private String lgcSummonerTag;

    @Column(name = "lcg_champion_id")
    private int lgcChampionId;

    @Column(name = "lcg_champion_name")
    private String lgcChampionName;

    @Column(name = "lcg_champion_level")
    private int lgcChampionLevel;

    @Column(name = "lcg_spell_id_1")
    private int lgcSpellId1;

    @Column(name = "lcg_spell_id_2")
    private int lgcSpellId2;

    @Column(name = "lcg_perk_id_1")
    private int lgcPerkId1;

    @Column(name = "lcg_perk_id_2")
    private int lgcPerkId2;

    @Column(name = "lcg_item_id_1")
    private int lgcItemId1;

    @Column(name = "lcg_item_id_2")
    private int lgcItemId2;

    @Column(name = "lcg_item_id_3")
    private int lgcItemId3;

    @Column(name = "lcg_item_id_4")
    private int lgcItemId4;

    @Column(name = "lcg_item_id_5")
    private int lgcItemId5;

    @Column(name = "lcg_item_id_6")
    private int lgcItemId6;

    @Column(name = "lcg_item_id_7")
    private int lgcItemId7;

    @Column(name = "lcg_kill_count")
    private int lgcKillCount;

    @Column(name = "lcg_death_count")
    private int lgcDeathCount;

    @Column(name = "lcg_assist_count")
    private int lgcAssistCount;

    @Column(name = "lcg_damage_total")
    private int lgcDamageTotal;

    @Column(name = "lcg_damage_taken")
    private int lgcDamageTaken;

    @Column(name = "lcg_minion_count")
    private int lgcMinionCount;

    @Column(name = "lcg_jungle_count")
    private int lgcJungleCount;

    @Column(name = "lcg_vision_score")
    private int lgcVisionScore;

    @Builder
    public LCG_Match_Main(Long lgcGameId, int lgcParticipantId, int lgcTeamId, Long lgcSummonerId,
                          String lgcSummonerName, String lgcSummonerTag, int lgcChampionId,
                          String lgcChampionName, int lgcChampionLevel, int lgcSpellId1, int lgcSpellId2,
                          int lgcPerkId1, int lgcPerkId2, int lgcItemId1, int lgcItemId2, int lgcItemId3,
                          int lgcItemId4, int lgcItemId5, int lgcItemId6, int lgcItemId7, int lgcKillCount,
                          int lgcDeathCount, int lgcAssistCount, int lgcDamageTotal, int lgcDamageTaken,
                          int lgcMinionCount, int lgcJungleCount, int lgcVisionScore) {
        this.lgcGameId = lgcGameId;
        this.lgcParticipantId = lgcParticipantId;
        this.lgcTeamId = lgcTeamId;
        this.lgcSummonerId = lgcSummonerId;
        this.lgcSummonerName = lgcSummonerName;
        this.lgcSummonerTag = lgcSummonerTag;
        this.lgcChampionId = lgcChampionId;
        this.lgcChampionName = lgcChampionName;
        this.lgcChampionLevel = lgcChampionLevel;
        this.lgcSpellId1 = lgcSpellId1;
        this.lgcSpellId2 = lgcSpellId2;
        this.lgcPerkId1 = lgcPerkId1;
        this.lgcPerkId2 = lgcPerkId2;
        this.lgcItemId1 = lgcItemId1;
        this.lgcItemId2 = lgcItemId2;
        this.lgcItemId3 = lgcItemId3;
        this.lgcItemId4 = lgcItemId4;
        this.lgcItemId5 = lgcItemId5;
        this.lgcItemId6 = lgcItemId6;
        this.lgcItemId7 = lgcItemId7;
        this.lgcKillCount = lgcKillCount;
        this.lgcDeathCount = lgcDeathCount;
        this.lgcAssistCount = lgcAssistCount;
        this.lgcDamageTotal = lgcDamageTotal;
        this.lgcDamageTaken = lgcDamageTaken;
        this.lgcMinionCount = lgcMinionCount;
        this.lgcJungleCount = lgcJungleCount;
        this.lgcVisionScore = lgcVisionScore;
    }
}
