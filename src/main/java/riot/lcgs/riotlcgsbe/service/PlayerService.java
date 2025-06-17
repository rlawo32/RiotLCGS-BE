package riot.lcgs.riotlcgsbe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import riot.lcgs.riotlcgsbe.jpa.domain.*;
import riot.lcgs.riotlcgsbe.jpa.repository.*;
import riot.lcgs.riotlcgsbe.web.dto.CommonResponseDto;
import riot.lcgs.riotlcgsbe.web.dto.object.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static riot.lcgs.riotlcgsbe.util.CalculatorTool.CalculatorJungleObjectScore;
import static riot.lcgs.riotlcgsbe.util.CalculatorTool.CalculatorMultiKillScore;
import static riot.lcgs.riotlcgsbe.util.ExtractionTool.*;

@RequiredArgsConstructor
@Service
public class PlayerService {


    private final MvpService mvpService;

    private final LCG_Match_Etc_Repository lcgMatchEtcRepository;
    private final LCG_Match_Sub_Repository lcgMatchSubRepository;
    private final LCG_Player_Data_Repository lcgPlayerDataRepository;
    private final LCG_Player_Statistics_Repository lcgPlayerStatisticsRepository;
    private final LCG_Player_Champion_Repository lcgPlayerChampionRepository;
    private final LCG_Player_Relative_Repository lcgPlayerRelativeRepository;
    private final LCG_Player_Ranking_Repository lcgPlayerRankingRepository;
    private final LCG_Player_Position_Repository lcgPlayerPositionRepository;

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
                            .lcgAiSummaryContent("")
                            .lcgAiSummaryVerify("N")
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

