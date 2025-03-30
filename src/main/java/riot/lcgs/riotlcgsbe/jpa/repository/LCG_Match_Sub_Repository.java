package riot.lcgs.riotlcgsbe.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Match_Sub;

public interface LCG_Match_Sub_Repository extends JpaRepository<LCG_Match_Sub, Long>, LCG_Match_Sub_RepositoryCustom {

}
