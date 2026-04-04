package riot.lcgs.riotlcgsbe.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import riot.lcgs.riotlcgsbe.web.dto.object.GameData;
import riot.lcgs.riotlcgsbe.web.dto.object.RankData;
import riot.lcgs.riotlcgsbe.web.dto.object.TeamData;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiTestDataRequestDto {

    private GameData gameData;
    private List<TeamData> teamData;
    private List<RankData> rankData;
}
