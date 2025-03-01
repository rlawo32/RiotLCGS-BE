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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static riot.lcgs.riotlcgsbe.util.CalculatorTool.*;
import static riot.lcgs.riotlcgsbe.util.ExtractionTool.*;

@RequiredArgsConstructor
@Service
public class MatchService {

    private final MvpService mvpService;

    private final LCG_Match_Etc_Repository lcgMatchEtcRepository;
    private final LCG_Match_Info_Repository lcgMatchInfoRepository;
    private final LCG_Match_Main_Repository lcgMatchMainRepository;
    private final LCG_Match_Sub_Repository lcgMatchSubRepository;
    private final LCG_Match_Team_Repository lcgMatchTeamRepository;
    private final LCG_Match_Log_Repository lcgMatchLogRepository;
    private final LCG_Player_Data_Repository lcgPlayerDataRepository;

    @Transactional
    public CommonResponseDto<?> LCGTeamLogSave(Long gameId, GameData gameData, Map<String, String> version) {

        try {
            String[] extractionStep1 = gameData.getGameCreationDate().split("T");
            String[] extractionStep2 = extractionStep1[1].split("\\.");
            String extractionGameDate = extractionStep1[0] + "/" + extractionStep2[0];

            List<ParticipantIdentities> list1 = gameData.getParticipantIdentities();
            List<LCG_Player_Data> list2 = lcgPlayerDataRepository.findAll();
            List<Teams> list3 = gameData.getTeams();
            String[] arr = new String[10];
            int gameWin = 0;

            for(int i=0; i<list1.size(); i++) {
            	ParticipantIdentities participantIdentities = list1.get(i);
                Player playerData = participantIdentities.getPlayer();

                for(LCG_Player_Data lcgPlayerData : list2) {
                    if(lcgPlayerData.getLcgSummonerPuuid().equals(playerData.getPuuid())) {
                        arr[i] = lcgPlayerData.getLcgPlayer();
                    }
                }
            }

            for(Teams teams : list3) {
                if(teams.getWin().equals("Win")) {
                    gameWin = teams.getTeamId();
                }
            }

            lcgMatchLogRepository.save(LCG_Match_Log.builder()
                    .lcgGameId(gameId)
                    .lcgGameWin(gameWin)
                    .lcgGameDate(extractionGameDate)
                    .lcgGameVer(version.get("ver"))
                    .lcgGameDuration(gameData.getGameDuration())
                    .lcgGameDate(extractionGameDate)
                    .teamAName1(arr[0]).teamAName2(arr[1])
                    .teamAName3(arr[2]).teamAName4(arr[3])
                    .teamAName5(arr[4]).teamBName1(arr[5])
                    .teamBName2(arr[6]).teamBName3(arr[7])
                    .teamBName4(arr[8]).teamBName5(arr[9]).build());

            return CommonResponseDto.setSuccess("MatchLog Data 저장 완료!", "Success");
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Database Insert Failed !");
        }
    }

