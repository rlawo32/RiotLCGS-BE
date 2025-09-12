package riot.lcgs.riotlcgsbe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import riot.lcgs.riotlcgsbe.jpa.domain.TEST;
import riot.lcgs.riotlcgsbe.jpa.repository.LCG_Match_Info_Repository;
import riot.lcgs.riotlcgsbe.jpa.repository.TEST_Repository;
import riot.lcgs.riotlcgsbe.util.ExtractionTool;
import riot.lcgs.riotlcgsbe.web.dto.CommonResponseDto;
import riot.lcgs.riotlcgsbe.web.dto.CustomGameRequestDto;
import riot.lcgs.riotlcgsbe.web.dto.PlayerDataRequestDto;
import riot.lcgs.riotlcgsbe.web.dto.object.*;

import java.util.List;
import java.util.Map;

import static riot.lcgs.riotlcgsbe.service.HttpService.*;
import static riot.lcgs.riotlcgsbe.util.AccountCheckTool.playerAccountChk;

@RequiredArgsConstructor
@Service
public class MainService {

    private final ValidationService validationService;
    private final MatchService matchService;
    private final PlayerService playerService;
    private final MvpService mvpService;
    private final ImageService imageService;

    private final LCG_Match_Info_Repository lcgMatchInfoRepository;
    private final TEST_Repository testRepository;

    public CommonResponseDto<?> LolCustomGameDataSave(CustomGameRequestDto requestDto) {

        GameData gameData = requestDto.getGameData();
        List<TeamData> teamData = requestDto.getTeamData();

        String checkGameData = validationService.ValidationCheckGameData(gameData).getMessage();
        //String checkTeamData = validationService.ValidationCheckTeamData(teamData).getMessage();

        if(checkGameData.equals("Success")) {

            Long gameId = gameData.getGameId();

            gameData = (GameData) playerAccountChk(gameData, teamData).get("gameData");
            teamData = (List<TeamData>) playerAccountChk(gameData, teamData).get("teamData");

            // TeamId => 100 : Blue , 200 : Red
            boolean duplicationCheck = lcgMatchInfoRepository.existsLCG_Match_InfoByLcgGameId(gameId);

            if(!duplicationCheck) {
                if(DataDragonAPIVersion().isResult()) {
                    Map<String, String> version = DataDragonAPIVersion().getData();
                    ExtractionTool.jsonChampion = DataDragonAPIChampion().getData();
                    ExtractionTool.jsonPerk = DataDragonAPIPerk().getData();

                    playerService.LCGPlayerRelativeSave(gameData, teamData);
                    playerService.LCGPlayerChampionSave(gameData);
                    playerService.LCGPlayerStatisticsSave(gameData);
                    playerService.LCGPlayerPositionSave(gameData, teamData);
                    matchService.LCGMatchInfoSave(gameId, gameData, version);
                    matchService.LCGMatchEtcSave(version);
                    matchService.LCGTeamLogSave(gameId, gameData, version);
                    matchService.LCGMatchMainSave(gameId, gameData, teamData);
                    matchService.LCGMatchSubSave(gameId, gameData);
                    matchService.LCGMatchTeamSave(gameId, gameData);
                    playerService.LCGPlayerRankingSave();

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
                //playerService.LCGPlayerRankingSave();
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

    @Transactional
//    @Scheduled(cron = "0 0 0 * * ?") // 매일 24시
    public void LCGCustomGameImageSave() {

        try {
            Map<String, String> version = DataDragonAPIVersion().getData();
            String imageUpdate = matchService.LCGMatchEtcSave(version).getData();
            if(imageUpdate.equals("N")) {
                imageService.DataDragonImageUpload(version.get("ver"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Database Insert Failed !");
        }
    }

    @Transactional
    public CommonResponseDto<?> testService() {

        try {
            Map<String, String> version = DataDragonAPIVersion().getData();
            imageService.DataDragonImageUpload(version.get("ver"));
//            playerService.LCGPlayerRankingSave();
//            testRepository.save(TEST.builder()
//                    .testContent("TEST1")
//                    .testVerify("N")
//                    .build());

            return CommonResponseDto.setSuccess("TEST 완료!", "Success");
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("TEST 실패!");
        }
    }
}
