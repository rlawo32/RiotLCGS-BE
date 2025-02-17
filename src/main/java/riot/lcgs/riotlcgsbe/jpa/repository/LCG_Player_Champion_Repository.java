package riot.lcgs.riotlcgsbe.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Player_Champion;

import java.util.Optional;

public interface LCG_Player_Champion_Repository extends JpaRepository<LCG_Player_Champion, Long> {

    boolean existsLCG_Player_ChampionByLcgPuuidAndLcgChampionId(String puuid, int championId);

    Optional<LCG_Player_Champion> findByLcgPuuidAndLcgChampionId(String puuid, int championId);
}
