package riot.lcgs.riotlcgsbe.jpa.repository;

import java.util.List;
import java.util.Map;

public interface LCG_Player_Data_RepositoryCustom {

    List<Map<String, Object>> findByAllTierRank();
}
