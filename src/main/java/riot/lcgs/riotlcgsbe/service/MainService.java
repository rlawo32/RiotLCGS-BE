package riot.lcgs.riotlcgsbe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import riot.lcgs.riotlcgsbe.jpa.repository.LCG_Match_Info_Repository;
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
    private final LCG_Match_Info_Repository lcgMatchInfoRepository;

    public CommonResponseDto<?> LolCustomGameDataSave(CustomGameRequestDto requestDto) {

        GameData gameData = requestDto.getGameData();
        TeamData teamData = requestDto.getTeamData();
        Long gameId = gameData.getGameId();

        // TeamId => 100 : Blue , 200 : Red

        boolean duplicationCheck = lcgMatchInfoRepository.existsLCG_Match_InfoByLcgGameId(gameId);

        if(!duplicationCheck) {
            if(DataDragonAPIVersion().isResult()) {
                Map<String, String> version = DataDragonAPIVersion().getData();
                ExtractionTool.jsonChampion = DataDragonAPIChampion().getData();
                ExtractionTool.jsonPerk = DataDragonAPIPerk().getData();

                saveService.LCGMatchInfoSave(gameId, gameData, version);
                saveService.LCGMatchMainSave(gameId, gameData);
                saveService.LCGMatchSubSave(gameId, gameData);
                saveService.LCGMatchTeamSave(gameId, gameData);
                saveService.LCGTeamLogSave(gameId, gameData, teamData, version);
                saveService.LCGPlayerStatisticsSave(gameData);
                saveService.LCGPlayerDataSave(gameData);

                return CommonResponseDto.setSuccess("Success", "저장 완료");
            } else {
                return CommonResponseDto.setFailed("통신 실패");
            }
        } else {
            return CommonResponseDto.setFailed("중복 저장");
        }
    }
}
