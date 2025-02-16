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
    private final SaveService saveService;
    private final MvpService mvpService;

    private final LCG_Match_Info_Repository lcgMatchInfoRepository;
    private final LCG_Player_Data_Repository lcgPlayerDataRepository;

    public CommonResponseDto<?> LolCustomGameDataSave(CustomGameRequestDto requestDto) {

        GameData gameData = requestDto.getGameData();

        validationService.ValidationCheckGameData(gameData);
        Long gameId = gameData.getGameId();

        // TeamId => 100 : Blue , 200 : Red

        boolean duplicationCheck = lcgMatchInfoRepository.existsLCG_Match_InfoByLcgGameId(gameId);

        if(!duplicationCheck) {
            if(DataDragonAPIVersion().isResult()) {
                Map<String, String> version = DataDragonAPIVersion().getData();
                ExtractionTool.jsonChampion = DataDragonAPIChampion().getData();
                ExtractionTool.jsonPerk = DataDragonAPIPerk().getData();

                mvpService.LCGMvpSelection(gameData);
                saveService.LCGMatchInfoSave(gameId, gameData, version);
                saveService.LCGMatchMainSave(gameId, gameData);
                saveService.LCGMatchSubSave(gameId, gameData);
                saveService.LCGMatchTeamSave(gameId, gameData);
                saveService.LCGTeamLogSave(gameId, gameData, version);
                saveService.LCGPlayerStatisticsSave(gameData);

                return CommonResponseDto.setSuccess("Success", "저장 완료");
            } else {
                return CommonResponseDto.setFailed("통신 실패");
            }
        } else {
            return CommonResponseDto.setFailed("중복 저장");
        }
    }
    @Transactional
    public CommonResponseDto<?> LCGPlayerDataSave(PlayerDataRequestDto requestDto) {

        try {
            GameData gameData = requestDto.getGameData();
            List<RankData> list1 = requestDto.getRankData();

            validationService.ValidationCheckGameData(gameData);
            validationService.ValidationCheckRankData(list1);
            List<ParticipantIdentities> list2 = gameData.getParticipantIdentities();

            for(int i=0; i<list2.size(); i++) {
                ParticipantIdentities participantIdentities = list2.get(i);
                Player playerData = participantIdentities.getPlayer();

                String puuid = playerData.getPuuid();
                String nickname = playerData.getGameName() + "#" + playerData.getTagLine();
                boolean existsCheck = lcgPlayerDataRepository.existsLCG_Player_DataByLcgSummonerPuuid(puuid);

                RankData rankData = list1.stream().filter(rank -> rank.getPuuid().equals(puuid)).findAny().orElse(null);

                if(existsCheck) {
                    LCG_Player_Data lcgPlayerData = lcgPlayerDataRepository.findById(puuid)
                            .orElseThrow(() -> new IllegalArgumentException("해당 플레이어가 없습니다. Puuid. : " + puuid));

                    lcgPlayerData.playerDataUpdate(playerData, rankData);
                } else {
                    lcgPlayerDataRepository.save(LCG_Player_Data.builder()
                            .lcgSummonerPuuid(puuid)
                            .lcgPlayer("")
                            .lcgSummonerNickname(nickname)
                            .lcgSummonerId(playerData.getSummonerId())
                            .lcgSummonerName(playerData.getGameName())
                            .lcgSummonerTag(playerData.getTagLine())
                            .lcgSummonerIcon(playerData.getProfileIcon())
                            .lcgRankWin(rankData.getWins())
                            .lcgRankPoint(rankData.getPoints())
                            .lcgPresentTier(rankData.getPresentTier())
                            .lcgPresentDivision(rankData.getPresentDivision())
                            .lcgPresentHighTier(rankData.getPresentHighestTier())
                            .lcgPresentHighDivision(rankData.getPresentHighestDivision())
                            .lcgPreviousTier(rankData.getPreviousTier())
                            .lcgPreviousDivision(rankData.getPreviousDivision())
                            .lcgPreviousHighTier(rankData.getPreviousHighestTier())
                            .lcgPreviousHighDivision(rankData.getPreviousHighestDivision())
                            .build());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Database Insert Failed !");
        }

        return CommonResponseDto.setSuccess("Success", "플레이어 저장 완료!");
    }

}
