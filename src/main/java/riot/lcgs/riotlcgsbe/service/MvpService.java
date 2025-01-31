package riot.lcgs.riotlcgsbe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import riot.lcgs.riotlcgsbe.web.dto.CommonResponseDto;
import riot.lcgs.riotlcgsbe.web.dto.object.*;

import java.util.Arrays;
import java.util.List;

import static riot.lcgs.riotlcgsbe.util.CalculatorTool.*;

@RequiredArgsConstructor
@Service
public class MvpService {

    @Transactional
    public CommonResponseDto<?> LCGMvpSelection(GameData gameData) {

        try {
            List<ParticipantIdentities> list1 = gameData.getParticipantIdentities();
            List<Participants> list2 = gameData.getParticipants();
            List<Teams> list3 = gameData.getTeams();

            Metrics[] metrics = new Metrics[10];
            Teams teams1 = new Teams();
            Teams teams2 = new Teams();
            int team1TotalKill = 0;
            int team2TotalKill = 0;

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

                metrics[i] = new Metrics(playerData.getPuuid(), statsData.getKills(),
                        statsData.getDeaths(), statsData.getAssists(), statsData.getGoldEarned(),
                        (statsData.getTotalMinionsKilled() + statsData.getNeutralMinionsKilled()),
                        statsData.getTotalDamageDealtToChampions(), statsData.getVisionScore(), statsData.getTimeCCingOthers(),
                        CalculatorMultiKillScore(statsData).getData(), CalculatorDemolisherScore(statsData).getData(),
                        CalculatorJungleObjectScore(participants.getTeamId() == 100 ? teams1 : teams2).getData(),
                        (participants.getTeamId() == 100 ? team1TotalKill : team2TotalKill), 0, statsData.isWin(),
                        statsData.isFirstBloodKill(), statsData.isFirstTowerKill(), statsData.isFirstInhibitorKill(),
                        (participants.getTeamId() == 100 ? teams1.isFirstDargon() : teams2.isFirstDargon()),
                        (participants.getTeamId() == 100 ? teams1.isFirstBaron() : teams2.isFirstBaron()));
            }

//            List<Metrics> list = Arrays.asList(metrics);
            CalculatorMvpScore(metrics, "");
            CalculatorMvpScore(metrics, "D");
            Arrays.sort(metrics, (m1, m2) -> Integer.compare((int) m2.getObjectScore(), (int) m1.getObjectScore()));
            CalculatorMvpScore(metrics, "E");

            Arrays.sort(metrics, (m1, m2) -> Integer.compare(m2.getKill(),m1.getKill()));
            CalculatorMvpScore(metrics, "A");

            Arrays.sort(metrics, (m1, m2) -> Integer.compare(m1.getDeath(),m2.getDeath()));
            CalculatorMvpScore(metrics, "A");

            Arrays.sort(metrics, (m1, m2) -> Integer.compare(m2.getAssist(),m1.getAssist()));
            CalculatorMvpScore(metrics, "B");

            Arrays.sort(metrics, (m1, m2) -> Integer.compare(m2.getGold(),m1.getGold()));
            CalculatorMvpScore(metrics, "A");

            Arrays.sort(metrics, (m1, m2) -> Integer.compare(m2.getCs(),m1.getCs()));
            CalculatorMvpScore(metrics, "A");

            Arrays.sort(metrics, (m1, m2) -> Integer.compare(m2.getDamage(),m1.getDamage()));
            CalculatorMvpScore(metrics, "A");

            Arrays.sort(metrics, (m1, m2) -> Integer.compare(m2.getVisionScore(),m1.getVisionScore()));
            CalculatorMvpScore(metrics, "C");

            Arrays.sort(metrics, (m1, m2) -> Integer.compare(m2.getCrowdScore(),m1.getCrowdScore()));
            CalculatorMvpScore(metrics, "C");

            Arrays.sort(metrics, (m1, m2) -> Integer.compare(m2.getTotalScore(),m1.getTotalScore()));

            for(Metrics m : metrics) {
                System.out.println(m.toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Database Insert Failed !");
        }

        return CommonResponseDto.setSuccess("Success", "");
    }
}
