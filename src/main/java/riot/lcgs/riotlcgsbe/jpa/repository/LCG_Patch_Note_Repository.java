package riot.lcgs.riotlcgsbe.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import riot.lcgs.riotlcgsbe.jpa.PatchNoteId;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Patch_Note;

public interface LCG_Patch_Note_Repository extends JpaRepository<LCG_Patch_Note, PatchNoteId> {

    boolean existsByIdLcgPatchVersionAndIdLcgPatchSection(
            String lcgPatchVersion,
            String lcgPatchSection
    );
}
