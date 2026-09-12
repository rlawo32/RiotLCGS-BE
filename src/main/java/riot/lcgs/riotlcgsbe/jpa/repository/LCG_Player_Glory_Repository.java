package riot.lcgs.riotlcgsbe.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Player_Glory;

import java.util.Optional;

public interface LCG_Player_Glory_Repository extends JpaRepository<LCG_Player_Glory, Long> {

    boolean existsLCG_Player_GloryByLcgSummonerPuuidAndLcgGloryIdAndLcgGloryActive(String puuid, String gloryId, String gloryActive);

    Optional<LCG_Player_Glory> findByLcgGloryIdAndLcgGloryUniqueAndLcgGloryActive(String gloryId, String gloryUnique, String gloryActive);

    Optional<LCG_Player_Glory> findByLcgSummonerPuuidAndLcgGloryIdAndLcgGloryActive(String puuid, String gloryId, String gloryActive);

    boolean existsByLcgSummonerPuuidAndLcgGloryIdAndLcgGloryActiveAndLcgGloryTitleContaining(String puuid, String gloryId, String gloryActive, String gloryTitle);
}
