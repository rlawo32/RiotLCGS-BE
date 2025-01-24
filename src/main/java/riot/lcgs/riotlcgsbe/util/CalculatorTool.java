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
    
    public static CommonResponseDto<Metrics[]> CalculatorMvpScore(Metrics[] metrics, String flag) {

        int initialScore = 0;
        int subtractScore = 0;
        
		switch (flag) {
			case "A" : initialScore = 5; subtractScore = 1; break;
			case "B" : initialScore = 10; subtractScore = 2; break;
			case "C" : initialScore = 20; subtractScore = 3; break;
			case "D" : initialScore = 30; subtractScore = 3; break;
			default: break;
		}

        if(flag.equals("A")) {
            for (int i=0; i<metrics.length; i++) {
    			metrics[i].setTotalScore(metrics[i].getTotalScore() + initialScore);
    			initialScore -= subtractScore;
    		}
        }

	    Arrays.sort(metrics, (m1, m2) -> Integer.compare(m2.getKill(),m1.getKill()));
        
        return CommonResponseDto.setSuccess("Success", metrics);
    }
}
