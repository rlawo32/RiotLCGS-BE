package riot.lcgs.riotlcgsbe.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Match_Main;
import riot.lcgs.riotlcgsbe.jpa.domain.TEST;

import java.util.Optional;

public interface TEST_Repository extends JpaRepository<TEST, Long> {

    Optional<TEST> findTESTByTestContent(String gameSet);
}
