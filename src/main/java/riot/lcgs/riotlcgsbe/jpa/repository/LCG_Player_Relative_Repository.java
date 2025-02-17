package riot.lcgs.riotlcgsbe.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Player_Relative;

import java.util.Optional;

public interface LCG_Player_Relative_Repository extends JpaRepository<LCG_Player_Relative, Long> {

    boolean existsLCG_Player_RelativeByLcgPersonPuuidAndLcgMatchLineAndLcgOpponentPuuid
            (String personPuuid, String line, String opponentPuuid);

    Optional<LCG_Player_Relative> findByLcgPersonPuuidAndLcgMatchLineAndLcgOpponentPuuid
            (String personPuuid, String line, String opponentPuuid);
}
