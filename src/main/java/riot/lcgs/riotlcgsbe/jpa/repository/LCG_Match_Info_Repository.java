package riot.lcgs.riotlcgsbe.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Match_Info;

import java.util.Optional;

public interface LCG_Match_Info_Repository extends JpaRepository<LCG_Match_Info, Long> {
    boolean existsLCG_Match_InfoByLcgGameId(Long gameId);

    Optional<LCG_Match_Info> findTopByLcgGameSetStartingWithOrderByLcgGameIdDesc(String gameSet);
}
