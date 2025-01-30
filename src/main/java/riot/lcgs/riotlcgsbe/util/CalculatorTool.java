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
			case "B" : initialScore = 10; subtractScore = 1; break;
			case "C" : initialScore = 20; subtractScore = 1; break;
			case "D" : initialScore = 5; break;
            case "E" : initialScore = 7; break;
			default: break;
		}

        if(flag.equals("A")) {
            for (int i=0; i<metrics.length; i++) {
    			metrics[i].setTotalScore(metrics[i].getTotalScore() + initialScore);
                if(i % 2 == 1) {
                    initialScore -= subtractScore;
                }
    		}
        } else if(flag.equals("B") || flag.equals("C")) {
            for (int i=0; i<metrics.length; i++) {
                metrics[i].setTotalScore(metrics[i].getTotalScore() + initialScore);
                initialScore -= subtractScore;
            }
        } else if(flag.equals("D")) {
            for (int i=0; i<metrics.length; i++) {
                if(metrics[i].isFirstDragon()) {
                    metrics[i].setTotalScore(metrics[i].getTotalScore() + initialScore);
                }
                if(metrics[i].isFirstBaron()) {
                    metrics[i].setTotalScore(metrics[i].getTotalScore() + initialScore);
                }
                if(metrics[i].isWin()) {
                    metrics[i].setTotalScore(metrics[i].getTotalScore() + initialScore);
                }
            }
        } else if(flag.equals("E")) {
            for (int i=0; i<metrics.length; i++) {
                if(metrics[i].isFirstKill()) {
                    metrics[i].setTotalScore(metrics[i].getTotalScore() + initialScore);
                }
                if(metrics[i].isFirstTower()) {
                    metrics[i].setTotalScore(metrics[i].getTotalScore() + initialScore);
                }
                if(metrics[i].isFirstInhibitor()) {
                    metrics[i].setTotalScore(metrics[i].getTotalScore() + initialScore);
                }
            }
        }
        
        return CommonResponseDto.setSuccess("Success", metrics);
    }
}
