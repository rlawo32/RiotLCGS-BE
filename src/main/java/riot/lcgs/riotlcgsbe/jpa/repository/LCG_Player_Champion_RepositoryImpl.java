package riot.lcgs.riotlcgsbe.jpa.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Player_Champion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static riot.lcgs.riotlcgsbe.jpa.domain.QLCG_Player_Champion.lCG_Player_Champion;

public class LCG_Player_Champion_RepositoryImpl extends QuerydslRepositorySupport implements LCG_Player_Champion_RepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    public LCG_Player_Champion_RepositoryImpl() {
        super(LCG_Player_Champion.class);
    }

    @Override
    public List<Map<String, Object>> findPerfectWinner() {
        List<Tuple> query = queryFactory
                .select(lCG_Player_Champion.lcgPuuid, lCG_Player_Champion.lcgChampionName, lCG_Player_Champion.lcgPlayCount)
                .from(lCG_Player_Champion)
                .where(lCG_Player_Champion.lcgPlayCount.goe(5L).and(lCG_Player_Champion.lcgFailCount.eq(0L)))
                .fetch();

        List<Map<String, Object>> result = new ArrayList<>();
        for(Tuple tuple : query) {
            Map<String, Object> row = new HashMap<>();
            row.put("puuid", tuple.get(lCG_Player_Champion.lcgPuuid));
            row.put("champion", tuple.get(lCG_Player_Champion.lcgChampionName));
            row.put("play", tuple.get(lCG_Player_Champion.lcgPlayCount));

            result.add(row);
        }

        return result;
    }

}
