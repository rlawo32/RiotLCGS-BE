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
    private Long lcgGameId;

    @Column(name = "lcg_participant_id")
    private int lcgParticipantId;

    @Column(name = "lcg_team_id")
    private int lcgTeamId;

    @Column(name = "lcg_summoner_id")
    private Long lcgSummonerId;

    @Column(name = "lcg_summoner_name")
    private String lcgSummonerName;

    @Column(name = "lcg_summoner_tag")
    private String lcgSummonerTag;

    @Column(name = "lcg_champion_id")
    private int lcgChampionId;

    @Column(name = "lcg_champion_name")
    private String lcgChampionName;

    @Column(name = "lcg_champion_level")
    private int lcgChampionLevel;

    @Column(name = "lcg_spell_name_1")
    private String lcgSpellName1;

    @Column(name = "lcg_spell_name_2")
    private String lcgSpellName2;

    @Column(name = "lcg_perk_name_1")
    private String lcgPerkName1;

    @Column(name = "lcg_perk_name_2")
    private String lcgPerkName2;

    @Column(name = "lcg_item_id_1")
    private int lcgItemId1;

    @Column(name = "lcg_item_id_2")
    private int lcgItemId2;

    @Column(name = "lcg_item_id_3")
    private int lcgItemId3;

    @Column(name = "lcg_item_id_4")
    private int lcgItemId4;

    @Column(name = "lcg_item_id_5")
    private int lcgItemId5;

    @Column(name = "lcg_item_id_6")
    private int lcgItemId6;

    @Column(name = "lcg_item_id_7")
    private int lcgItemId7;

    @Column(name = "lcg_kill_count")
    private int lcgKillCount;

    @Column(name = "lcg_death_count")
    private int lcgDeathCount;

    @Column(name = "lcg_assist_count")
    private int lcgAssistCount;

    @Column(name = "lcg_damage_total")
    private int lcgDamageTotal;

    @Column(name = "lcg_damage_taken")
    private int lcgDamageTaken;

    @Column(name = "lcg_minion_count")
    private int lcgMinionCount;

    @Column(name = "lcg_jungle_count")
    private int lcgJungleCount;

    @Column(name = "lcg_vision_score")
    private int lcgVisionScore;

    @Builder
    public LCG_Match_Main(Long lcgGameId, int lcgParticipantId, int lcgTeamId, Long lcgSummonerId,
                          String lcgSummonerName, String lcgSummonerTag, int lcgChampionId,
                          String lcgChampionName, int lcgChampionLevel, String lcgSpellName1, String lcgSpellName2,
                          String lcgPerkName1, String lcgPerkName2, int lcgItemId1, int lcgItemId2, int lcgItemId3,
                          int lcgItemId4, int lcgItemId5, int lcgItemId6, int lcgItemId7, int lcgKillCount,
                          int lcgDeathCount, int lcgAssistCount, int lcgDamageTotal, int lcgDamageTaken,
                          int lcgMinionCount, int lcgJungleCount, int lcgVisionScore) {
        this.lcgGameId = lcgGameId;
        this.lcgParticipantId = lcgParticipantId;
        this.lcgTeamId = lcgTeamId;
        this.lcgSummonerId = lcgSummonerId;
        this.lcgSummonerName = lcgSummonerName;
        this.lcgSummonerTag = lcgSummonerTag;
        this.lcgChampionId = lcgChampionId;
        this.lcgChampionName = lcgChampionName;
        this.lcgChampionLevel = lcgChampionLevel;
        this.lcgSpellName1 = lcgSpellName1;
        this.lcgSpellName2 = lcgSpellName2;
        this.lcgPerkName1 = lcgPerkName1;
        this.lcgPerkName2 = lcgPerkName2;
        this.lcgItemId1 = lcgItemId1;
        this.lcgItemId2 = lcgItemId2;
        this.lcgItemId3 = lcgItemId3;
        this.lcgItemId4 = lcgItemId4;
        this.lcgItemId5 = lcgItemId5;
        this.lcgItemId6 = lcgItemId6;
        this.lcgItemId7 = lcgItemId7;
        this.lcgKillCount = lcgKillCount;
        this.lcgDeathCount = lcgDeathCount;
        this.lcgAssistCount = lcgAssistCount;
        this.lcgDamageTotal = lcgDamageTotal;
        this.lcgDamageTaken = lcgDamageTaken;
        this.lcgMinionCount = lcgMinionCount;
        this.lcgJungleCount = lcgJungleCount;
        this.lcgVisionScore = lcgVisionScore;
    }
}
