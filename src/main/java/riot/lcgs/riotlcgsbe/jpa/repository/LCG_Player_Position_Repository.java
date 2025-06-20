package riot.lcgs.riotlcgsbe.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Player_Position;

public interface LCG_Player_Position_Repository extends JpaRepository<LCG_Player_Position, String> {

    boolean existsLCG_Player_PositionByLcgSummonerPuuid(String puuid);
}
