package riot.lcgs.riotlcgsbe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import riot.lcgs.riotlcgsbe.util.ExtractionTool;
import riot.lcgs.riotlcgsbe.web.dto.CommonResponseDto;
import riot.lcgs.riotlcgsbe.web.dto.CustomGameRequestDto;
import riot.lcgs.riotlcgsbe.web.dto.object.*;

import java.util.Map;

import static riot.lcgs.riotlcgsbe.service.HttpService.*;

@RequiredArgsConstructor
@Service
public class MainService {

    private final SaveService saveService;

    public CommonResponseDto<?> LolCustomGameDataSave(CustomGameRequestDto requestDto) {

        GameData gameData = requestDto.getGameData();
        TeamData teamData = requestDto.getTeamData();
        Long gameId = gameData.getGameId();

        // 동일한 gameId로 insert 불가

        Map<String, String> version = DataDragonAPIVersion().getData();
        ExtractionTool.jsonChampion = DataDragonAPIChampion().getData();
        ExtractionTool.jsonPerk = DataDragonAPIPerk().getData();

        saveService.LCGMatchInfoSave(gameId, gameData, version);
        saveService.LCGMatchMainSave(gameId, gameData);
        saveService.LCGMatchSubSave(gameId, gameData);
        saveService.LCGMatchTeamSave(gameId, gameData);

        return CommonResponseDto.setSuccess("Success", "");
    }
}
