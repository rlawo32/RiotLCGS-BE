package riot.lcgs.riotlcgsbe.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Info_Champion;

import java.util.Optional;

public interface LCG_Info_Champion_Repository extends JpaRepository<LCG_Info_Champion, Long> {

    Optional<LCG_Info_Champion> findByLcgChampionName(String championName);
}