    @Transactional
    public CommonResponseDto<?> LCGMatchInfoSave(Long gameId, GameData gameData, Map<String, String> version) {

        try {
            String[] extractionStep1 = gameData.getGameCreationDate().split("T");
            String[] extractionStep2 = extractionStep1[1].split("\\.");
            String extractionGameDate = extractionStep1[0] + "/" + extractionStep2[0];

            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String now = localDateTime.format(dtf);

            int duration = gameData.getGameDuration();

            List<Participants> list1 = gameData.getParticipants();
            int[] maxGold = new int[10];
            int[] maxCrowd = new int[10];
            int[] maxDpm = new int[10];
            int[] maxGpm = new int[10];
            int[] maxDpg = new int[10];
//            int[] maxDpd = new int[10];
//            int[] maxTpd = new int[10];
            int[] maxDamageTotal = new int[10];
            int[] maxDamageTaken = new int[10];
            for(int i=0; i<list1.size(); i++) {
                Participants participants = list1.get(i);
                Stats statsData = participants.getStats();

                maxGold[i] = statsData.getGoldEarned();
                maxCrowd[i] = statsData.getTimeCCingOthers();
                maxDpm[i] = (int) (CalculatorCharacteristic(duration, statsData).getData().get("DPM") * 100);
                maxGpm[i] = (int) (CalculatorCharacteristic(duration, statsData).getData().get("GPM") * 100);
                maxDpg[i] = (int) (CalculatorCharacteristic(duration, statsData).getData().get("DPG") * 100);
                maxDamageTotal[i] = statsData.getTotalDamageDealtToChampions();
                maxDamageTaken[i] = statsData.getTotalDamageTaken();
            }

            Arrays.sort(maxGold);
            Arrays.sort(maxCrowd);
            Arrays.sort(maxDpm);
            Arrays.sort(maxGpm);
            Arrays.sort(maxDpg);
            Arrays.sort(maxDamageTotal);
            Arrays.sort(maxDamageTaken);

            lcgMatchInfoRepository.save(LCG_Match_Info.builder()
                    .lcgGameId(gameId)
                    .lcgGameDate(extractionGameDate)
                    .lcgGameMode(gameData.getGameMode())
                    .lcgGameType(gameData.getGameType())
                    .lcgGameDuration(gameData.getGameDuration())
                    .lcgGameMap(gameData.getMapId())
                    .lcgVerMain(version.get("ver"))
                    .lcgMaxGold(maxGold[9])
                    .lcgMaxCrowd(maxCrowd[9])
                    .lcgMaxDpm(maxDpm[9])
                    .lcgMaxGpm(maxGpm[9])
                    .lcgMaxDpg(maxDpg[9])
                    .lcgMaxDpd(0)
                    .lcgMaxTpd(0)
                    .lcgMaxDamageTotal(maxDamageTotal[9])
                    .lcgMaxDamageTaken(maxDamageTaken[9]).build());

            boolean existsCheck = lcgMatchEtcRepository.existsLCG_Match_EtcByLcgMainVer(version.get("ver"));

            if(!existsCheck) {
                List<LCG_Match_Etc> list = lcgMatchEtcRepository.findAll();
                
                String imageMain = version.get("cdn") + "/" + version.get("ver") + "/img/";
                String imageSub = version.get("cdn") + "/img/";
                
                lcgMatchEtcRepository.save(LCG_Match_Etc.builder()
                        .lcgVersion("LcgVer" + String.format("%04d", list.size()+1))
                        .lcgUpdateDate(gameData.getGameCreationDate())
                        .lcgUpdatePlayer(now)
                        .lcgUpdateData(now)
                        .lcgCdn(version.get("cdn"))
                        .lcgLang(version.get("lang"))
                        .lcgMainVer(version.get("ver"))
                        .lcgItemVer(version.get("item"))
                        .lcgRuneVer(version.get("rune"))
                        .lcgMasteryVer(version.get("mastery"))
                        .lcgSummonerVer(version.get("summoner"))
                        .lcgChampionVer(version.get("champion"))
                        .lcgMainImage(imageMain)
                        .lcgSubImage(imageSub).build());
            }

            return CommonResponseDto.setSuccess("MatchInfo Data 저장 완료!", "Success");
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Database Insert Failed !");
        }
    }

