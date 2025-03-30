package riot.lcgs.riotlcgsbe.jpa.repository;

import java.util.List;
import java.util.Map;

public interface LCG_Match_Sub_RepositoryCustom {

    List<Map<String, Object>> findByAllAvgDpmRank();
    List<Map<String, Object>> findByAllAvgGpmRank();
    List<Map<String, Object>> findByAllAvgDpgRank();

}
