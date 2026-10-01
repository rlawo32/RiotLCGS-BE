package riot.lcgs.riotlcgsbe.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import riot.lcgs.riotlcgsbe.jpa.PlayerPointId;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Player_Point;

import java.util.Optional;

public interface LCG_Player_Point_Repository extends JpaRepository<LCG_Player_Point, PlayerPointId> {

    boolean existsById_LcgGameId(Long gameId);

    Optional<LCG_Player_Point> findFirstByIdLcgSummonerPuuidOrderByIdLcgGameIdDesc(String puuid);
}