    @Transactional
    public CommonResponseDto<?> LCGMatchMainSave(Long gameId, GameData gameData) {

        try {
            List<ParticipantIdentities> list1 = gameData.getParticipantIdentities();
            List<Participants> list2 = gameData.getParticipants();
            List<Teams> list3 = gameData.getTeams();
            List<Metrics> list4 = mvpService.LCGMvpSelection(gameData).getData();

            String mvpPuuid = "";
            String acePuuid = "";
            int winTeam = 0;
            int failTeam = 0;

            for(Teams teams : list3) {
                if(teams.getWin().equals("Win")) {
                    winTeam = teams.getTeamId();
                } else {
                    failTeam = teams.getTeamId();
                }
            }

            for(Metrics metrics : list4) {
                if(winTeam == metrics.getTeam()) {
                    mvpPuuid = metrics.getPuuid();
                    break;
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
                Player playerData = participantIdentities.getPlayer();
                Stats statsData = participants.getStats();

                String mvpRank = "";

                for(int j=0; j<list4.size(); j++) {
                    int rank = j+1;
                    if(list4.get(j).getPuuid().equals(playerData.getPuuid())) {
                        if(mvpPuuid.equals(list4.get(j).getPuuid())) {
                            mvpRank = "M" + rank;
                        } else if (acePuuid.equals(list4.get(j).getPuuid())) {
                            mvpRank = "A" + rank;
                        } else {
                            mvpRank = "D" + rank;
                        }
                    }
                }

                String championName = ExtractionName(participants.getChampionId()).getData();

                lcgMatchMainRepository.save(LCG_Match_Main.builder()
                        .lcgGameId(gameId)
                        .lcgParticipantId(participantIdentities.getParticipantId())
                        .lcgTeamId(participants.getTeamId())
                        .lcgSummonerPuuid(playerData.getPuuid())
                        .lcgMvpRank(mvpRank)
                        .lcgChampionId(participants.getChampionId())
                        .lcgChampionName(championName)
                        .lcgChampionLevel(statsData.getChampLevel())
                        .lcgSpellName1(ExtractionSummonerSpell(participants.getSpell1Id()).getData())
                        .lcgSpellName2(ExtractionSummonerSpell(participants.getSpell2Id()).getData())
                        .lcgPerkName1(ExtractionPerk(statsData.getPerk0()).getData())
                        .lcgPerkName2(ExtractionPerk(statsData.getPerkSubStyle()).getData())
                        .lcgItemId1(statsData.getItem0()).lcgItemId2(statsData.getItem1())
                        .lcgItemId3(statsData.getItem2()).lcgItemId4(statsData.getItem3())
                        .lcgItemId5(statsData.getItem4()).lcgItemId6(statsData.getItem5())
                        .lcgItemId7(statsData.getItem6())
                        .lcgKillCount(statsData.getKills())
                        .lcgAssistCount(statsData.getAssists())
                        .lcgDeathCount(statsData.getDeaths())
                        .lcgDamageTotal(statsData.getTotalDamageDealtToChampions())
                        .lcgDamageTaken(statsData.getTotalDamageTaken())
                        .lcgMinionCount(statsData.getTotalMinionsKilled())
                        .lcgJungleCount(statsData.getNeutralMinionsKilled()).build());
            }

            return CommonResponseDto.setSuccess("MatchMain Data 저장 완료!", "Success");
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Database Insert Failed !");
        }
    }

    @Transactional
    public CommonResponseDto<?> LCGMatchSubSave(Long gameId, GameData gameData) {

        try {
            List<ParticipantIdentities> list1 = gameData.getParticipantIdentities();
            List<Participants> list2 = gameData.getParticipants();

            int duration = gameData.getGameDuration();

            for(int i=0; i<list1.size(); i++) {
                ParticipantIdentities participantIdentities = list1.get(i);
                Participants participants = list2.get(i);
                Player playerData = participantIdentities.getPlayer();
                Stats statsData = participants.getStats();

                lcgMatchSubRepository.save(LCG_Match_Sub.builder()
                        .lcgGameId(gameId)
                        .lcgParticipantId(participantIdentities.getParticipantId())
                        .lcgSummonerPuuid(playerData.getPuuid())
                        .lcgFirstKill(statsData.getFirstBloodKill() ? "Y" : "N")
                        .lcgFirstTower(statsData.getFirstTowerKill() ? "Y" : "N")
                        .lcgDoubleKill(statsData.getDoubleKills())
                        .lcgTripleKill(statsData.getTripleKills())
                        .lcgQuadraKill(statsData.getQuadraKills())
                        .lcgPentaKill(statsData.getPentaKills())
                        .lcgNormalWard(statsData.getWardsPlaced())
                        .lcgVisionWard(statsData.getVisionWardsBoughtInGame())
                        .lcgDestroyWard(statsData.getWardsKilled())
                        .lcgVisionScore(statsData.getVisionScore())
                        .lcgGoldTotal(statsData.getGoldEarned())
                        .lcgHealTotal(statsData.getTotalHeal())
                        .lcgCrowdTime(statsData.getTimeCCingOthers())
                        .lcgDestroyTower(statsData.getTurretKills())
                        .lcgDamageTower(statsData.getDamageDealtToTurrets())
                        .lcgDestroyInhibitor(statsData.getInhibitorKills())
                        .lcgDamagePerMinute(CalculatorCharacteristic(duration, statsData).getData().get("DPM"))
                        .lcgGoldPerMinute(CalculatorCharacteristic(duration, statsData).getData().get("GPM"))
                        .lcgDamagePerGold(CalculatorCharacteristic(duration, statsData).getData().get("DPG")).build());
            }

            return CommonResponseDto.setSuccess("MatchSub Data 저장 완료!", "Success");
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Database Insert Failed !");
        }
    }

    @Transactional
    public CommonResponseDto<?> LCGMatchTeamSave(Long gameId, GameData gameData) {

        try {
            List<Teams> list1 = gameData.getTeams();
            List<Participants> list2 = gameData.getParticipants();

            for(int i=0; i<list1.size(); i++) {
                Teams teams = list1.get(i);
                List<Bans> bans = teams.getBans();
                int bansLen = bans.size();

                int totalGold = 0;
                int totalKill = 0;
                int totalDeath = 0;
                int totalAssist = 0;

                for(int j=0; j<list2.size(); j++) {
                    Participants participants = list2.get(j);
                    Stats statsData = participants.getStats();

                    if(Objects.equals(teams.getTeamId(), participants.getTeamId())) {
                        totalGold += statsData.getGoldEarned();
                        totalKill += statsData.getKills();
                        totalDeath += statsData.getDeaths();
                        totalAssist += statsData.getAssists();
                    }
                }

                lcgMatchTeamRepository.save(LCG_Match_Team.builder()
                        .lcgGameId(gameId)
                        .lcgTeamId(teams.getTeamId())
                        .lcgTeamWin(teams.getWin().equals("Win") ? "Y" : "N")
                        .lcgFirstDragon(teams.getFirstDargon() ? "Y" : "N")
                        .lcgFirstBaron(teams.getFirstBaron() ? "Y" : "N")
                        .lcgFirstKill(teams.getFirstBlood() ? "Y" : "N")
                        .lcgFirstTower(teams.getFirstTower() ? "Y" : "N")
                        .lcgFirstInhibitor(teams.getFirstInhibitor() ? "Y" : "N")
                        .lcgTotalGold(totalGold)
                        .lcgTotalKill(totalKill)
                        .lcgTotalDeath(totalDeath)
                        .lcgTotalAssist(totalAssist)
                        .lcgTotalDragon(teams.getDragonKills())
                        .lcgTotalBaron(teams.getBaronKills())
                        .lcgTotalHorde(teams.getHordeKills())
                        .lcgTotalHerald(teams.getRiftHeraldKills())
                        .lcgTotalAtakhan(0)
                        .lcgTotalTower(teams.getTowerKills())
                        .lcgTotalInhibitor(teams.getInhibitorKills())
                        .lcgBansName1(bansLen >= 1 ? ExtractionName(bans.get(0).getChampionId()).getData() : "Empty")
                        .lcgBansName2(bansLen >= 2 ? ExtractionName(bans.get(1).getChampionId()).getData() : "Empty")
                        .lcgBansName3(bansLen >= 3 ? ExtractionName(bans.get(2).getChampionId()).getData() : "Empty")
                        .lcgBansName4(bansLen >= 4 ? ExtractionName(bans.get(3).getChampionId()).getData() : "Empty")
                        .lcgBansName5(bansLen >= 5 ? ExtractionName(bans.get(4).getChampionId()).getData() : "Empty")
                        .build());

            }

            return CommonResponseDto.setSuccess("MatchTeam Data 저장 완료!", "Success");
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Database Insert Failed !");
        }
    }

}
