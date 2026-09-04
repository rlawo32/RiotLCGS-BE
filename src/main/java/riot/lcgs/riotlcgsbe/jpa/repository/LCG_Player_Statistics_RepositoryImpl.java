package riot.lcgs.riotlcgsbe.jpa.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Player_Statistics;

import java.util.*;

import static riot.lcgs.riotlcgsbe.jpa.domain.QLCG_Player_Position.lCG_Player_Position;
import static riot.lcgs.riotlcgsbe.jpa.domain.QLCG_Player_Statistics.lCG_Player_Statistics;

public class LCG_Player_Statistics_RepositoryImpl extends QuerydslRepositorySupport implements LCG_Player_Statistics_RepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    public LCG_Player_Statistics_RepositoryImpl() {
        super(LCG_Player_Statistics.class);
    }

    @Override
    public List<Map<String, Object>> findByAllPlayer() {
        List<Tuple> query = queryFactory
                .select(lCG_Player_Statistics.lcgSummonerPuuid, lCG_Player_Statistics.lcgNickname, lCG_Player_Statistics.lcgCountPlay)
                .from(lCG_Player_Statistics).fetch();

        List<Map<String, Object>> result = new ArrayList<>();
        for(Tuple tuple : query) {
            Map<String, Object> row = new HashMap<>();
            row.put("puuid", tuple.get(lCG_Player_Statistics.lcgSummonerPuuid));
            row.put("nickname", tuple.get(lCG_Player_Statistics.lcgNickname));
            row.put("play", tuple.get(lCG_Player_Statistics.lcgCountPlay));
            row.put("score", 0);
            row.put("rank", 0);

            result.add(row);
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> findByAllWinningRate() {
        NumberExpression<Double> calcWinningRate = calcWinningRate(lCG_Player_Statistics.lcgCountPlay, lCG_Player_Statistics.lcgCountVictory);
        List<Tuple> query = queryFactory
                .select(lCG_Player_Statistics.lcgSummonerPuuid, lCG_Player_Statistics.lcgCountPlay,
                        lCG_Player_Statistics.lcgCountVictory, lCG_Player_Statistics.lcgCountDefeat, calcWinningRate)
                .from(lCG_Player_Statistics).fetch();

        List<Map<String, Object>> result = new ArrayList<>();
        for(Tuple tuple : query) {
            Map<String, Object> row = new HashMap<>();
            row.put("puuid", tuple.get(lCG_Player_Statistics.lcgSummonerPuuid));
            row.put("countPlay", tuple.get(lCG_Player_Statistics.lcgCountPlay));
            row.put("countVictory", tuple.get(lCG_Player_Statistics.lcgCountVictory));
            row.put("countDefeat", tuple.get(lCG_Player_Statistics.lcgCountDefeat));
            row.put("grade", tuple.get(calcWinningRate)); // winningRate

            result.add(row);
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> findByAllMvpRank() {
        List<Tuple> query = queryFactory
                .select(lCG_Player_Statistics.lcgSummonerPuuid, lCG_Player_Statistics.lcgCountPlay,
                        lCG_Player_Statistics.lcgCountMvp)
                .from(lCG_Player_Statistics).fetch();

        List<Map<String, Object>> result = new ArrayList<>();
        for(Tuple tuple : query) {
            Map<String, Object> row = new HashMap<>();
            row.put("puuid", tuple.get(lCG_Player_Statistics.lcgSummonerPuuid));
            row.put("countPlay", tuple.get(lCG_Player_Statistics.lcgCountPlay));
            row.put("grade", tuple.get(lCG_Player_Statistics.lcgCountMvp)); // countMvp

            result.add(row);
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> findByAllAceRank() {
        List<Tuple> query = queryFactory
                .select(lCG_Player_Statistics.lcgSummonerPuuid, lCG_Player_Statistics.lcgCountPlay,
                        lCG_Player_Statistics.lcgCountAce)
                .from(lCG_Player_Statistics).fetch();

        List<Map<String, Object>> result = new ArrayList<>();
        for(Tuple tuple : query) {
            Map<String, Object> row = new HashMap<>();
            row.put("puuid", tuple.get(lCG_Player_Statistics.lcgSummonerPuuid));
            row.put("countPlay", tuple.get(lCG_Player_Statistics.lcgCountPlay));
            row.put("grade", tuple.get(lCG_Player_Statistics.lcgCountAce)); // countAce

            result.add(row);
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> findByAllKdaRank() {
        NumberExpression<Double> kda = calcKda(lCG_Player_Statistics.lcgCountKill, lCG_Player_Statistics.lcgCountDeath, lCG_Player_Statistics.lcgCountAssist);
        List<Tuple> query = queryFactory
                .select(lCG_Player_Statistics.lcgSummonerPuuid, lCG_Player_Statistics.lcgCountPlay,
                        lCG_Player_Statistics.lcgCountKill, lCG_Player_Statistics.lcgCountDeath,
                        lCG_Player_Statistics.lcgCountAssist, kda)
                .from(lCG_Player_Statistics).fetch();

        List<Map<String, Object>> result = new ArrayList<>();
        for(Tuple tuple : query) {
            Map<String, Object> row = new HashMap<>();
            row.put("puuid", tuple.get(lCG_Player_Statistics.lcgSummonerPuuid));
            row.put("countPlay", tuple.get(lCG_Player_Statistics.lcgCountPlay));
            row.put("countKill", tuple.get(lCG_Player_Statistics.lcgCountKill));
            row.put("countDeath", tuple.get(lCG_Player_Statistics.lcgCountDeath));
            row.put("countAssist", tuple.get(lCG_Player_Statistics.lcgCountAssist));
            row.put("grade", tuple.get(kda)); // kda

            result.add(row);
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> findByAllVisionRank() {
        NumberExpression<Double> calcAvg = calcAvg(lCG_Player_Statistics.lcgCountVisionScore, lCG_Player_Statistics.lcgCountPlay);
        List<Tuple> query = queryFactory
                .select(lCG_Player_Statistics.lcgSummonerPuuid, lCG_Player_Statistics.lcgCountPlay,
                        lCG_Player_Statistics.lcgCountVisionScore, calcAvg)
                .from(lCG_Player_Statistics).fetch();

        List<Map<String, Object>> result = new ArrayList<>();
        for(Tuple tuple : query) {
            Map<String, Object> row = new HashMap<>();
            row.put("puuid", tuple.get(lCG_Player_Statistics.lcgSummonerPuuid));
            row.put("countPlay", tuple.get(lCG_Player_Statistics.lcgCountPlay));
            row.put("score", tuple.get(lCG_Player_Statistics.lcgCountVisionScore));
            row.put("grade", tuple.get(calcAvg)); // visionScore avg

            result.add(row);
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> findByAllGoldRank() {
        NumberExpression<Double> calcAvg = calcAvg(lCG_Player_Statistics.lcgCountGold, lCG_Player_Statistics.lcgCountPlay);
        List<Tuple> query = queryFactory
                .select(lCG_Player_Statistics.lcgSummonerPuuid, lCG_Player_Statistics.lcgCountPlay,
                        lCG_Player_Statistics.lcgCountGold, calcAvg)
                .from(lCG_Player_Statistics).fetch();

        List<Map<String, Object>> result = new ArrayList<>();
        for(Tuple tuple : query) {
            Map<String, Object> row = new HashMap<>();
            row.put("puuid", tuple.get(lCG_Player_Statistics.lcgSummonerPuuid));
            row.put("countPlay", tuple.get(lCG_Player_Statistics.lcgCountPlay));
            row.put("score", tuple.get(lCG_Player_Statistics.lcgCountGold));
            row.put("grade", tuple.get(calcAvg)); // countGold avg

            result.add(row);
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> findByAllDeathRank() {
        NumberExpression<Double> calcAvg = calcAvg(lCG_Player_Statistics.lcgCountDeath, lCG_Player_Statistics.lcgCountPlay);
        List<Tuple> query = queryFactory
                .select(lCG_Player_Statistics.lcgSummonerPuuid, lCG_Player_Statistics.lcgCountPlay,
                        lCG_Player_Statistics.lcgCountDeath, calcAvg)
                .from(lCG_Player_Statistics).fetch();

        List<Map<String, Object>> result = new ArrayList<>();
        for(Tuple tuple : query) {
            Map<String, Object> row = new HashMap<>();
            row.put("puuid", tuple.get(lCG_Player_Statistics.lcgSummonerPuuid));
            row.put("countPlay", tuple.get(lCG_Player_Statistics.lcgCountPlay));
            row.put("score", tuple.get(lCG_Player_Statistics.lcgCountDeath));
            row.put("grade", tuple.get(calcAvg)); // countDeath avg

            result.add(row);
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> findByAllMultiKillRank() {
        NumberExpression<Double> calcAvg = calcAvg(lCG_Player_Statistics.lcgMultiKillScore, lCG_Player_Statistics.lcgCountPlay);
        List<Tuple> query = queryFactory
                .select(lCG_Player_Statistics.lcgSummonerPuuid, lCG_Player_Statistics.lcgCountPlay,
                        lCG_Player_Statistics.lcgMultiKillScore, calcAvg)
                .from(lCG_Player_Statistics).fetch();

        List<Map<String, Object>> result = new ArrayList<>();
        for(Tuple tuple : query) {
            Map<String, Object> row = new HashMap<>();
            row.put("puuid", tuple.get(lCG_Player_Statistics.lcgSummonerPuuid));
            row.put("countPlay", tuple.get(lCG_Player_Statistics.lcgCountPlay));
            row.put("score", tuple.get(lCG_Player_Statistics.lcgMultiKillScore));
            row.put("grade", tuple.get(calcAvg)); // multiKillScore avg

            result.add(row);
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> findByAllDemolisherRank() {
        List<Tuple> query = queryFactory
                .select(lCG_Player_Statistics.lcgSummonerPuuid, lCG_Player_Statistics.lcgCountPlay,
                        lCG_Player_Statistics.lcgCountTower, lCG_Player_Statistics.lcgCountInhibitor)
                .from(lCG_Player_Statistics).fetch();

        List<Map<String, Object>> result = new ArrayList<>();
        for(Tuple tuple : query) {
            Map<String, Object> row = new HashMap<>();
            row.put("puuid", tuple.get(lCG_Player_Statistics.lcgSummonerPuuid));
            row.put("countPlay", tuple.get(lCG_Player_Statistics.lcgCountPlay));
            // countTower + countInhibitor
            row.put("grade", tuple.get(lCG_Player_Statistics.lcgCountTower) + tuple.get(lCG_Player_Statistics.lcgCountInhibitor));

            result.add(row);
        }

        return result;
    }

    @Override
    public Map<String, Integer> findByAllMaxStatistics() {
        NumberExpression<Long> cs = lCG_Player_Statistics.lcgCountMinion.add(lCG_Player_Statistics.lcgCountJungle).max();
        NumberExpression<Long> demolisher = lCG_Player_Statistics.lcgCountTower.add(lCG_Player_Statistics.lcgCountInhibitor).max();

        Tuple query = queryFactory
                .select(lCG_Player_Statistics.lcgCountKill.max(), lCG_Player_Statistics.lcgCountDeath.max(),
                        lCG_Player_Statistics.lcgCountAssist.max(), lCG_Player_Statistics.lcgCountMvp.max(),
                        lCG_Player_Statistics.lcgCountAce.max(), lCG_Player_Statistics.lcgCountGold.max(), cs, demolisher,
                        lCG_Player_Statistics.lcgCountVisionWard.max(), lCG_Player_Statistics.lcgCountVisionScore.max(),
                        lCG_Player_Statistics.lcgMultiKillScore.max(), lCG_Player_Statistics.lcgJungleObjectScore.max())
                .from(lCG_Player_Statistics).fetchOne();

        Map<String, Integer> result = new HashMap<>();
        result.put("kill", query.get(lCG_Player_Statistics.lcgCountKill.max()).intValue());
        result.put("death", query.get(lCG_Player_Statistics.lcgCountDeath.max()).intValue());
        result.put("assist", query.get(lCG_Player_Statistics.lcgCountAssist.max()).intValue());
        result.put("mvp", query.get(lCG_Player_Statistics.lcgCountMvp.max()).intValue());
        result.put("ace", query.get(lCG_Player_Statistics.lcgCountAce.max()).intValue());
        result.put("gold", query.get(lCG_Player_Statistics.lcgCountGold.max()).intValue());
        result.put("cs", query.get(cs).intValue());
        result.put("demolisher", query.get(demolisher).intValue());
        result.put("pinkward", query.get(lCG_Player_Statistics.lcgCountVisionWard.max()).intValue());
        result.put("vision", query.get(lCG_Player_Statistics.lcgCountVisionScore.max()).intValue());
        result.put("multikill", query.get(lCG_Player_Statistics.lcgMultiKillScore.max()).intValue());
        result.put("object", query.get(lCG_Player_Statistics.lcgJungleObjectScore.max()).intValue());

        return result;
    }

    private NumberExpression<Double> calcWinningRate(NumberPath<Long> lcgCountPlay, NumberPath<Long> lcgCountVictory) {
        NumberExpression<Double> count = Expressions.numberTemplate(Double.class,"nullif({0}, 0)", lcgCountPlay);
        return Expressions.numberTemplate(Double.class, "FLOOR({0}*10)/10", (lcgCountVictory.doubleValue().multiply(100.0)).divide(count));
    }

    private NumberExpression<Double> calcKda(NumberPath<Long> lcgCountKill, NumberPath<Long> lcgCountDeath, NumberPath<Long> lcgCountAssist) {
        return new CaseBuilder()
                .when(lcgCountDeath.eq(0L))
                .then(lcgCountKill.doubleValue().add(lcgCountAssist))
                .otherwise(
                        Expressions.numberTemplate(
                                Double.class,"FLOOR({0} * 100) / 100",
                                lcgCountKill.doubleValue().add(lcgCountAssist).divide(lcgCountDeath)
                        )
                );
    }

    private NumberExpression<Double> calcAvg(NumberPath<Long> target, NumberPath<Long> play) {
        return Expressions.numberTemplate(Double.class, "FLOOR({0}*100)/100", target.divide(play));
    }
}
