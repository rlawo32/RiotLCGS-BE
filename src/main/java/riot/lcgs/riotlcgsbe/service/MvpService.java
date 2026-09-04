package riot.lcgs.riotlcgsbe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Info_Maximum;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Match_Etc;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Player_Position;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Player_Ranking;
import riot.lcgs.riotlcgsbe.jpa.repository.*;
import riot.lcgs.riotlcgsbe.web.dto.CommonResponseDto;
import riot.lcgs.riotlcgsbe.web.dto.object.*;

import java.util.*;

import static riot.lcgs.riotlcgsbe.util.CalculatorTool.*;
import static riot.lcgs.riotlcgsbe.util.DateTimeTool.dateTimeCurrent;

@RequiredArgsConstructor
@Service
public class MvpService {

    private final LCG_Match_Sub_Repository lcgMatchSubRepository;
    private final LCG_Player_Data_Repository lcgPlayerDataRepository;
    private final LCG_Player_Statistics_Repository lcgPlayerStatisticsRepository;
    private final LCG_Player_Ranking_Repository lcgPlayerRankingRepository;
    private final LCG_Player_Position_Repository lcgPlayerPositionRepository;
    private final LCG_Info_Maximum_Repository lcgInfoMaximumRepository;

    @Transactional
    public CommonResponseDto<List<Metrics>> LCGMvpSelection(GameData gameData) {

        try {
            List<ParticipantIdentities> list1 = gameData.getParticipantIdentities();
            List<Participants> list2 = gameData.getParticipants();
            List<Teams> list3 = gameData.getTeams();

            Metrics[] metrics = new Metrics[10];
            Teams teams1 = new Teams();
            Teams teams2 = new Teams();
            int team1TotalKill = 0;
            int team2TotalKill = 0;
            int duration = gameData.getGameDuration();

            for(Teams teams : list3) {
                if(teams.getTeamId() == 100) {
                    teams1 = teams;
                } else {
                    teams2 = teams;
                }
            }

            for(Participants participants : list2) {
                Stats statsData = participants.getStats();

                if (participants.getTeamId() == 100) {
                    team1TotalKill += statsData.getKills();
                } else {
                    team2TotalKill += statsData.getKills();
                }
            }

            for(int i=0; i<list1.size(); i++) {
                ParticipantIdentities participantIdentities = list1.get(i);
                Participants participants = list2.get(i);
                Player playerData = participantIdentities.getPlayer();
                Stats statsData = participants.getStats();

                double kda = 0;
                if(statsData.getDeaths() == 0 && (statsData.getKills() > 0 || statsData.getAssists() > 0)) {
                    kda = statsData.getKills() + statsData.getAssists();
                } else {
                    kda = Math.round(((double) (statsData.getKills() + statsData.getAssists()) / statsData.getDeaths()) * 100) / 100.0;
                }

                int engagementRate = Math.round((float) (statsData.getKills() + statsData.getAssists()) / (participants.getTeamId() == 100 ? team1TotalKill : team2TotalKill) * 100);

                metrics[i] = new Metrics(playerData.getPuuid(), playerData.getSummonerName(), 0, participants.getTeamId(),
                        statsData.getKills(), statsData.getDeaths(), statsData.getAssists(), kda, engagementRate, statsData.getGoldEarned(),
                        statsData.getTotalDamageDealtToChampions(), (statsData.getTotalMinionsKilled() + statsData.getNeutralMinionsKilled()),
                        CalculatorCharacteristic(duration, statsData).getData().get("DPM"),
                        CalculatorCharacteristic(duration, statsData).getData().get("GPM"),
                        CalculatorCharacteristic(duration, statsData).getData().get("DPG"),
                        statsData.getVisionScore(), statsData.getTimeCCingOthers(),
                        CalculatorMultiKillScore(statsData).getData(), CalculatorDemolisherScore(statsData).getData(),
                        CalculatorJungleObjectScore(participants.getTeamId() == 100 ? teams1 : teams2).getData(), statsData.getWin(),
                        statsData.getFirstBloodKill(), statsData.getFirstTowerKill(), statsData.getFirstInhibitorKill(),
                        (participants.getTeamId() == 100 ? teams1.getFirstDargon() : teams2.getFirstDargon()),
                        (participants.getTeamId() == 100 ? teams1.getFirstBaron() : teams2.getFirstBaron()));
            }

            // MultiKillScore, DemolisherScore
            CalculatorMvpScore(metrics, "");

            // FirstKill, FirstTower, FirstInhibitor, FirstDragon, FirstBaron, Win
            CalculatorMvpScore(metrics, "D");

            // JungleObjectScore
            Arrays.sort(metrics, (m1, m2) -> Long.compare(m2.getObjectScore(),m1.getObjectScore()));
            CalculatorMvpScore(metrics, "E");

            // Kill, Death, Assist, KDA
            Arrays.sort(metrics, (m1, m2) -> Integer.compare(m2.getKill(),m1.getKill()));
            CalculatorMvpScore(metrics, "A");
            Arrays.sort(metrics, (m1, m2) -> Integer.compare(m1.getDeath(),m2.getDeath()));
            CalculatorMvpScore(metrics, "B");
            Arrays.sort(metrics, (m1, m2) -> Integer.compare(m2.getAssist(),m1.getAssist()));
            CalculatorMvpScore(metrics, "B");
            Arrays.sort(metrics, (m1, m2) -> Double.compare(m2.getKda(),m1.getKda()));
            CalculatorMvpScore(metrics, "C");

            // EngagementRate
            Arrays.sort(metrics, (m1, m2) -> Integer.compare(m2.getEngagementRate(),m1.getEngagementRate()));
            CalculatorMvpScore(metrics, "C");

            // Gold, Damage, CS
            Arrays.sort(metrics, (m1, m2) -> Integer.compare(m2.getGold(),m1.getGold()));
            CalculatorMvpScore(metrics, "A");
            Arrays.sort(metrics, (m1, m2) -> Integer.compare(m2.getDamage(),m1.getDamage()));
            CalculatorMvpScore(metrics, "A");
            Arrays.sort(metrics, (m1, m2) -> Integer.compare(m2.getCs(),m1.getCs()));
            CalculatorMvpScore(metrics, "A");

            // DPM, GPM
            Arrays.sort(metrics, (m1, m2) -> Double.compare(m2.getDpm(),m1.getDpm()));
            CalculatorMvpScore(metrics, "C");
            Arrays.sort(metrics, (m1, m2) -> Double.compare(m2.getGpm(),m1.getGpm()));
            CalculatorMvpScore(metrics, "B");

            // VisionScore
            Arrays.sort(metrics, (m1, m2) -> Integer.compare(m2.getVisionScore(),m1.getVisionScore()));
            CalculatorMvpScore(metrics, "C");

            // CrowdScore
            Arrays.sort(metrics, (m1, m2) -> Integer.compare(m2.getCrowdScore(),m1.getCrowdScore()));
            CalculatorMvpScore(metrics, "C");

            // TotalScore
            Arrays.sort(metrics, (m1, m2) -> Integer.compare(m2.getTotalScore(),m1.getTotalScore()));

//            for(Metrics m : metrics) {
//                System.out.println(m.toString());
//            }

            List<Metrics> list = Arrays.asList(metrics);

            return CommonResponseDto.setSuccess("Mvp 데이터 계산 완료!", list);
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Database Insert Failed !");
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
                            (Map<String, Object> map) -> (Double) map.get("grade")
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
    public CommonResponseDto<?> LCGPlayerRankingSave() {

        try {
            List<Map<String, Object>> listPlayer = lcgPlayerStatisticsRepository.findByAllPlayer();
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
            calcPlayerRankingScore(listPlayer, listDPGRank, "D", listPlayer.size(), 1);
            calcPlayerRankingScore(listPlayer, listTierRank, "L", listPlayer.size()*2, 2);
            calcPlayerRankingScore(listPlayer, listWinningRate, "D", listPlayer.size()*2, 2);
            calcPlayerRankingScore(listPlayer, listMvpRank, "L", listPlayer.size()*3, 3);
            calcPlayerRankingScore(listPlayer, listAceRank, "L", listPlayer.size()*2, 2);
            calcPlayerRankingScore(listPlayer, listKdaRank, "D", listPlayer.size()*2, 2);
            calcPlayerRankingScore(listPlayer, listVisionRank, "D", listPlayer.size()*2, 2);
            calcPlayerRankingScore(listPlayer, listGoldRank, "D", listPlayer.size(), 1);
            calcPlayerRankingScore(listPlayer, listDeathRank, "R", listPlayer.size()*2, 2);
            calcPlayerRankingScore(listPlayer, listMultiKillRank, "D", listPlayer.size(), 1);
            calcPlayerRankingScore(listPlayer, listDemolisherRank, "L", listPlayer.size(), 1);

            // 판수 어드밴티지
            for (Map<String, Object> map : listPlayer) {
                int play = ((Number) map.get("play")).intValue();
                int score = ((Number) map.get("score")).intValue();

//                if (play < 30) { score -= 100; }
//                else if (play < 70) { score -= 60; }
//                else if (play < 100) { score -= 20; }
                if (play < 10) { score -= 100; }
                else if (play < 30) { score -= 60; }

                map.put("score", score);
            }

//            listPlayer.removeIf(map -> ((Long) map.get("play")) < 10);
            listPlayer.sort(Comparator.comparing((Map<String, Object> map) -> (int) map.get("score")).reversed()); // reversed 내림차순. reversed 지우면 오름차순.

            int rank = 1;
            int gradeRange = 0;
            for(Map<String, Object> map : listPlayer) {
                String puuid = (String) map.get("puuid");
                LCG_Player_Ranking lcgPlayerRanking = lcgPlayerRankingRepository.findById(puuid).orElse(null);

                if (lcgPlayerRanking == null) { continue; }

                if("Y".equals(lcgPlayerRanking.getLcgRankingActive())) {
                    map.put("rank", rank++);
                    gradeRange++;
                }
            }

            int cnt = 1;
            for(Map<String, Object> map : listPlayer) {
                String puuid = (String) map.get("puuid");
                String nickName = (String) map.get("nickname");
                int currentScore = Integer.parseInt(String.valueOf(map.get("score")));
                int currentRank = Integer.parseInt(String.valueOf(map.get("rank")));
                int gradeStandard = Math.round((float) gradeRange / 5);
                int grade = 5;
                for(int i=gradeStandard; i<=listPlayer.size(); i+=gradeStandard) {
                    if(currentRank <= i) {
                        break;
                    }
                    grade--;
                }

                boolean duplicationCheck = lcgPlayerRankingRepository.existsLCG_Player_RankingByLcgSummonerPuuid(puuid);

                if(!duplicationCheck) {
                    lcgPlayerRankingRepository.save(LCG_Player_Ranking.builder()
                            .lcgSummonerPuuid(puuid)
                            .lcgPlayerName("")
                            .lcgSummonerNickname(nickName)
                            .lcgRankingCurrentRank(currentRank)
                            .lcgRankingPreviousRank(0)
                            .lcgRankingGrade(grade)
                            .lcgRankingCurrentScore(currentScore)
                            .lcgRankingPreviousScore(0)
                            .lcgRankingCount(1)
                            .lcgRankingActive("Y")
                            .build());

                    System.out.println("플레이어 Ranking 신규 저장 완료! (" + cnt + ") : " + nickName);
                } else {
                    LCG_Player_Ranking lcgPlayerRanking = lcgPlayerRankingRepository.findById(puuid)
                            .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. puuid : " + puuid));

                    int previousRank = lcgPlayerRanking.getLcgRankingCurrentRank();
                    int previousScore = lcgPlayerRanking.getLcgRankingCurrentScore();
                    lcgPlayerRanking.playerRankingUpdate(currentRank, previousRank, grade, currentScore, previousScore);

                    System.out.println("플레이어 Ranking 업데이트 완료! (" + cnt + ") : " + nickName);
                }
                cnt++;
            }
            return CommonResponseDto.setSuccess("플레이어 Ranking 업데이트 완료!", "Success");
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Failed");
        }
    }

    @Transactional
    public void LCGInfoMaximumSave() {

        try {
            String now = dateTimeCurrent().getData();

            Map<String, Integer> listMax = lcgPlayerStatisticsRepository.findByAllMaxStatistics();
            List<Map<String, Object>> listLane = lcgPlayerPositionRepository.findAllLaneRate();

            List<Map<String, Object>> listLaneTop = new ArrayList<>(listLane);
            List<Map<String, Object>> listLaneJug = new ArrayList<>(listLane);
            List<Map<String, Object>> listLaneMid = new ArrayList<>(listLane);
            List<Map<String, Object>> listLaneAdc = new ArrayList<>(listLane);
            List<Map<String, Object>> listLaneSup = new ArrayList<>(listLane);

            listLaneTop.removeIf(map -> ((Long) map.get("play_top")) <= 20);
            listLaneTop.sort(Comparator.comparing((Map<String, Object> map) -> (Double)map.get("rate_top")).reversed());
            listLaneJug.removeIf(map -> ((Long) map.get("play_jug")) <= 20);
            listLaneJug.sort(Comparator.comparing((Map<String, Object> map) -> (Double)map.get("rate_jug")).reversed());
            listLaneMid.removeIf(map -> ((Long) map.get("play_mid")) <= 20);
            listLaneMid.sort(Comparator.comparing((Map<String, Object> map) -> (Double)map.get("rate_mid")).reversed());
            listLaneAdc.removeIf(map -> ((Long) map.get("play_adc")) <= 20);
            listLaneAdc.sort(Comparator.comparing((Map<String, Object> map) -> (Double)map.get("rate_adc")).reversed());
            listLaneSup.removeIf(map -> ((Long) map.get("play_sup")) <= 20);
            listLaneSup.sort(Comparator.comparing((Map<String, Object> map) -> (Double)map.get("rate_sup")).reversed());

            lcgInfoMaximumRepository.save(LCG_Info_Maximum.builder()
                    .lcgMaxKill(listMax.get("kill"))
                    .lcgMaxDeath(listMax.get("death"))
                    .lcgMaxAssist(listMax.get("assist"))
                    .lcgMaxMvp(listMax.get("mvp"))
                    .lcgMaxAce(listMax.get("ace"))
                    .lcgMaxGold(listMax.get("gold"))
                    .lcgMaxCs(listMax.get("cs"))
                    .lcgMaxDemolisher(listMax.get("demolisher"))
                    .lcgMaxPinkward(listMax.get("pinkward"))
                    .lcgMaxVision(listMax.get("vision"))
                    .lcgMaxMultikill(listMax.get("multikill"))
                    .lcgMaxObject(listMax.get("object"))
                    .lcgMaxTop((String) listLaneTop.get(0).get("puuid"))
                    .lcgMaxJug((String) listLaneJug.get(0).get("puuid"))
                    .lcgMaxMid((String) listLaneMid.get(0).get("puuid"))
                    .lcgMaxAdc((String) listLaneAdc.get(0).get("puuid"))
                    .lcgMaxSup((String) listLaneSup.get(0).get("puuid"))
                    .lcgUpdateDate(now)
                    .build());

            System.out.println("InfoMaximum 업데이트 완료!");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Database Insert Failed !");
        }
    }
}
