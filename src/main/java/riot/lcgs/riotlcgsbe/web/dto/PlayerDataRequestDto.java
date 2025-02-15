package riot.lcgs.riotlcgsbe.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import riot.lcgs.riotlcgsbe.web.dto.object.GameData;
import riot.lcgs.riotlcgsbe.web.dto.object.RankData;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDataRequestDto {

    private GameData gameData;
    private List<RankData> rankData;
}
