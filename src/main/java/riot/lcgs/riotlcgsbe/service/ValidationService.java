package riot.lcgs.riotlcgsbe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import riot.lcgs.riotlcgsbe.web.dto.CommonResponseDto;
import riot.lcgs.riotlcgsbe.web.dto.object.*;

import java.util.List;

import static riot.lcgs.riotlcgsbe.util.ValidationTool.*;

@RequiredArgsConstructor
@Service
public class ValidationService {

    @Transactional
    public CommonResponseDto<?> ValidationCheckGameData(GameData gameData) {

        try {
            validationChkLong(gameData.getGameId());
            validationChkString(gameData.getGameCreationDate());
            validationChkString(gameData.getGameMode());
            validationChkString(gameData.getGameType());
            validationChkInteger(gameData.getGameDuration());
            validationChkInteger(gameData.getMapId());

            List<ParticipantIdentities> list1 = gameData.getParticipantIdentities();
            List<Participants> list2 = gameData.getParticipants();
            List<Teams> list3 = gameData.getTeams();

            for(int i=0; i<list1.size(); i++) {
                ParticipantIdentities participantIdentities = list1.get(i);
                Participants participants = list2.get(i);
                Player playerData = participantIdentities.getPlayer();
                Stats statsData = participants.getStats();

                // Participants Validation Check
                validationChkInteger(participantIdentities.getParticipantId());
                validationChkInteger(participants.getChampionId());
                validationChkInteger(participants.getParticipantId());
                validationChkInteger(participants.getSpell1Id());
                validationChkInteger(participants.getSpell2Id());
                validationChkInteger(participants.getTeamId());

                // Player Validation Check
                validationChkInteger(playerData.getAccountId());
                validationChkInteger(playerData.getCurrentAccountId());
                validationChkString(playerData.getCurrentPlatformId());
                validationChkString(playerData.getGameName());
                validationChkString(playerData.getMatchHistoryUri());
                validationChkString(playerData.getPlatformId());
                validationChkInteger(playerData.getProfileIcon());
                validationChkString(playerData.getPuuid());
                validationChkLong(playerData.getSummonerId());
                validationChkString(playerData.getSummonerName());
                validationChkString(playerData.getTagLine());

                // Stats Validation Check
                validationChkInteger(statsData.getKills());
                validationChkInteger(statsData.getDeaths());
                validationChkInteger(statsData.getAssists());
                validationChkInteger(statsData.getChampLevel());
                validationChkInteger(statsData.getPerk0());
                validationChkInteger(statsData.getPerk1());
                validationChkInteger(statsData.getPerk2());
                validationChkInteger(statsData.getPerk3());
                validationChkInteger(statsData.getPerk4());
                validationChkInteger(statsData.getPerk5());
                validationChkInteger(statsData.getPerkSubStyle());
                validationChkInteger(statsData.getItem0());
                validationChkInteger(statsData.getItem1());
                validationChkInteger(statsData.getItem2());
                validationChkInteger(statsData.getItem3());
                validationChkInteger(statsData.getItem4());
                validationChkInteger(statsData.getItem5());
                validationChkInteger(statsData.getItem6());
                validationChkInteger(statsData.getTotalDamageDealtToChampions());
                validationChkInteger(statsData.getTotalDamageTaken());
                validationChkInteger(statsData.getTotalHeal());
                validationChkInteger(statsData.getTotalMinionsKilled());
                validationChkInteger(statsData.getNeutralMinionsKilled());
                validationChkInteger(statsData.getDoubleKills());
                validationChkInteger(statsData.getTripleKills());
                validationChkInteger(statsData.getQuadraKills());
                validationChkInteger(statsData.getPentaKills());
                validationChkInteger(statsData.getWardsKilled());
                validationChkInteger(statsData.getWardsPlaced());
                validationChkInteger(statsData.getVisionWardsBoughtInGame());
                validationChkInteger(statsData.getVisionScore());
                validationChkInteger(statsData.getGoldEarned());
                validationChkInteger(statsData.getGoldSpent());
                validationChkInteger(statsData.getTimeCCingOthers());
                validationChkInteger(statsData.getTurretKills());
                validationChkInteger(statsData.getInhibitorKills());
                validationChkInteger(statsData.getDamageDealtToTurrets());
                validationChkInteger(statsData.getTurretKills());
                validationChkBoolean(statsData.getWin());
                validationChkBoolean(statsData.getFirstBloodKill());
                validationChkBoolean(statsData.getFirstTowerKill());
                validationChkBoolean(statsData.getFirstInhibitorKill());
                validationChkBoolean(statsData.getFirstBloodAssist());
                validationChkBoolean(statsData.getFirstTowerAssist());
                validationChkBoolean(statsData.getFirstInhibitorAssist());
            }

            validationChkString(gameData.getGameCreationDate());

            for(Teams teams : list3) {
                List<Bans> bans = teams.getBans();

                validationChkInteger(teams.getTeamId());
                validationChkString(teams.getWin());
                validationChkBoolean(teams.getFirstBlood());
                validationChkBoolean(teams.getFirstTower());
                validationChkBoolean(teams.getFirstInhibitor());
                validationChkBoolean(teams.getFirstDargon());
                validationChkBoolean(teams.getFirstBaron());
                validationChkInteger(teams.getDragonKills());
                validationChkInteger(teams.getBaronKills());
                validationChkInteger(teams.getHordeKills());
                validationChkInteger(teams.getRiftHeraldKills());
                validationChkInteger(teams.getTowerKills());
                validationChkInteger(teams.getInhibitorKills());
                for(Bans ban : bans) {
                    validationChkInteger(ban.getChampionId());
                }
            }
            return CommonResponseDto.setSuccess("Success", "Data 검사 완료");
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Database Insert Failed !");
        }
    }

    @Transactional
    public CommonResponseDto<?> ValidationCheckRankData(List<RankData> rankData) {

        try {
            for(RankData data : rankData) {
                validationChkInteger(data.getWins());
                validationChkInteger(data.getPoints());
                validationChkString(data.getPresentTier());
                validationChkString(data.getPresentDivision());
                validationChkString(data.getPresentHighestTier());
                validationChkString(data.getPresentHighestDivision());
                validationChkString(data.getPreviousTier());
                validationChkString(data.getPreviousDivision());
                validationChkString(data.getPreviousHighestTier());
                validationChkString(data.getPreviousHighestDivision());
            }

            return CommonResponseDto.setSuccess("Success", "Data 검사 완료");
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Database Insert Failed !");
        }
    }
}
