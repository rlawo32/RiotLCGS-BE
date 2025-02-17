package riot.lcgs.riotlcgsbe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Player_Data;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Player_Statistics;
import riot.lcgs.riotlcgsbe.jpa.repository.LCG_Player_Data_Repository;
import riot.lcgs.riotlcgsbe.jpa.repository.LCG_Player_Statistics_Repository;
import riot.lcgs.riotlcgsbe.web.dto.CommonResponseDto;
import riot.lcgs.riotlcgsbe.web.dto.PlayerDataRequestDto;
import riot.lcgs.riotlcgsbe.web.dto.object.*;

import java.util.List;

import static riot.lcgs.riotlcgsbe.util.CalculatorTool.CalculatorJungleObjectScore;
import static riot.lcgs.riotlcgsbe.util.CalculatorTool.CalculatorMultiKillScore;
import static riot.lcgs.riotlcgsbe.util.ValidationTool.*;

@RequiredArgsConstructor
@Service
public class PlayerService {


    private final MvpService mvpService;

    private final LCG_Player_Data_Repository lcgPlayerDataRepository;
    private final LCG_Player_Statistics_Repository lcgPlayerStatisticsRepository;

    @Transactional
    public CommonResponseDto<?> LCGPlayerDataSave(GameData gameData, List<RankData> list1) {

        try {
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
            return CommonResponseDto.setSuccess("Success", "플레이어 저장 완료!");
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Database Insert Failed !");
        }
    }

    @Transactional
    public CommonResponseDto<?> LCGPlayerStatisticsSave(GameData gameData) {

        try {
            List<ParticipantIdentities> list1 = gameData.getParticipantIdentities();
            List<Participants> list2 = gameData.getParticipants();
            List<Teams> list3 = gameData.getTeams();
            List<Metrics> list4 = mvpService.LCGMvpSelection(gameData).getData();

            int failTeam = 0;
            String mvpPuuid = list4.get(0).getPuuid();
            String acePuuid = "";

            for(Teams teams : list3) {
                if(teams.getWin().equals("Fail")) {
                    failTeam = teams.getTeamId();
                }
            }

            for(Metrics metrics : list4) {
                if(failTeam == metrics.getTeam()) {
                    acePuuid = metrics.getPuuid();
                    break;
                }
            }

            for(int i=0; i<list1.size(); i++) {
                ParticipantIdentities participantIdentities = list1.get(i);
                Participants participants = list2.get(i);
                Stats statsData = participants.getStats();
                Teams teams = new Teams();

                for(Teams team : list3) {
                    teams = team;
                    if (participants.getTeamId() == teams.getTeamId()) {
                        break;
                    }
                }

                // 사용자 정보
                String puuid = participantIdentities.getPlayer().getPuuid();
                String nickname = participantIdentities.getPlayer().getGameName() + "#" + participantIdentities.getPlayer().getTagLine();

                boolean existsCheck = lcgPlayerStatisticsRepository.existsLCG_Player_StatisticsByLcgSummonerPuuid(puuid);

                if(existsCheck) {
                    LCG_Player_Statistics lcgPlayerStatistics = lcgPlayerStatisticsRepository.findById(puuid)
                            .orElseThrow(() -> new IllegalArgumentException("해당 플레이어가 없습니다. Puuid. : " + puuid));

                    lcgPlayerStatistics.playerDataCounting(nickname, statsData, teams, puuid.equals(mvpPuuid) ? 1L : 0L,
                            puuid.equals(acePuuid) ? 1L : 0L, CalculatorMultiKillScore(statsData).getData(), CalculatorJungleObjectScore(teams).getData());
                } else {
                    lcgPlayerStatisticsRepository.save(LCG_Player_Statistics.builder()
                            .lcgSummonerPuuid(puuid)
                            .lcgPlayer("")
                            .lcgNickname(nickname)
                            .lcgCountVictory(teams.getWin().equals("Win") ? 1L : 0L)
                            .lcgCountDefeat(teams.getWin().equals("Fail") ? 1L : 0L)
                            .lcgCountMvp(puuid.equals(mvpPuuid) ? 1L : 0L)
                            .lcgCountAce(puuid.equals(acePuuid) ? 1L : 0L)
                            .lcgCountKill((long)statsData.getKills())
                            .lcgCountDeath((long)statsData.getDeaths())
                            .lcgCountAssist((long)statsData.getAssists())
                            .lcgCountTower((long)statsData.getTurretKills())
                            .lcgCountInhibitor((long)statsData.getInhibitorKills())
                            .lcgCountTowerDamage((long)statsData.getDamageDealtToTurrets())
                            .lcgCountDamage((long)statsData.getTotalDamageDealtToChampions())
                            .lcgCountTaken((long)statsData.getTotalDamageTaken())
                            .lcgCountGold((long)statsData.getGoldEarned())
                            .lcgCountCrowdTime((long)statsData.getTimeCCingOthers())
                            .lcgCountMinion((long)statsData.getTotalMinionsKilled())
                            .lcgCountJungle((long)statsData.getNeutralMinionsKilled())
                            .lcgCountWardKill((long)statsData.getWardsKilled())
                            .lcgCountWardPlaced((long)statsData.getWardsPlaced())
                            .lcgCountVisionWard((long)statsData.getVisionWardsBoughtInGame())
                            .lcgCountVisionScore((long)statsData.getVisionScore())
                            .lcgCountDoubleKill((long)statsData.getDoubleKills())
                            .lcgCountTripleKill((long)statsData.getTripleKills())
                            .lcgCountQuadraKill((long)statsData.getQuadraKills())
                            .lcgCountPentaKill((long)statsData.getPentaKills())
                            .lcgMultiKillScore(CalculatorMultiKillScore(statsData).getData())
                            .lcgCountDragon((long)teams.getDragonKills())
                            .lcgCountBaron((long)teams.getBaronKills())
                            .lcgCountHorde((long)teams.getHordeKills())
                            .lcgCountHerald((long)teams.getRiftHeraldKills())
                            .lcgCountAtakhan(0L)
                            .lcgJungleObjectScore(CalculatorJungleObjectScore(teams).getData()).build());
                }
            }

            return CommonResponseDto.setSuccess("Success", "");
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Database Insert Failed !");
        }
    }

    @Transactional
    public CommonResponseDto<?> ValidationCheckRankData(List<RankData> rankData) {

        try {
            for(RankData data : rankData) {

                // RankData Validation Check
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
            return CommonResponseDto.setFailed("Failed");
        }
    }
}
