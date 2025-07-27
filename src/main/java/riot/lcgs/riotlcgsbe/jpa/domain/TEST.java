package riot.lcgs.riotlcgsbe.jpa.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "TEST")
public class TEST {

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

    @Builder
    public TEST(String testContent, String testVerify) {
        this.testContent = testContent;
        this.testVerify = testVerify;
    }
}
