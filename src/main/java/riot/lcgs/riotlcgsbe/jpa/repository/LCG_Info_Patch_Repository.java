package riot.lcgs.riotlcgsbe.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import riot.lcgs.riotlcgsbe.jpa.PatchNoteId;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Info_Patch;

public interface LCG_Info_Patch_Repository extends JpaRepository<LCG_Info_Patch, PatchNoteId> {

    boolean existsByIdLcgPatchVersionAndIdLcgPatchSection(
            String lcgPatchVersion,
            String lcgPatchSection
    );

    @Query(value = """
        SELECT lcg_patch_version
        FROM lcg_info_patch
        ORDER BY lcg_created_date DESC
        LIMIT 1
        """, nativeQuery = true)
    String findLatestValue();
}
