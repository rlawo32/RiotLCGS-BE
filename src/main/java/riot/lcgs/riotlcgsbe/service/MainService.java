package riot.lcgs.riotlcgsbe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Player_Data;
import riot.lcgs.riotlcgsbe.jpa.repository.LCG_Match_Info_Repository;
import riot.lcgs.riotlcgsbe.jpa.repository.LCG_Player_Data_Repository;
import riot.lcgs.riotlcgsbe.util.ExtractionTool;
import riot.lcgs.riotlcgsbe.web.dto.CommonResponseDto;
import riot.lcgs.riotlcgsbe.web.dto.CustomGameRequestDto;
import riot.lcgs.riotlcgsbe.web.dto.PlayerDataRequestDto;
import riot.lcgs.riotlcgsbe.web.dto.object.*;

import java.util.List;
import java.util.Map;

import static riot.lcgs.riotlcgsbe.service.HttpService.*;

@RequiredArgsConstructor
@Service
public class MainService {

    private final ValidationService validationService;
    private final MatchService matchService;
    private final PlayerService playerService;
    private final MvpService mvpService;

    private final LCG_Match_Info_Repository lcgMatchInfoRepository;
    private final LCG_Player_Data_Repository lcgPlayerDataRepository;

    public CommonResponseDto<?> LolCustomGameDataSave(CustomGameRequestDto requestDto) {

        GameData gameData = requestDto.getGameData();
        List<TeamData> teamData = requestDto.getTeamData();

        String checkGameData = validationService.ValidationCheckGameData(gameData).getMessage();

        if(checkGameData.equals("Success")) {

            Long gameId = gameData.getGameId();

            // TeamId => 100 : Blue , 200 : Red

            boolean duplicationCheck = lcgMatchInfoRepository.existsLCG_Match_InfoByLcgGameId(gameId);

            if(duplicationCheck) {
                if(DataDragonAPIVersion().isResult()) {
                    Map<String, String> version = DataDragonAPIVersion().getData();
                    ExtractionTool.jsonChampion = DataDragonAPIChampion().getData();
                    ExtractionTool.jsonPerk = DataDragonAPIPerk().getData();

//                    mvpService.LCGMvpSelection(gameData);
//                    matchService.LCGMatchInfoSave(gameId, gameData, version);
//                    matchService.LCGMatchMainSave(gameId, gameData);
//                    matchService.LCGMatchSubSave(gameId, gameData);
//                    matchService.LCGMatchTeamSave(gameId, gameData);
//                    matchService.LCGTeamLogSave(gameId, gameData, version);
//                    playerService.LCGPlayerStatisticsSave(gameData);
                    playerService.LCGPlayerChampionSave(gameData);
                    playerService.LCGPlayerRelativeSave(gameData, teamData);

                    return CommonResponseDto.setSuccess("저장 완료", "Success");
                } else {
                    return CommonResponseDto.setFailed("통신 실패");
                }
            } else {
                return CommonResponseDto.setFailed("중복 저장");
            }
        } else {
            return CommonResponseDto.setFailed("검증 오류 발생");
        }
    }
    @Transactional
    public CommonResponseDto<?> LCGCustomGamePlayerSave(PlayerDataRequestDto requestDto) {

        try {
            GameData gameData = requestDto.getGameData();
            List<RankData> rankData = requestDto.getRankData();

            String checkGameData = validationService.ValidationCheckGameData(gameData).getMessage();
            String checkRankData = validationService.ValidationCheckRankData(rankData).getMessage();

            if(checkGameData.equals("Success") && checkRankData.equals("Success")) {
                playerService.LCGPlayerDataSave(gameData, rankData);

                return CommonResponseDto.setSuccess("플레이어 저장 완료!", "Success");
            } else {
                return CommonResponseDto.setFailed("검증 오류 발생");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Database Insert Failed !");
        }
    }

}
