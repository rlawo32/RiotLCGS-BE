package riot.lcgs.riotlcgsbe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import riot.lcgs.riotlcgsbe.jpa.domain.*;
import riot.lcgs.riotlcgsbe.jpa.repository.*;
import riot.lcgs.riotlcgsbe.web.dto.CommonResponseDto;
import riot.lcgs.riotlcgsbe.web.dto.PlayerDataRequestDto;
import riot.lcgs.riotlcgsbe.web.dto.object.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static riot.lcgs.riotlcgsbe.util.CalculatorTool.CalculatorJungleObjectScore;
import static riot.lcgs.riotlcgsbe.util.CalculatorTool.CalculatorMultiKillScore;
import static riot.lcgs.riotlcgsbe.util.ExtractionTool.*;
import static riot.lcgs.riotlcgsbe.util.ExtractionTool.ExtractionPerk;
import static riot.lcgs.riotlcgsbe.util.ValidationTool.*;

@RequiredArgsConstructor
@Service
public class PlayerService {


    private final MvpService mvpService;

    private final LCG_Match_Etc_Repository lcgMatchEtcRepository;
    private final LCG_Player_Data_Repository lcgPlayerDataRepository;
    private final LCG_Player_Statistics_Repository lcgPlayerStatisticsRepository;
    private final LCG_Player_Champion_Repository lcgPlayerChampionRepository;
    private final LCG_Player_Relative_Repository lcgPlayerRelativeRepository;

    @Transactional
    public CommonResponseDto<?> LCGPlayerDataSave(GameData gameData, List<RankData> list1) {

        try {
            List<ParticipantIdentities> list2 = gameData.getParticipantIdentities();

            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String now = localDateTime.format(dtf);

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

                    assert rankData != null;
                    lcgPlayerData.playerDataUpdate(playerData, rankData);
                } else {
                    assert rankData != null;
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

                List<LCG_Match_Etc> list = lcgMatchEtcRepository.findAll();
                LCG_Match_Etc lcgMatchEtc = lcgMatchEtcRepository.findById("LcgVer" + String.format("%04d", list.size()))
                        .orElseThrow(() -> new IllegalArgumentException("해당 게임 버전이 없습니다. LcgVer : " + list.size() +"v"));

                lcgMatchEtc.playerRecentUpdate(now);
            }
            return CommonResponseDto.setSuccess("플레이어 저장 완료!", "Success");
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

            return CommonResponseDto.setSuccess("플레이어 정보 저장 완료!", "Success");
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Database Insert Failed !");
        }
    }

    @Transactional
    public CommonResponseDto<?> LCGPlayerChampionSave(GameData gameData) {

        try {
            List<ParticipantIdentities> list1 = gameData.getParticipantIdentities();
            List<Participants> list2 = gameData.getParticipants();

            for(int i=0; i<list1.size(); i++) {
                ParticipantIdentities participantIdentities = list1.get(i);
                Participants participants = list2.get(i);
                Player playerData = participantIdentities.getPlayer();
                Stats statsData = participants.getStats();

                String puuid = playerData.getPuuid();
                int championId = participants.getChampionId();
                String championName = ExtractionName(championId).getData();

                boolean duplicationCheck = lcgPlayerChampionRepository.existsLCG_Player_ChampionByLcgPuuidAndLcgChampionId(puuid, championId);

                if(duplicationCheck) {
                    LCG_Player_Champion lcgPlayerChampion = lcgPlayerChampionRepository.findByLcgPuuidAndLcgChampionId(puuid, championId)
                            .orElseThrow(() -> new IllegalArgumentException("해당 플레이어가 없습니다. Puuid. : " + puuid));

                    lcgPlayerChampion.playerChampionUpdate(statsData);
                } else {
                    lcgPlayerChampionRepository.save(LCG_Player_Champion.builder()
                            .lcgPuuid(puuid)
                            .lcgChampionId(championId)
                            .lcgChampionName(championName)
                            .lcgKillCount((long) statsData.getKills())
                            .lcgDeathCount((long) statsData.getDeaths())
                            .lcgAssistCount((long) statsData.getAssists())
                            .lcgPlayCount(1L)
                            .lcgWinCount(statsData.getWin() ? 1L : 0L)
                            .lcgFailCount(statsData.getWin() ? 0L : 1L)
                            .build());
                }
            }

            return CommonResponseDto.setSuccess("플레이어 Champion 전적 저장 완료!", "Success");
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Failed");
        }
    }

    @Transactional
    public CommonResponseDto<?> LCGPlayerRelativeSave(GameData gameData, List<TeamData> teamData) {

        try {
            List<ParticipantIdentities> list1 = gameData.getParticipantIdentities();
            List<Participants> list2 = gameData.getParticipants();
            List<Teams> list3 = gameData.getTeams();

            for(TeamData player : teamData) {
                String name = player.getName();
                LCG_Player_Data lcgPlayerData = lcgPlayerDataRepository.findByLcgPlayer(name)
                        .orElseThrow(() -> new IllegalArgumentException("해당 플레이어가 없습니다. Name : " + name));

                player.setPuuid(lcgPlayerData.getLcgSummonerPuuid());
            }

            for(int i=0; i<list1.size(); i++) {
                ParticipantIdentities participantIdentities = list1.get(i);
                Participants participants = list2.get(i);
                Player playerData = participantIdentities.getPlayer();
                Stats statsData = participants.getStats();

                String personPuuid = playerData.getPuuid();
                String opponentPuuid = "";
                String line = "";

                for(TeamData player : teamData) {
                    if(player.getPuuid().equals(personPuuid)) {
                        line = player.getLine();
                    }
                }

                for(TeamData player : teamData) {
                    if(!player.getPuuid().equals(personPuuid) && player.getLine().equals(line)) {
                        opponentPuuid = player.getPuuid();
                    }
                }

                boolean duplicationCheck = lcgPlayerRelativeRepository.
                        existsLCG_Player_RelativeByLcgPersonPuuidAndLcgMatchLineAndLcgOpponentPuuid(personPuuid, line, opponentPuuid);

                if(duplicationCheck) {
                    LCG_Player_Relative lcgPlayerRelative = lcgPlayerRelativeRepository.
                            findByLcgPersonPuuidAndLcgMatchLineAndLcgOpponentPuuid(personPuuid, line, opponentPuuid)
                            .orElseThrow(() -> new IllegalArgumentException("해당 플레이어가 없습니다. personPuuid : " + personPuuid));

                    lcgPlayerRelative.playerRelativeUpdate(statsData);
                } else {
                    lcgPlayerRelativeRepository.save(LCG_Player_Relative.builder()
                            .lcgPersonPuuid(personPuuid)
                            .lcgMatchLine(line)
                            .lcgOpponentPuuid(opponentPuuid)
                            .lcgPlayCount(1L)
                            .lcgWinCount(statsData.getWin() ? 1L : 0L)
                            .lcgFailCount(statsData.getWin() ? 0L : 1L)
                            .build());
                }
            }

            return CommonResponseDto.setSuccess("플레이어 Relative 전적 저장 완료!", "Success");
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Failed");
        }
    }
}