    @Transactional
    public CommonResponseDto<?> LCGPlayerRankingSave() {

        try {
            List<Map<String, Object>> listPlayer = lcgPlayerDataRepository.findByAllPlayer();
            List<Map<String, Object>> listDPMRank = lcgMatchSubRepository.findByAllAvgDpmRank();
            List<Map<String, Object>> listGPMRank = lcgMatchSubRepository.findByAllAvgGpmRank();
            List<Map<String, Object>> listDPGRank = lcgMatchSubRepository.findByAllAvgDpgRank();
            List<Map<String, Object>> listTierRank = lcgPlayerDataRepository.findByAllTierRank();
            List<Map<String, Object>> listWinningRate = lcgPlayerStatisticsRepository.findByAllWinningRate();
            List<Map<String, Object>> listMvpRank = lcgPlayerStatisticsRepository.findByAllMvpRank();
            List<Map<String, Object>> listAceRank = lcgPlayerStatisticsRepository.findByAllAceRank();
            List<Map<String, Object>> listKdaRank = lcgPlayerStatisticsRepository.findByAllKdaRank();
            List<Map<String, Object>> listVisionRank = lcgPlayerStatisticsRepository.findByAllVisionRank();
            List<Map<String, Object>> listGoldRank = lcgPlayerStatisticsRepository.findByAllGoldRank();
            List<Map<String, Object>> listDeathRank = lcgPlayerStatisticsRepository.findByAllDeathRank();
            List<Map<String, Object>> listMultiKillRank = lcgPlayerStatisticsRepository.findByAllMultiKillRank();
            List<Map<String, Object>> listDemolisherRank = lcgPlayerStatisticsRepository.findByAllDemolisherRank();

            calcPlayerRankingScore(listPlayer, listDPMRank, "D", listPlayer.size()*2, 2);
            calcPlayerRankingScore(listPlayer, listGPMRank, "D", listPlayer.size()*2, 2);
            calcPlayerRankingScore(listPlayer, listDPGRank, "D", listPlayer.size()*2, 2);
            calcPlayerRankingScore(listPlayer, listTierRank, "L", listPlayer.size(), 1);
            calcPlayerRankingScore(listPlayer, listWinningRate, "D", listPlayer.size()*2, 2);
            calcPlayerRankingScore(listPlayer, listMvpRank, "L", listPlayer.size()*3, 3);
            calcPlayerRankingScore(listPlayer, listAceRank, "L", listPlayer.size()*3, 3);
            calcPlayerRankingScore(listPlayer, listKdaRank, "D", listPlayer.size()*3, 3);
            calcPlayerRankingScore(listPlayer, listVisionRank, "L", listPlayer.size()*2, 2);
            calcPlayerRankingScore(listPlayer, listGoldRank, "L", listPlayer.size(), 1);
            calcPlayerRankingScore(listPlayer, listDeathRank, "R", listPlayer.size()*2, 2);
            calcPlayerRankingScore(listPlayer, listMultiKillRank, "L", listPlayer.size(), 1);
            calcPlayerRankingScore(listPlayer, listDemolisherRank, "L", listPlayer.size(), 1);

            listPlayer.sort(
                Comparator.comparing(
                    (Map<String, Object> map) -> (int) map.get("score")
                ).reversed() //reversed 내림차순. reversed 지우면 오름차순.
            );

            int rank = 0;
            Object compare = 0;

            for(Map<String, Object> map : listPlayer) {
                if(!map.get("score").equals(compare)) {
                    rank += 1;
                }
                map.put("rank", rank);
                compare = map.get("score");
            }

            List<LCG_Match_Etc> list = lcgMatchEtcRepository.findAll();
            LCG_Match_Etc lcgMatchEtc = lcgMatchEtcRepository.findById("LcgVer" + String.format("%04d", list.size()))
                    .orElseThrow(() -> new IllegalArgumentException("해당 게임 버전이 없습니다. LcgVer : " + list.size() +"v"));

            Long rankingCount = lcgMatchEtc.getLcgRankingCount();

            boolean duplicationCheck = lcgPlayerRankingRepository.existsLCG_Player_RankingByLcgRankingCount(rankingCount);

            if(!duplicationCheck) {
                for(Map<String, Object> map : listPlayer) {
                    lcgPlayerRankingRepository.save(LCG_Player_Ranking.builder()
                            .lcgRankingCount(rankingCount)
                            .lcgRankingRank(Long.parseLong(String.valueOf(map.get("rank"))))
                            .lcgRankingScore(Long.parseLong(String.valueOf(map.get("score"))))
                            .lcgSummonerPuuid((String) map.get("puuid"))
                            .lcgPlayerName((String) map.get("name"))
                            .lcgSummonerNickname((String) map.get("nickname"))
                            .build());
                }

                return CommonResponseDto.setSuccess("플레이어 Champion 전적 저장 완료!", "Success");
            } else {
                return CommonResponseDto.setFailed("중복 저장");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Failed");
        }
    }

    private void calcPlayerRankingScore(List<Map<String, Object>> listPlayer, List<Map<String, Object>> listGrade,
                                        String flag, int point, int decrease) {

        if(flag.equals("D")) {
            listGrade.sort(
                Comparator.comparing(
                    (Map<String, Object> map) -> (Double)map.get("grade")
                ).reversed()
            );
        } else if(flag.equals("L")) {
            listGrade.sort(
                Comparator.comparing(
                    (Map<String, Object> map) -> (Long) map.get("grade")
                ).reversed()
            );
        } else if(flag.equals("R")) {
            listGrade.sort(
                Comparator.comparing(
                    (Map<String, Object> map) -> (Long) map.get("grade")
                )
            );
        }

		Object compare = 0;

        for(Map<String, Object> map1 : listGrade) {
			if(!map1.get("grade").equals(compare)) {
				point -= decrease;
			} 
            for(Map<String, Object> map2 : listPlayer) {
                if(map1.get("puuid").equals(map2.get("puuid"))) {
                    Object score = ((int) map2.get("score") + point);
                    map2.put("score", score);
                }
            }
			compare = map1.get("grade");
        }
    }

    @Transactional
    public CommonResponseDto<?> LCGPlayerPositionSave(GameData gameData, List<TeamData> teamData) {

        try {
            List<ParticipantIdentities> list1 = gameData.getParticipantIdentities();
            List<Participants> list2 = gameData.getParticipants();

            int duration = gameData.getGameDuration();

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

                String puuid = playerData.getPuuid();
                String nickname = playerData.getGameName() + "#" + playerData.getTagLine();
                String line = "";
                boolean win = statsData.getWin();

                for(TeamData player : teamData) {
                    if(player.getPuuid().equals(puuid)) {
                        line = player.getLine();
                    }
                }

                boolean duplicationCheck = lcgPlayerPositionRepository.existsLCG_Player_PositionByLcgSummonerPuuid(puuid);

                if(duplicationCheck) {
                    LCG_Player_Position lcgPlayerPosition = lcgPlayerPositionRepository.findById(puuid)
                            .orElseThrow(() -> new IllegalArgumentException("해당 플레이어가 없습니다. Puuid. : " + puuid));

                    lcgPlayerPosition.playerPositionUpdate(nickname, duration, line, win);
                } else {
                    lcgPlayerPositionRepository.save(LCG_Player_Position.builder()
                            .lcgSummonerPuuid(puuid)
                            .lcgSummonerNickname(nickname)
                            .lcgPlayCount(1L)
                            .lcgTotalPlaytime((long) duration)
                            .lcgPositionTopCount(line.equals("TOP") ? 1L : 0L)
                            .lcgPositionJugCount(line.equals("JUG") ? 1L : 0L)
                            .lcgPositionMidCount(line.equals("MID") ? 1L : 0L)
                            .lcgPositionAdcCount(line.equals("ADC") ? 1L : 0L)
                            .lcgPositionSupCount(line.equals("SUP") ? 1L : 0L)
                            .lcgPositionTopWin(line.equals("TOP") && win ? 1L : 0L)
                            .lcgPositionJugWin(line.equals("JUG") && win ? 1L : 0L)
                            .lcgPositionMidWin(line.equals("MID") && win ? 1L : 0L)
                            .lcgPositionAdcWin(line.equals("ADC") && win ? 1L : 0L)
                            .lcgPositionSupWin(line.equals("SUP") && win ? 1L : 0L)
                            .lcgPositionTopPlaytime(line.equals("TOP") ? (long) duration : 0L)
                            .lcgPositionJugPlaytime(line.equals("JUG") ? (long) duration : 0L)
                            .lcgPositionMidPlaytime(line.equals("MID") ? (long) duration : 0L)
                            .lcgPositionAdcPlaytime(line.equals("ADC") ? (long) duration : 0L)
                            .lcgPositionSupPlaytime(line.equals("SUP") ? (long) duration : 0L)
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
