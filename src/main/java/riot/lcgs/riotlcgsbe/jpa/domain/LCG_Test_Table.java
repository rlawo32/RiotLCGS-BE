package riot.lcgs.riotlcgsbe.jpa.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "LCG_Test_Table")
public class LCG_Test_Table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_num")
    private Long rowNum;

    @NotNull
    @Column(name = "test_content")
    private String testContent;

    @NotNull
    @Column(name = "test_verify")
    private String testVerify;

    @NotNull
    @Column(name = "test_date")
    private String testDate;

    @Builder
    public LCG_Test_Table(String testContent, String testVerify, String testDate) {
        this.testContent = testContent;
        this.testVerify = testVerify;
        this.testDate = testDate;
    }
}
