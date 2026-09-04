package riot.lcgs.riotlcgsbe.jpa.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Player_Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static riot.lcgs.riotlcgsbe.jpa.domain.QLCG_Player_Position.lCG_Player_Position;

public class LCG_Player_Position_RepositoryImpl extends QuerydslRepositorySupport implements LCG_Player_Position_RepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    public LCG_Player_Position_RepositoryImpl() {
        super(LCG_Player_Position.class);
    }

    @Override
    public List<Map<String, Object>> findAllLaneRate() {
        NumberExpression<Double> calcTop = calcWinningRate(lCG_Player_Position.lcgPositionTopCount, lCG_Player_Position.lcgPositionTopWin);
        NumberExpression<Double> calcJug = calcWinningRate(lCG_Player_Position.lcgPositionJugCount, lCG_Player_Position.lcgPositionJugWin);
        NumberExpression<Double> calcMid = calcWinningRate(lCG_Player_Position.lcgPositionMidCount, lCG_Player_Position.lcgPositionMidWin);
        NumberExpression<Double> calcAdc = calcWinningRate(lCG_Player_Position.lcgPositionAdcCount, lCG_Player_Position.lcgPositionAdcWin);
        NumberExpression<Double> calcSup = calcWinningRate(lCG_Player_Position.lcgPositionSupCount, lCG_Player_Position.lcgPositionSupWin);

        List<Tuple> query = queryFactory
                .select(lCG_Player_Position.lcgSummonerPuuid,
                        lCG_Player_Position.lcgPositionTopCount, calcTop,
                        lCG_Player_Position.lcgPositionJugCount, calcJug,
                        lCG_Player_Position.lcgPositionMidCount, calcMid,
                        lCG_Player_Position.lcgPositionAdcCount, calcAdc,
                        lCG_Player_Position.lcgPositionSupCount, calcSup)
                .from(lCG_Player_Position).fetch();

        List<Map<String, Object>> result = new ArrayList<>();
        for(Tuple tuple : query) {
            Map<String, Object> row = new HashMap<>();
            row.put("puuid", tuple.get(lCG_Player_Position.lcgSummonerPuuid));
            row.put("play_top", tuple.get(lCG_Player_Position.lcgPositionTopCount));
            row.put("rate_top", tuple.get(calcTop));
            row.put("play_jug", tuple.get(lCG_Player_Position.lcgPositionJugCount));
            row.put("rate_jug", tuple.get(calcJug));
            row.put("play_mid", tuple.get(lCG_Player_Position.lcgPositionMidCount));
            row.put("rate_mid", tuple.get(calcMid));
            row.put("play_adc", tuple.get(lCG_Player_Position.lcgPositionAdcCount));
            row.put("rate_adc", tuple.get(calcAdc));
            row.put("play_sup", tuple.get(lCG_Player_Position.lcgPositionSupCount));
            row.put("rate_sup", tuple.get(calcSup));
            result.add(row);
        }

        return result;
    }

    private NumberExpression<Double> calcWinningRate(NumberPath<Long> lcgCountPlay, NumberPath<Long> lcgCountVictory) {
        NumberExpression<Double> count = Expressions.numberTemplate(Double.class,"nullif({0}, 0)", lcgCountPlay);
        return Expressions.numberTemplate(Double.class, "FLOOR({0}*10)/10", (lcgCountVictory.doubleValue().multiply(100.0)).divide(count));
    }
}
