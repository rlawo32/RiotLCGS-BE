package riot.lcgs.riotlcgsbe.util;

import riot.lcgs.riotlcgsbe.web.dto.CommonResponseDto;
import riot.lcgs.riotlcgsbe.web.dto.object.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculatorTool {

    public static CommonResponseDto<Long> CalculatorJungleObjectScore(Teams teams) {

        long calculatorResult   = 0L;
        long pointHorde         = 1L; // 유충
        long pointDragon        = 2L; // 드래곤
        long pointHerald        = 4L; // 전령
        long pointBaron         = 7L; // 바론
        long pointAtakhan       = 7L; // 아타칸

        calculatorResult = (teams.getHordeKills() * pointHorde) + (teams.getDragonKills() * pointDragon) + (teams.getRiftHeraldKills() * pointHerald) + (teams.getBaronKills() * pointBaron);

        return CommonResponseDto.setSuccess("Success", calculatorResult);
    }

    public static CommonResponseDto<Long> CalculatorMultiKillScore(Stats stats) {

        long calculatorResult   = 0L;
        long pointDoubleKill    = 1L;   // 더블킬
        long pointTripleKill    = 3L;   // 트리플킬
        long pointQuadraKill    = 10L;  // 쿼드라킬
        long pointPentaKill     = 50L;  // 펜타킬
        
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
