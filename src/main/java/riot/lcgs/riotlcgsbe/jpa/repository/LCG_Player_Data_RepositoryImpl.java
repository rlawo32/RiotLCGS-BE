package riot.lcgs.riotlcgsbe.jpa.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Player_Data;

import java.util.*;

import static riot.lcgs.riotlcgsbe.jpa.domain.QLCG_Player_Data.lCG_Player_Data;

public class LCG_Player_Data_RepositoryImpl extends QuerydslRepositorySupport implements LCG_Player_Data_RepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    public LCG_Player_Data_RepositoryImpl() {
        super(LCG_Player_Data.class);
    }

    @Override
    public List<Map<String, Object>> findByAllPlayer() {
        List<Tuple> query = queryFactory
                .select(lCG_Player_Data.lcgSummonerPuuid, lCG_Player_Data.lcgPlayer, lCG_Player_Data.lcgSummonerNickname)
                .from(lCG_Player_Data).fetch();

        List<Map<String, Object>> result = new ArrayList<>();
        for(Tuple tuple : query) {
            Map<String, Object> row = new HashMap<>();
            row.put("puuid", tuple.get(lCG_Player_Data.lcgSummonerPuuid));
            row.put("name", tuple.get(lCG_Player_Data.lcgPlayer));
            row.put("nickname", tuple.get(lCG_Player_Data.lcgSummonerNickname));
            row.put("score", 0);

            result.add(row);
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> findByAllTierRank() {
        StringPath tier = Expressions.stringPath("tier");
        StringPath division = Expressions.stringPath("division");

        List<Tuple> query = queryFactory
                .select(lCG_Player_Data.lcgSummonerPuuid, lCG_Player_Data.lcgPresentTier, lCG_Player_Data.lcgPresentDivision,
                        new CaseBuilder()
                                .when(lCG_Player_Data.lcgPresentTier.eq("NA")).then("0")
                                .when(lCG_Player_Data.lcgPresentTier.eq("IRON")).then("10")
                                .when(lCG_Player_Data.lcgPresentTier.eq("BRONZE")).then("20")
                                .when(lCG_Player_Data.lcgPresentTier.eq("SILVER")).then("30")
                                .when(lCG_Player_Data.lcgPresentTier.eq("GOLD")).then("40")
                                .when(lCG_Player_Data.lcgPresentTier.eq("PLATINUM")).then("50")
                                .when(lCG_Player_Data.lcgPresentTier.eq("EMERALD")).then("60")
                                .when(lCG_Player_Data.lcgPresentTier.eq("DIAMOND")).then("70")
                                .when(lCG_Player_Data.lcgPresentTier.eq("MASTER")).then("80")
                                .otherwise("0").as(String.valueOf(tier)),
                        new CaseBuilder()
                                .when(lCG_Player_Data.lcgPresentDivision.eq("I")).then("5")
                                .when(lCG_Player_Data.lcgPresentDivision.eq("II")).then("4")
                                .when(lCG_Player_Data.lcgPresentDivision.eq("III")).then("3")
                                .when(lCG_Player_Data.lcgPresentDivision.eq("IV")).then("2")
                                .when(lCG_Player_Data.lcgPresentDivision.eq("V")).then("1")
                                .when(lCG_Player_Data.lcgPresentDivision.eq("NA")).then("0")
                                .otherwise("0").as(String.valueOf(division)))
                .from(lCG_Player_Data).fetch();

        List<Map<String, Object>> result = new ArrayList<>();
        for(Tuple tuple : query) {
            Map<String, Object> row = new HashMap<>();
            row.put("puuid", tuple.get(lCG_Player_Data.lcgSummonerPuuid));
            row.put("tier", tuple.get(lCG_Player_Data.lcgPresentTier));
            row.put("division", tuple.get(lCG_Player_Data.lcgPresentDivision));
            row.put("score", Integer.parseInt(Objects.requireNonNull(tuple.get(tier)))
                    + Integer.parseInt(Objects.requireNonNull(tuple.get(division))));

            result.add(row);
        }

        return result;
    }
}
