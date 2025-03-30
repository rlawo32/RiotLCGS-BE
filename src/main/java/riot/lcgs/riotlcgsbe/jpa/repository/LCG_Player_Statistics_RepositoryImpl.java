package riot.lcgs.riotlcgsbe.jpa.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Player_Statistics;

import java.util.*;

import static riot.lcgs.riotlcgsbe.jpa.domain.QLCG_Player_Statistics.lCG_Player_Statistics;

public class LCG_Player_Statistics_RepositoryImpl extends QuerydslRepositorySupport implements LCG_Player_Statistics_RepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    public LCG_Player_Statistics_RepositoryImpl() {
        super(LCG_Player_Statistics.class);
    }

    @Override
    public List<Map<String, Object>> findByAllWinningRate() {
        StringPath rate = Expressions.stringPath("rate");
        List<Tuple> query = queryFactory
                .select(lCG_Player_Statistics.lcgSummonerPuuid, lCG_Player_Statistics.lcgCountPlay,
                        lCG_Player_Statistics.lcgCountVictory, lCG_Player_Statistics.lcgCountDefeat,
                        new CaseBuilder()
                            .when(lCG_Player_Statistics.lcgCountVictory.gt(0))
                            .then(calcWinningRate(lCG_Player_Statistics.lcgCountPlay, lCG_Player_Statistics.lcgCountVictory))
                            .otherwise((double) 0).as(String.valueOf(rate)))
                .from(lCG_Player_Statistics).fetch();

        List<Map<String, Object>> result = new ArrayList<>();
        for(Tuple tuple : query) {
            Map<String, Object> row = new HashMap<>();
            row.put("puuid", tuple.get(lCG_Player_Statistics.lcgSummonerPuuid));
            row.put("countPlay", tuple.get(lCG_Player_Statistics.lcgCountPlay));
            row.put("countVictory", tuple.get(lCG_Player_Statistics.lcgCountVictory));
            row.put("countDefeat", tuple.get(lCG_Player_Statistics.lcgCountDefeat));
            row.put("rate", tuple.get(rate));

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
            row.put("countMvp", tuple.get(lCG_Player_Statistics.lcgCountMvp));

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
            row.put("countAce", tuple.get(lCG_Player_Statistics.lcgCountAce));

            result.add(row);
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> findByAllKdaRank() {
        StringPath kda = Expressions.stringPath("kda");
        List<Tuple> query = queryFactory
                .select(lCG_Player_Statistics.lcgSummonerPuuid, lCG_Player_Statistics.lcgCountPlay,
                        lCG_Player_Statistics.lcgCountKill, lCG_Player_Statistics.lcgCountDeath,
                        lCG_Player_Statistics.lcgCountAssist, calcKda(lCG_Player_Statistics.lcgCountKill,
                                lCG_Player_Statistics.lcgCountDeath, lCG_Player_Statistics.lcgCountAssist).as(String.valueOf(kda)))
                .from(lCG_Player_Statistics).fetch();

        List<Map<String, Object>> result = new ArrayList<>();
        for(Tuple tuple : query) {
            Map<String, Object> row = new HashMap<>();
            row.put("puuid", tuple.get(lCG_Player_Statistics.lcgSummonerPuuid));
            row.put("countPlay", tuple.get(lCG_Player_Statistics.lcgCountPlay));
            row.put("countKill", tuple.get(lCG_Player_Statistics.lcgCountKill));
            row.put("countDeath", tuple.get(lCG_Player_Statistics.lcgCountDeath));
            row.put("countAssist", tuple.get(lCG_Player_Statistics.lcgCountAssist));
            row.put("kda", tuple.get(kda));

            result.add(row);
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> findByAllVisionRank() {
        List<Tuple> query = queryFactory
                .select(lCG_Player_Statistics.lcgSummonerPuuid, lCG_Player_Statistics.lcgCountPlay,
                        lCG_Player_Statistics.lcgCountVisionScore)
                .from(lCG_Player_Statistics).fetch();

        List<Map<String, Object>> result = new ArrayList<>();
        for(Tuple tuple : query) {
            Map<String, Object> row = new HashMap<>();
            row.put("puuid", tuple.get(lCG_Player_Statistics.lcgSummonerPuuid));
            row.put("countPlay", tuple.get(lCG_Player_Statistics.lcgCountPlay));
            row.put("visionScore", tuple.get(lCG_Player_Statistics.lcgCountVisionScore));

            result.add(row);
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> findByAllGoldRank() {
        List<Tuple> query = queryFactory
                .select(lCG_Player_Statistics.lcgSummonerPuuid, lCG_Player_Statistics.lcgCountPlay,
                        lCG_Player_Statistics.lcgCountGold)
                .from(lCG_Player_Statistics).fetch();

        List<Map<String, Object>> result = new ArrayList<>();
        for(Tuple tuple : query) {
            Map<String, Object> row = new HashMap<>();
            row.put("puuid", tuple.get(lCG_Player_Statistics.lcgSummonerPuuid));
            row.put("countPlay", tuple.get(lCG_Player_Statistics.lcgCountPlay));
            row.put("countGold", tuple.get(lCG_Player_Statistics.lcgCountGold));

            result.add(row);
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> findByAllDeathRank() {
        List<Tuple> query = queryFactory
                .select(lCG_Player_Statistics.lcgSummonerPuuid, lCG_Player_Statistics.lcgCountPlay,
                        lCG_Player_Statistics.lcgCountDeath)
                .from(lCG_Player_Statistics).fetch();

        List<Map<String, Object>> result = new ArrayList<>();
        for(Tuple tuple : query) {
            Map<String, Object> row = new HashMap<>();
            row.put("puuid", tuple.get(lCG_Player_Statistics.lcgSummonerPuuid));
            row.put("countPlay", tuple.get(lCG_Player_Statistics.lcgCountPlay));
            row.put("countDeath", tuple.get(lCG_Player_Statistics.lcgCountDeath));

            result.add(row);
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> findByAllMultiKillRank() {
        List<Tuple> query = queryFactory
                .select(lCG_Player_Statistics.lcgSummonerPuuid, lCG_Player_Statistics.lcgCountPlay,
                        lCG_Player_Statistics.lcgMultiKillScore)
                .from(lCG_Player_Statistics).fetch();

        List<Map<String, Object>> result = new ArrayList<>();
        for(Tuple tuple : query) {
            Map<String, Object> row = new HashMap<>();
            row.put("puuid", tuple.get(lCG_Player_Statistics.lcgSummonerPuuid));
            row.put("countPlay", tuple.get(lCG_Player_Statistics.lcgCountPlay));
            row.put("multiKillScore", tuple.get(lCG_Player_Statistics.lcgMultiKillScore));

            result.add(row);
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> findByAllDemolisherRank() {
        List<Tuple> query = queryFactory
                .select(lCG_Player_Statistics.lcgSummonerPuuid, lCG_Player_Statistics.lcgCountPlay,
                        lCG_Player_Statistics.lcgCountTower, lCG_Player_Statistics.lcgCountTowerDamage)
                .from(lCG_Player_Statistics).fetch();

        List<Map<String, Object>> result = new ArrayList<>();
        for(Tuple tuple : query) {
            Map<String, Object> row = new HashMap<>();
            row.put("puuid", tuple.get(lCG_Player_Statistics.lcgSummonerPuuid));
            row.put("countPlay", tuple.get(lCG_Player_Statistics.lcgCountPlay));
            row.put("countTower", tuple.get(lCG_Player_Statistics.lcgCountTower));
            row.put("countTowerDamage", tuple.get(lCG_Player_Statistics.lcgCountTowerDamage));

            result.add(row);
        }

        return result;
    }

    private SimpleTemplate<Double> calcWinningRate(NumberPath<Long> lcgCountPlay, NumberPath<Long> lcgCountVictory) {
        return Expressions.template(Double.class, "FLOOR({0}*10)/10", (lcgCountVictory.doubleValue().multiply(100.0)).divide(lcgCountPlay));
    }

    private SimpleTemplate<Double> calcKda(NumberPath<Long> lcgCountKill, NumberPath<Long> lcgCountDeath, NumberPath<Long> lcgCountAssist) {
        return Expressions.template(Double.class, "FLOOR({0}*100)/100",
                (lcgCountKill.doubleValue().add(lcgCountAssist)).divide(lcgCountDeath));
    }
}
