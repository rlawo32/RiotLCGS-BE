package riot.lcgs.riotlcgsbe.util;

import riot.lcgs.riotlcgsbe.web.dto.CommonResponseDto;
import riot.lcgs.riotlcgsbe.web.dto.object.Perk;
import riot.lcgs.riotlcgsbe.web.dto.object.PerkSlot;
import riot.lcgs.riotlcgsbe.web.dto.object.Rune;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtractionTool {

    public static String jsonChampion;
    public static List<Perk> jsonPerk;

    public static CommonResponseDto<String> ExtractionName(int championId) {

        int idx = jsonChampion.indexOf("\"" + championId + "\"");
        String extractionStep1 = jsonChampion.substring(idx-30, idx);
        String[] extractionStep2 = extractionStep1.split(",");
        String extractionResult = extractionStep2[1].replace("\"", "").substring(3);

        return CommonResponseDto.setSuccess("Success", extractionResult);
    }

    public static CommonResponseDto<String> ExtractionSummonerSpell(int summonerId) {

        String extractionResult = "";

        switch (summonerId) {
            case 1 : extractionResult = "SummonerBoost"; break;                     // Cleanse
            case 3 : extractionResult = "SummonerExhaust"; break;                   // Exhaust
            case 4 : extractionResult = "SummonerFlash"; break;                     // Flash
            case 6 : extractionResult = "SummonerHaste"; break;                     // Ghost
            case 7 : extractionResult = "SummonerHeal"; break;                      // Heal
            case 11 : extractionResult = "SummonerSmite"; break;                    // Smite
            case 12 : extractionResult = "SummonerTeleport"; break;                 // Teleport
            case 13 : extractionResult = "SummonerMana"; break;                     // Clarity
            case 14 : extractionResult = "SummonerDot"; break;                      // Ignite
            case 21 : extractionResult = "SummonerBarrier"; break;                  // Barrier
            case 30 : extractionResult = "SummonerPoroRecall"; break;               // To the King
            case 31 : extractionResult = "SummonerPoroThrow"; break;                // Poro Toss
            case 32 : extractionResult = "SummonerSnowball"; break;                 // Mark
            case 39 : extractionResult = "SummonerSnowURFSnowball_Mark"; break;     // Mark
            case 54 : extractionResult = "Summoner_UltBookPlaceholder"; break;      // Placeholder
            case 55 : extractionResult = "Summoner_UltBookSmitePlaceholder"; break; // Placeholder and Attack-Smite
            case 2202 : extractionResult = "SummonerCherryFlash"; break;            // Flash
            case 2201 : extractionResult = "SummonerCherryHold"; break;             // Flee
            default: extractionResult = "Null SpellId"; break;                      // Non-Existent SpellId
        }

        return CommonResponseDto.setSuccess("Success", extractionResult);
    }

    public static CommonResponseDto<String> ExtractionPerk(int perkId) {

        Map<Integer, String> extractionResult = new HashMap<>();

        for(int i=0; i<jsonPerk.size(); i++) {
            extractionResult.put(jsonPerk.get(i).getId(), jsonPerk.get(i).getIcon());
            List<PerkSlot> slots = jsonPerk.get(i).getSlots();
            for(int j=0; j<slots.size(); j++) {
                List<Rune> rune = jsonPerk.get(i).getSlots().get(j).getRunes();
                for(int k=0; k<rune.size(); k++) {
                    extractionResult.put(rune.get(k).getId(), rune.get(k).getIcon());
                }
            }
        }

        return CommonResponseDto.setSuccess("Success", extractionResult.get(perkId));
    }
}
