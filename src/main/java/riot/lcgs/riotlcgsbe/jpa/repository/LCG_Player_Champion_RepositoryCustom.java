package riot.lcgs.riotlcgsbe.jpa.repository;

import java.util.List;
import java.util.Map;

public interface LCG_Player_Champion_RepositoryCustom {

    List<Map<String, Object>> findPerfectWinner();

    List<Map<String, Object>> findChampionMaster();
}
