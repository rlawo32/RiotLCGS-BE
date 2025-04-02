package riot.lcgs.riotlcgsbe.jpa.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Match_Sub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static riot.lcgs.riotlcgsbe.jpa.domain.QLCG_Match_Sub.lCG_Match_Sub;

public class LCG_Match_Sub_RepositoryImpl extends QuerydslRepositorySupport implements LCG_Match_Sub_RepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    public LCG_Match_Sub_RepositoryImpl() {
        super(LCG_Match_Sub.class);
    }

    @Override
    public List<Map<String, Object>> findByAllAvgDpmRank() {
        StringPath dpmAvg = Expressions.stringPath("dpmAvg");
        StringPath dpmCnt = Expressions.stringPath("dpmCnt");
        List<Tuple> query = queryFactory
                .select(calcAvg(lCG_Match_Sub.lcgDamagePerMinute.sum(), lCG_Match_Sub.lcgSummonerPuuid.count()).as(String.valueOf(dpmAvg)),
                        lCG_Match_Sub.lcgSummonerPuuid, lCG_Match_Sub.lcgSummonerPuuid.count().as(String.valueOf(dpmCnt)))
                .from(lCG_Match_Sub)
                .groupBy(lCG_Match_Sub.lcgSummonerPuuid).fetch();

        List<Map<String, Object>> result = new ArrayList<>();
        for(Tuple tuple : query) {
            Map<String, Object> row = new HashMap<>();
            row.put("puuid", tuple.get(lCG_Match_Sub.lcgSummonerPuuid));
            row.put("count", tuple.get(dpmCnt));
            row.put("grade", tuple.get(dpmAvg)); // avgDpm

            result.add(row);
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> findByAllAvgGpmRank() {
        StringPath gpmAvg = Expressions.stringPath("gpmAvg");
        StringPath gpmCnt = Expressions.stringPath("gpmCnt");
        List<Tuple> query = queryFactory
                .select(calcAvg(lCG_Match_Sub.lcgGoldPerMinute.sum(), lCG_Match_Sub.lcgSummonerPuuid.count()).as(String.valueOf(gpmAvg)),
                        lCG_Match_Sub.lcgSummonerPuuid, lCG_Match_Sub.lcgSummonerPuuid.count().as(String.valueOf(gpmCnt)))
                .from(lCG_Match_Sub)
                .groupBy(lCG_Match_Sub.lcgSummonerPuuid).fetch();

        List<Map<String, Object>> result = new ArrayList<>();
        for(Tuple tuple : query) {
            Map<String, Object> row = new HashMap<>();
            row.put("puuid", tuple.get(lCG_Match_Sub.lcgSummonerPuuid));
            row.put("count", tuple.get(gpmCnt));
            row.put("grade", tuple.get(gpmAvg)); // avgGpm

            result.add(row);
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> findByAllAvgDpgRank() {
        StringPath dpgAvg = Expressions.stringPath("dpgAvg");
        StringPath dpgCnt = Expressions.stringPath("dpgCnt");
        List<Tuple> query = queryFactory
                .select(calcAvg(lCG_Match_Sub.lcgDamagePerGold.sum(), lCG_Match_Sub.lcgSummonerPuuid.count()).as(String.valueOf(dpgAvg)),
                        lCG_Match_Sub.lcgSummonerPuuid, lCG_Match_Sub.lcgSummonerPuuid.count().as(String.valueOf(dpgCnt)))
                .from(lCG_Match_Sub)
                .groupBy(lCG_Match_Sub.lcgSummonerPuuid).fetch();

        List<Map<String, Object>> result = new ArrayList<>();
        for(Tuple tuple : query) {
            Map<String, Object> row = new HashMap<>();
            row.put("puuid", tuple.get(lCG_Match_Sub.lcgSummonerPuuid));
            row.put("count", tuple.get(dpgCnt));
            row.put("grade", tuple.get(dpgAvg)); // avgDpg

            result.add(row);
        }

        return result;
    }

    private SimpleTemplate<Double> calcAvg(NumberExpression<Double> sum, NumberExpression<Long> cnt) {
        return Expressions.template(Double.class, "FLOOR({0}*10)/10", sum.divide(cnt));
    }
}
