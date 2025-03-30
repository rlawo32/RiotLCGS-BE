package riot.lcgs.riotlcgsbe.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Player_Data;

import java.util.Optional;

public interface LCG_Player_Data_Repository extends JpaRepository<LCG_Player_Data, String>, LCG_Player_Data_RepositoryCustom {
    boolean existsLCG_Player_DataByLcgSummonerPuuid(String puuid);

    Optional<LCG_Player_Data> findByLcgPlayer(String name);
}
