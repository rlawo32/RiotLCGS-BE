package riot.lcgs.riotlcgsbe.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Match_Etc;

public interface LCG_Match_Etc_Repository extends JpaRepository<LCG_Match_Etc, String> {
    boolean existsLCG_Match_EtcByLcgMainVer(String ver);

    @Query(value = """
        SELECT lcg_main_ver
        FROM lcg_match_etc
        ORDER BY lcg_update_date DESC
        LIMIT 1
        """, nativeQuery = true)
    String findLatestValue();
}
