package riot.lcgs.riotlcgsbe.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Player_Data;

public interface LCG_Player_Data_Repository extends JpaRepository<LCG_Player_Data, String> {
    boolean existsLCG_Player_DataByLcgSummonerPuuid(String puuid);
}
