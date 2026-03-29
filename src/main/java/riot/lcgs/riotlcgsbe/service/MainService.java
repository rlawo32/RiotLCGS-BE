package riot.lcgs.riotlcgsbe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Match_Main;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Player_Data;
import riot.lcgs.riotlcgsbe.jpa.domain.TEST;
import riot.lcgs.riotlcgsbe.jpa.repository.LCG_Match_Info_Repository;
import riot.lcgs.riotlcgsbe.jpa.repository.LCG_Match_Main_Repository;
import riot.lcgs.riotlcgsbe.jpa.repository.LCG_Player_Data_Repository;
import riot.lcgs.riotlcgsbe.jpa.repository.TEST_Repository;
import riot.lcgs.riotlcgsbe.util.ExtractionTool;
import riot.lcgs.riotlcgsbe.web.dto.CommonResponseDto;
import riot.lcgs.riotlcgsbe.web.dto.CustomGameRequestDto;
import riot.lcgs.riotlcgsbe.web.dto.PlayerDataRequestDto;
import riot.lcgs.riotlcgsbe.web.dto.object.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final LCG_Match_Main_Repository lcgMatchMainRepository;
    private final LCG_Player_Data_Repository lcgPlayerDataRepository;
    private final TEST_Repository testRepository;

    @Transactional
    public CommonResponseDto<?> LolCustomGameDataSave(CustomGameRequestDto requestDto) {
        GameData gameData = requestDto.getGameData();
        List<TeamData> teamData = requestDto.getTeamData();

        // 1. 데이터 검증
        String validationMsg = validationService.ValidationCheckGameData(gameData).getMessage();
        //String checkTeamData = validationService.ValidationCheckTeamData(teamData).getMessage();
        if (!"Success".equals(validationMsg)) {
            return CommonResponseDto.setFailed("검증 오류: " + validationMsg);
        }

        // 2. 플레이어 체크
        List<String> nameList = teamData.stream()
                .map(TeamData::getName)
                .collect(Collectors.toList());
        List<LCG_Player_Data> existingPlayers =lcgPlayerDataRepository.findAllByLcgPlayerIn(nameList);
        if(existingPlayers.size() != nameList.size()) {
            Set<String> existingNames = existingPlayers.stream()
                    .map(LCG_Player_Data::getLcgPlayer)
                    .collect(Collectors.toSet());
            List<String> missingNames = nameList.stream()
                    .filter(name -> !existingNames.contains(name))
                    .collect(Collectors.toList());
            return CommonResponseDto.setFailed("존재하지 않는 플레이어: " + String.join(", ", missingNames));
        }

        // 3. 중복 체크
        Long gameId = gameData.getGameId();
        if(lcgMatchInfoRepository.existsLCG_Match_InfoByLcgGameId(gameId)) {
            return CommonResponseDto.setFailed("중복 저장");
        }

        // 4. 외부 API 및 버전 체크
        CommonResponseDto<Map<String, String>> versionResult = DataDragonAPIVersion();
        CommonResponseDto<String> championResult = DataDragonAPIChampion();
        CommonResponseDto<List<Perk>> perkResult = DataDragonAPIPerk();
        if(!versionResult.isResult() || !championResult.isResult() || !perkResult.isResult()) {
            return CommonResponseDto.setFailed("통신 실패");
        }

        gameData = (GameData) playerAccountChk(gameData, teamData).get("gameData");
        teamData = (List<TeamData>) playerAccountChk(gameData, teamData).get("teamData");

        Map<String, String> version = versionResult.getData();
        ExtractionTool.jsonChampion = championResult.getData();
        ExtractionTool.jsonPerk = perkResult.getData();

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
        playerService.LCGPlayerWinningStreakUpdate(gameData, teamData);
        playerService.LCGPlayerRankingSave();

        return CommonResponseDto.setSuccess("저장 완료", "Success");
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
//            Map<String, String> version = DataDragonAPIVersion().getData();
//            imageService.DataDragonImageUpload(version.get("ver"));
//            playerService.LCGPlayerRankingSave();
//            testRepository.save(TEST.builder()
//                    .testContent("TEST1")
//                    .testVerify("N")
//                    .build());

            // 게임 세트 구하기
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime minus = now.minusHours(4);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd");
            String todayGameSet = minus.format(formatter);
            String gameSet = "";

            Optional<LCG_Match_Main> lcgMatchMainGameSet = lcgMatchMainRepository.findTopByLcgGameSetContainingOrderByRowNumDesc(todayGameSet);

            if(lcgMatchMainGameSet.isPresent()) {
                String prevSet = lcgMatchMainGameSet.get().getLcgGameSet(); // ex. 09/18-SET_01
                int nextNumber = Integer.parseInt(prevSet.split("_")[1]) + 1;
                gameSet = prevSet.split("_")[0] + "_" +String.format("%02d", nextNumber); // ex. 09/18-SET_02
            } else {
                gameSet = todayGameSet + "-SET_01";
            }

            Long gameId = 7800181301L;

            List<LCG_Match_Main> lcgMatchMain = lcgMatchMainRepository.findByLcgGameId(gameId);
            for(int i=0; i<lcgMatchMain.size(); i++) {
                lcgMatchMain.get(i).gameSetUpdate(gameSet);
            }

            return CommonResponseDto.setSuccess("TEST 완료!", "Success");
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("TEST 실패!");
        }
    }
}
