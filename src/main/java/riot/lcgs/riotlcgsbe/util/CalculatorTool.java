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

        Long calculatorResult = 0L;
        Long pointHorde = 1L;
        Long pointDragon = 2L;
        Long pointHerald = 4L;
        Long pointBaron = 7L;
        Long pointAtakhan = 7L;

        calculatorResult = (teams.getHordeKills() * pointHorde) + (teams.getDragonKills() * pointDragon) + (teams.getRiftHeraldKills() * pointHerald) + (teams.getBaronKills() * pointBaron);

        return CommonResponseDto.setSuccess("Success", calculatorResult);
    }

    public static CommonResponseDto<Long> CalculatorMultiKillScore(Stats stats) {

        Long calculatorResult = 0L;
        Long pointDoubleKill = 1L;
        Long pointTripleKill = 3L;
        Long pointQuadraKill = 10L;
        Long pointPentaKill = 50L;
        
        calculatorResult = (stats.getDoubleKills() * pointDoubleKill) + (stats.getTripleKills() * pointTripleKill) + (stats.getQuadraKills() * pointQuadraKill) + (stats.getPentaKills() * pointPentaKill);

        return CommonResponseDto.setSuccess("Success", calculatorResult);
    }
}
