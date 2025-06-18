package riot.lcgs.riotlcgsbe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import riot.lcgs.riotlcgsbe.jpa.repository.LCG_Match_Info_Repository;
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

    public CommonResponseDto<?> LolCustomGameDataSave(CustomGameRequestDto requestDto) {

        GameData gameData = requestDto.getGameData();
        List<TeamData> teamData = requestDto.getTeamData();

        String checkGameData = validationService.ValidationCheckGameData(gameData).getMessage();
        //String checkTeamData = validationService.ValidationCheckTeamData(teamData).getMessage();

        if(checkGameData.equals("Success")) {

            Long gameId = gameData.getGameId();

            // 경우2(원딜) -> 경우1(알코올) 변환
            for(int i=0; i<gameData.getParticipantIdentities().size(); i++) {
                if(gameData.getParticipantIdentities().get(i).getPlayer().getPuuid().equals("8c77580d-04d8-5885-94a0-6fea71350fa4")) {
                    gameData.getParticipantIdentities().get(i).getPlayer().setPuuid("0d698fc9-7d69-5c0f-8c10-14a827e8de2d");
                }
            }
            for(int i=0; i<teamData.size(); i++) {
                if(teamData.get(i).getPuuid().equals("8c77580d-04d8-5885-94a0-6fea71350fa4")) {
                    teamData.get(i).setPuuid("0d698fc9-7d69-5c0f-8c10-14a827e8de2d");
                }
            }

            // 25년 - 2월8일 all & 2월15일 3~6번째 게임 & 5월4일 all, 승준이가 현석 아이디 사용 / 현석 -> 승준 변환
            if(gameData.getGameCreationDate().split("T")[0].equals("2025-02-08")
               || (gameData.getGameCreationDate().split("T")[0].equals("2025-02-15") && gameData.getGameDuration() != 1595)
               || (gameData.getGameCreationDate().split("T")[0].equals("2025-02-15") && gameData.getGameDuration() != 1272)
               || gameData.getGameCreationDate().split("T")[0].equals("2025-05-04")
            ) {
                for(int i=0; i<gameData.getParticipantIdentities().size(); i++) {
                    if(gameData.getParticipantIdentities().get(i).getPlayer().getPuuid().equals("1127fed4-642a-5b70-bab9-1c7a326ca923")) {
                        gameData.getParticipantIdentities().get(i).getPlayer().setPuuid("864ff5ac-b218-55fd-94ba-cb9cabe66ce4");
                    }
                }
                for(int i=0; i<teamData.size(); i++) {
                    if(teamData.get(i).getPuuid().equals("1127fed4-642a-5b70-bab9-1c7a326ca923")) {
                        teamData.get(i).setPuuid("864ff5ac-b218-55fd-94ba-cb9cabe66ce4");
                    }
                }
            }

            // 25년 - 3월25일 1번째 경기 성재가 다른 아이디 사용 / 다른 아이디 -> 성재 변환
            if(gameData.getGameCreationDate().split("T")[0].equals("2025-03-25")) {
                for(int i=0; i<gameData.getParticipantIdentities().size(); i++) {
                    if(gameData.getParticipantIdentities().get(i).getPlayer().getPuuid().equals("ce41bfe2-7ce4-5965-82d4-729a01bf4568")) {
                        gameData.getParticipantIdentities().get(i).getPlayer().setPuuid("1e062cfe-c62e-53ef-9145-ab0d6c76d40d");
                    }
                }
                for(int i=0; i<teamData.size(); i++) {
                    if(teamData.get(i).getPuuid().equals("ce41bfe2-7ce4-5965-82d4-729a01bf4568")) {
                        teamData.get(i).setPuuid("1e062cfe-c62e-53ef-9145-ab0d6c76d40d");
                    }
                }
            }

            // 25년 - 4월26일 1~6번째 게임, 승준이가 광호 아이디 사용 / 광호 -> 승준 변환
            if((gameData.getGameCreationDate().split("T")[0].equals("2025-04-26") && gameData.getGameDuration() != 1856)
                || (gameData.getGameCreationDate().split("T")[0].equals("2025-04-26") && gameData.getGameDuration() != 2683)
            ) {
                for (int i = 0; i < gameData.getParticipantIdentities().size(); i++) {
                    if (gameData.getParticipantIdentities().get(i).getPlayer().getPuuid().equals("60e3571d-2b64-5e2b-b9ba-c73789b86639")) {
                        gameData.getParticipantIdentities().get(i).getPlayer().setPuuid("864ff5ac-b218-55fd-94ba-cb9cabe66ce4");
                    }
                }
                for (int i = 0; i < teamData.size(); i++) {
                    if (teamData.get(i).getPuuid().equals("60e3571d-2b64-5e2b-b9ba-c73789b86639")) {
                        teamData.get(i).setPuuid("864ff5ac-b218-55fd-94ba-cb9cabe66ce4");
                    }
                }
            }

            // TeamId => 100 : Blue , 200 : Red
            boolean duplicationCheck = lcgMatchInfoRepository.existsLCG_Match_InfoByLcgGameId(gameId);

            if(!duplicationCheck) {
                if(DataDragonAPIVersion().isResult()) {
                    Map<String, String> version = DataDragonAPIVersion().getData();
                    ExtractionTool.jsonChampion = DataDragonAPIChampion().getData();
                    ExtractionTool.jsonPerk = DataDragonAPIPerk().getData();

                    mvpService.LCGMvpSelection(gameData);
                    playerService.LCGPlayerRelativeSave(gameData, teamData);
                    playerService.LCGPlayerChampionSave(gameData);
                    playerService.LCGPlayerStatisticsSave(gameData);
                    //playerService.LCGPlayerPositionSave(gameData, teamData);
                    matchService.LCGMatchInfoSave(gameId, gameData, version);
                    matchService.LCGTeamLogSave(gameId, gameData, version);
                    matchService.LCGMatchMainSave(gameId, gameData);
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
                playerService.LCGPlayerRankingSave();
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

    public CommonResponseDto<?> testService() {

        try {
            playerService.LCGPlayerRankingSave();

            return CommonResponseDto.setSuccess("TEST 완료!", "Success");
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Database Insert Failed !");
        }
    }
}
