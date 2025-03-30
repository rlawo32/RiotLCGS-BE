package riot.lcgs.riotlcgsbe.jpa.repository;

import java.util.List;
import java.util.Map;

public interface LCG_Player_Statistics_RepositoryCustom {

    List<Map<String, Object>> findByAllWinningRate();

    List<Map<String, Object>> findByAllMvpRank();

    List<Map<String, Object>> findByAllAceRank();

    List<Map<String, Object>> findByAllKdaRank();

    List<Map<String, Object>> findByAllVisionRank();

    List<Map<String, Object>> findByAllGoldRank();

    List<Map<String, Object>> findByAllDeathRank();

    List<Map<String, Object>> findByAllMultiKillRank();

    List<Map<String, Object>> findByAllDemolisherRank();
}
