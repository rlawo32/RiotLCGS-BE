package riot.lcgs.riotlcgsbe.jpa.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import riot.lcgs.riotlcgsbe.jpa.PatchNoteId;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "LCG_Patch_Note")
public class LCG_Patch_Note {

    @EmbeddedId
    private PatchNoteId id;

    @NotNull
    @Column(name = "lcg_patch_html")
    private String lcgPatchHtml;

    @NotNull
    @Column(name = "lcg_patch_url")
    private String lcgPatchUrl;

    @NotNull
    @Column(name = "lcg_created_date")
    private String lcgCreatedDate;

    @Builder
    public LCG_Patch_Note(String lcgPatchVersion, String lcgPatchSection, String lcgPatchHtml,
                          String lcgPatchUrl, String lcgCreatedDate) {
        this.id = new PatchNoteId(lcgPatchVersion, lcgPatchSection);
        this.lcgPatchHtml = lcgPatchHtml;
        this.lcgPatchUrl = lcgPatchUrl;
        this.lcgCreatedDate = lcgCreatedDate;
    }
}
