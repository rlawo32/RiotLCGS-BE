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

    public static CommonResponseDto<Long> CalculatorDemolisherScore(Stats stats) {

        long calculatorResult   = 0L;

        calculatorResult = stats.getTurretKills() * 2L;

        return CommonResponseDto.setSuccess("Success", calculatorResult);
    }
    
    public static CommonResponseDto<Metrics[]> CalculatorMvpScore(Metrics[] metrics, String flag) {

        int initialScore = 0;
        int subtractScore = 0;

        switch (flag) {
            case "A" -> {
                initialScore = 5;
                subtractScore = 1;
                for(int i = 0; i < metrics.length; i++) {
                    metrics[i].setTotalScore(metrics[i].getTotalScore() + initialScore);
                    if(i % 2 == 1) {
                        initialScore -= subtractScore;
                    }
                }
            }
            case "B" -> {
                initialScore = 10;
                subtractScore = 1;
                for(Metrics metric : metrics) {
                    metric.setTotalScore(metric.getTotalScore() + initialScore);
                    initialScore -= subtractScore;
                }
            }
            case "C" -> {
                initialScore = 15;
                subtractScore = 3;
                for(int i = 0; i < metrics.length; i++) {
                    metrics[i].setTotalScore(metrics[i].getTotalScore() + initialScore);
                    if(i % 2 == 1) {
                        initialScore -= subtractScore;
                    }
                }
            }
            case "D" -> {
                initialScore = 5;
                for(Metrics metric : metrics) {
                    if(metric.isFirstDragon()) {
                        metric.setTotalScore(metric.getTotalScore() + initialScore);
                    }
                    if(metric.isFirstBaron()) {
                        metric.setTotalScore(metric.getTotalScore() + initialScore);
                    }
                    if(metric.isWin()) {
                        metric.setTotalScore(metric.getTotalScore() + initialScore);
                    }
                    if(metric.isFirstKill()) {
                        metric.setTotalScore(metric.getTotalScore() + initialScore);
                    }
                    if(metric.isFirstTower()) {
                        metric.setTotalScore(metric.getTotalScore() + initialScore);
                    }
                    if(metric.isFirstInhibitor()) {
                        metric.setTotalScore(metric.getTotalScore() + initialScore);
                    }
                }
            }
            case "E" -> {
                for(int i = 0; i < metrics.length; i++) {
                    if(i < 5) {
                        initialScore = 5;
                        metrics[i].setTotalScore(metrics[i].getTotalScore() + initialScore);
                    } else {
                        initialScore = 2;
                        metrics[i].setTotalScore(metrics[i].getTotalScore() + initialScore);
                    }
                }
            }
            default -> {
                for(Metrics metric : metrics) {
                    metric.setTotalScore((int) (metric.getTotalScore() + metric.getDemolisherScore()));
                    metric.setTotalScore((int) (metric.getTotalScore() + metric.getMultiKillScore()));
                }
            }
        }
        
        return CommonResponseDto.setSuccess("Success", metrics);
    }
}
