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
@Table(name = "LCG_Match_Main")
public class LCG_Match_Main {

    @Id
    @Column(name = "row_num")
    private Long rowNum;

    @Column(name = "lcg_game_id")
    private Long lgcGameId;

    @Column(name = "lcg_participant_id")
    private int lgcParticipantId;

    @Column(name = "lcg_team_id")
    private String lgcTeamId;

    @Column(name = "lcg_summoner_id")
    private Long lgcSummonerId;

    @Column(name = "lcg_summoner_name")
    private String lgcSummonerName;

    @Column(name = "lcg_summoner_tag")
    private String lgcSummonerTag;

    @Column(name = "lcg_summoner_level")
    private String lgcSummonerLevel;

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
}
