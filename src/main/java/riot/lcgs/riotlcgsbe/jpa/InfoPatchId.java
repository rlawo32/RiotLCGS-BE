package riot.lcgs.riotlcgsbe.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InfoPatchId implements Serializable {

    @Column(name = "lcg_patch_version")
    private String lcgPatchVersion;

    @Column(name = "lcg_patch_section")
    private String lcgPatchSection;
}
