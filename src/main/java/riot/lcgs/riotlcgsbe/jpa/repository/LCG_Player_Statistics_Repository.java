package riot.lcgs.riotlcgsbe.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Player_Statistics;

public interface LCG_Player_Statistics_Repository extends JpaRepository<LCG_Player_Statistics, String> {
    boolean existsLCG_Player_StatisticsByLcgSummonerPuuid(String puuid);
}
