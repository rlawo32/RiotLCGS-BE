package riot.lcgs.riotlcgsbe.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Player_Ranking;

public interface LCG_Player_Ranking_Repository extends JpaRepository<LCG_Player_Ranking, Long> {
    boolean existsLCG_Player_RankingByLcgRankingCount(Long count);
}
