package riot.lcgs.riotlcgsbe.jpa.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import riot.lcgs.riotlcgsbe.jpa.InfoPatchId;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "LCG_Info_Patch")
public class LCG_Info_Patch {

    @EmbeddedId
    private InfoPatchId id;

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
    public LCG_Info_Patch(String lcgPatchVersion, String lcgPatchSection, String lcgPatchHtml,
                          String lcgPatchUrl, String lcgCreatedDate) {
        this.id = new InfoPatchId(lcgPatchVersion, lcgPatchSection);
        this.lcgPatchHtml = lcgPatchHtml;
        this.lcgPatchUrl = lcgPatchUrl;
        this.lcgCreatedDate = lcgCreatedDate;
    }
}
