package riot.lcgs.riotlcgsbe.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Match_Main;

import java.util.List;
import java.util.Optional;

public interface LCG_Match_Main_Repository extends JpaRepository<LCG_Match_Main, Long> {

    Optional<LCG_Match_Main> findTopByLcgGameSetContainingOrderByRowNumDesc(String gameSet);

    List<LCG_Match_Main> findByLcgGameId(Long gameId);
}
