package riot.lcgs.riotlcgsbe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import riot.lcgs.riotlcgsbe.jpa.repository.LCG_Match_Sub_Repository;
import riot.lcgs.riotlcgsbe.jpa.repository.LCG_Player_Data_Repository;
import riot.lcgs.riotlcgsbe.jpa.repository.LCG_Player_Statistics_Repository;
import riot.lcgs.riotlcgsbe.web.dto.CommonResponseDto;
import riot.lcgs.riotlcgsbe.web.dto.object.*;

import java.util.*;

import static riot.lcgs.riotlcgsbe.util.CalculatorTool.*;

@RequiredArgsConstructor
@Service
public class MvpService {

    private final LCG_Match_Sub_Repository lcgMatchSubRepository;
    private final LCG_Player_Data_Repository lcgPlayerDataRepository;
    private final LCG_Player_Statistics_Repository lcgPlayerStatisticsRepository;

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

    @Transactional
    public CommonResponseDto<List<Map<String, Object>>> LCGPowerRankingSelection() {

        try {
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

            listDPMRank.sort(
    			Comparator.comparing(
    				(Map<String, Object> map) -> (Double)map.get("avg")
    			).reversed() //reversed 내림차순. reversed를 지우면 오름차순.
    		);

            // int score = 10;
    		// for(Map<String, Object> map1 : list) {
    		// 	for(Map<String, Object> map2 : personal) {
    		// 		if(map1.get("key").equals(map2.get("name"))) {
    		// 			Object test = (Object) ((int) map2.get("score") + score);
    		// 			map2.put("score", test);
    		// 		}
    		// 	}
    		// 	score -= 1;
    		// }
    
    		// for(Map<String, Object> map : personal) {
    		// 	System.out.println("name : " + map.get("name") + " score : " + map.get("score"));
    		// }

            List<Map<String, Object>> list = List.of((Map<String, Object>) listDPMRank);

            for(Map<String, Object> map : list) {
                System.out.println("puuid : " + map.get("puuid") + " cnt : " + map.get("count") + " avg : " + map.get("avg"));
            }

            return CommonResponseDto.setSuccess("Mvp 데이터 계산 완료!", list);
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Database Insert Failed !");
        }
    }
}
