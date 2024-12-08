package riot.lcgs.riotlcgsbe.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomGameRequestDto {

    private String memberId;
    private String memberPw;
}
