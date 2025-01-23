package riot.lcgs.riotlcgsbe.util;

import riot.lcgs.riotlcgsbe.web.dto.CommonResponseDto;
import riot.lcgs.riotlcgsbe.web.dto.object.PerkSlot;
import riot.lcgs.riotlcgsbe.web.dto.object.Rune;
import riot.lcgs.riotlcgsbe.web.dto.object.Stats;
import riot.lcgs.riotlcgsbe.web.dto.object.Teams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculatorTool {

    public static CommonResponseDto<Long> CalculatorJungleObjectScore(Teams teams) {

        Long calculatorResult = teams.getHordeKills() + (teams.getDragonKills() * 2L) + (teams.getRiftHeraldKills() * 4L) + (teams.getBaronKills() * 7L);

        return CommonResponseDto.setSuccess("Success", calculatorResult);
    }

    public static CommonResponseDto<Long> CalculatorMultiKillScore(Stats stats) {

        Long calculatorResult = stats.getDoubleKills() + (stats.getTripleKills() * 3L) + (stats.getQuadraKills() * 10L) + (stats.getPentaKills() * 50L);

        return CommonResponseDto.setSuccess("Success", calculatorResult);
    }
}
