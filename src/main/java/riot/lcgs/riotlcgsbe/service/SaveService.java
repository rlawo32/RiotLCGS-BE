package riot.lcgs.riotlcgsbe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import riot.lcgs.riotlcgsbe.jpa.domain.*;
import riot.lcgs.riotlcgsbe.jpa.repository.*;
import riot.lcgs.riotlcgsbe.web.dto.CommonResponseDto;
import riot.lcgs.riotlcgsbe.web.dto.object.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.lang.Math;

import static riot.lcgs.riotlcgsbe.util.ExtractionTool.*;

@RequiredArgsConstructor
@Service
public class SaveService {

    private final LCG_Match_Info_Repository lcgMatchInfoRepository;
    private final LCG_Match_Main_Repository lcgMatchMainRepository;
    private final LCG_Match_Sub_Repository lcgMatchSubRepository;
    private final LCG_Match_Team_Repository lcgMatchTeamRepository;
    private final LCG_Match_Log_Repository lcgMatchLogRepository;
    private final LCG_Player_Statistics_Repository lcgPlayerStatisticsRepository;
    private final LCG_Player_Data_Repository lcgPlayerDataRepository;

    @Transactional
    public CommonResponseDto<?> LCGTeamLogSave(Long gameId, GameData gameData, TeamData teamData, Map<String, String> version) {

        try {
            String[] extractionStep1 = gameData.getGameCreationDate().split("T");
            String[] extractionStep2 = extractionStep1[1].split("\\.");
            String extractionGameDate = extractionStep1[0] + "/" + extractionStep2[0];

            List<Teams> list1 = gameData.getTeams();
            int gameWin = 0;

            for(int i=0; i<list1.size(); i++) {
                Teams teams = list1.get(i);
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
                    .teamAName1(teamData.getTeamA1())
                    .teamAName2(teamData.getTeamA2())
                    .teamAName3(teamData.getTeamA3())
                    .teamAName4(teamData.getTeamA4())
                    .teamAName5(teamData.getTeamA5())
                    .teamBName1(teamData.getTeamB1())
                    .teamBName2(teamData.getTeamB2())
                    .teamBName3(teamData.getTeamB3())
                    .teamBName4(teamData.getTeamB4())
                    .teamBName5(teamData.getTeamB5()).build());
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Database Insert Failed !");
        }

        return CommonResponseDto.setSuccess("Success", "");
    }

    @Transactional
    public CommonResponseDto<?> LCGMatchInfoSave(Long gameId, GameData gameData, Map<String, String> version) {

        try {
            String[] extractionStep1 = gameData.getGameCreationDate().split("T");
            String[] extractionStep2 = extractionStep1[1].split("\\.");
            String extractionGameDate = extractionStep1[0] + "/" + extractionStep2[0];

            List<Participants> list1 = gameData.getParticipants();
            int[] maxDamageTotal = new int[10];
            int[] maxDamageTaken = new int[10];
            for(int i=0; i<list1.size(); i++) {
                Participants participants = list1.get(i);
                Stats statsData = participants.getStats();

                maxDamageTotal[i] = statsData.getTotalDamageDealtToChampions();
                maxDamageTaken[i] = statsData.getTotalDamageTaken();
            }

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
                    .lcgVerCdn(version.get("cdn"))
                    .lcgVerLang(version.get("lang"))
                    .lcgVerItem(version.get("item"))
                    .lcgVerRune(version.get("rune"))
                    .lcgVerMastery(version.get("mastery"))
                    .lcgVerSummoner(version.get("summoner"))
                    .lcgVerChampion(version.get("champion"))
                    .lcgMaxDamageTotal(maxDamageTotal[9])
                    .lcgMaxDamageTaken(maxDamageTaken[9]).build());
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Database Insert Failed !");
        }

        return CommonResponseDto.setSuccess("Success", "");
    }

    @Transactional
    public CommonResponseDto<?> LCGMatchMainSave(Long gameId, GameData gameData) {

        try {
            List<ParticipantIdentities> list1 = gameData.getParticipantIdentities();
            List<Participants> list2 = gameData.getParticipants();

            for(int i=0; i<list1.size(); i++) {
                ParticipantIdentities participantIdentities = list1.get(i);
                Participants participants = list2.get(i);
                Player playerData = participantIdentities.getPlayer();
                Stats statsData = participants.getStats();

                String championName = ExtractionName(participants.getChampionId()).getData();

                lcgMatchMainRepository.save(LCG_Match_Main.builder()
                        .lcgGameId(gameId)
                        .lcgParticipantId(participantIdentities.getParticipantId())
                        .lcgTeamId(participants.getTeamId())
                        .lcgSummonerPuuid(playerData.getPuuid())
                        .lcgSummonerName(playerData.getSummonerName())
                        .lcgSummonerTag(playerData.getTagLine())
                        .lcgChampionId(participants.getChampionId())
                        .lcgChampionName(championName)
                        .lcgChampionLevel(statsData.getChampLevel())
                        .lcgSpellName1(ExtractionSummonerSpell(participants.getSpell1Id()).getData())
                        .lcgSpellName2(ExtractionSummonerSpell(participants.getSpell2Id()).getData())
                        .lcgPerkName1(ExtractionPerk(statsData.getPerk0()).getData())
                        .lcgPerkName2(ExtractionPerk(statsData.getPerkSubStyle()).getData())
                        .lcgItemId1(statsData.getItem0()).lcgItemId2(statsData.getItem1())
                        .lcgItemId3(statsData.getItem2()).lcgItemId4(statsData.getItem3())
                        .lcgItemId5(statsData.getItem4()).lcgItemId6(statsData.getItem5()).lcgItemId7(statsData.getItem6())
                        .lcgKillCount(statsData.getKills())
                        .lcgAssistCount(statsData.getAssists())
                        .lcgDeathCount(statsData.getDeaths())
                        .lcgDamageTotal(statsData.getTotalDamageDealtToChampions())
                        .lcgDamageTaken(statsData.getTotalDamageTaken())
                        .lcgMinionCount(statsData.getTotalMinionsKilled())
                        .lcgJungleCount(statsData.getNeutralMinionsKilled())
                        .lcgVisionScore(statsData.getVisionScore()).build());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Database Insert Failed !");
        }

        return CommonResponseDto.setSuccess("Success", "");
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

                // DPM, GPM, DPG Calculator
                int damage = statsData.getTotalDamageDealtToChampions();
                int gold = statsData.getGoldEarned();
                double convertMinute = Math.floor(duration / 60);
                int minusDPS = (int) (Math.floor(damage / duration) * (duration % 60));
                int minusGPS = (int) (Math.floor(gold / duration) * (duration % 60));

                double DPM = (damage-minusDPS) / convertMinute;
                double GPM = (gold-minusGPS) / convertMinute;

                lcgMatchSubRepository.save(LCG_Match_Sub.builder()
                        .lcgGameId(gameId)
                        .lcgParticipantId(participantIdentities.getParticipantId())
                        .lcgSummonerPuuid(playerData.getPuuid())
                        .lcgFirstKill(statsData.isFirstBloodKill() ? "Y" : "N")
                        .lcgFirstTower(statsData.isFirstTowerKill() ? "Y" : "N")
                        .lcgDoubleKill(statsData.getDoubleKills())
                        .lcgTripleKill(statsData.getTripleKills())
                        .lcgQuadraKill(statsData.getQuadraKills())
                        .lcgPentaKill(statsData.getPentaKills())
                        .lcgNormalWard(statsData.getWardsPlaced())
                        .lcgVisionWard(statsData.getVisionWardsBoughtInGame())
                        .lcgDestroyWard(statsData.getWardsKilled())
                        .lcgGoldTotal(statsData.getGoldEarned())
                        .lcgHealTotal(statsData.getTotalHeal())
                        .lcgCrowdTime(statsData.getTimeCCingOthers())
                        .lcgDestroyTower(statsData.getTurretKills())
                        .lcgDamageTower(statsData.getDamageDealtToTurrets())
                        .lcgDestroyInhibitor(statsData.getInhibitorKills())
                        .lcgDamagePerMinute(DPM)
                        .lcgGoldPerMinute(GPM)
                        .lcgDamagePerGold(DPM / GPM).build());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Database Insert Failed !");
        }

        return CommonResponseDto.setSuccess("Success", "");
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

                    if(teams.getTeamId() == participants.getTeamId()) {
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
                        .lcgFirstDragon(teams.isFirstDargon() ? "Y" : "N")
                        .lcgFirstBaron(teams.isFirstBaron() ? "Y" : "N")
                        .lcgFirstKill(teams.isFirstBlood() ? "Y" : "N")
                        .lcgFirstTower(teams.isFirstTower() ? "Y" : "N")
                        .lcgFirstInhibitor(teams.isFirstInhibitor() ? "Y" : "N")
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
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Database Insert Failed !");
        }

        return CommonResponseDto.setSuccess("Success", "");
    }

    @Transactional
    public CommonResponseDto<?> LCGPlayerStatisticsSave(GameData gameData) {

        try {
            List<ParticipantIdentities> list1 = gameData.getParticipantIdentities();
            List<Participants> list2 = gameData.getParticipants();
            List<Teams> list3 = gameData.getTeams();

            for(int i=0; i<list1.size(); i++) {
                ParticipantIdentities participantIdentities = list1.get(i);
                Participants participants = list2.get(i);
                Stats statsData = participants.getStats();
                Teams teams = new Teams();

                for(int j=0; j<list3.size(); j++) {
                    teams = list3.get(j);
                    if(participants.getTeamId() == teams.getTeamId()) {
                        break;
                    }
                }

                // 사용자 정보
                String puuid = participantIdentities.getPlayer().getPuuid();
                String nickname = participantIdentities.getPlayer().getGameName() + "#" + participantIdentities.getPlayer().getTagLine();

                // Score 연산
                Long multiKillScore = statsData.getDoubleKills() + (statsData.getTripleKills() * 3L) + (statsData.getQuadraKills() * 10L) + (statsData.getPentaKills() * 50L);
                Long jungleObjectScore = teams.getHordeKills() + (teams.getDragonKills() * 2L) + (teams.getRiftHeraldKills() * 4L) + (teams.getBaronKills() * 7L);

                boolean existsCheck = lcgPlayerStatisticsRepository.existsLCG_Player_StatisticsByLcgSummonerPuuid(puuid);

                if(existsCheck) {
                    LCG_Player_Statistics lcgPlayerStatistics = lcgPlayerStatisticsRepository.findById(puuid)
                            .orElseThrow(() -> new IllegalArgumentException("해당 플레이어가 없습니다. Puuid. : " + puuid));

                    lcgPlayerStatistics.playerDataCounting(nickname, statsData, teams, multiKillScore, jungleObjectScore);
                } else {
                    lcgPlayerStatisticsRepository.save(LCG_Player_Statistics.builder()
                            .lcgSummonerPuuid(puuid)
                            .lcgPlayer("")
                            .lcgNickname(nickname)
                            .lcgCountVictory(teams.getWin().equals("Win") ? 1L : 0L)
                            .lcgCountDefeat(teams.getWin().equals("Fail") ? 1L : 0L)
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
                            .lcgMultiKillScore(multiKillScore)
                            .lcgCountDragon((long)teams.getDragonKills())
                            .lcgCountBaron((long)teams.getBaronKills())
                            .lcgCountHorde((long)teams.getHordeKills())
                            .lcgCountHerald((long)teams.getRiftHeraldKills())
                            .lcgCountAtakhan(0L)
                            .lcgJungleObjectScore(jungleObjectScore).build());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Database Insert Failed !");
        }

        return CommonResponseDto.setSuccess("Success", "");
    }

    @Transactional
    public CommonResponseDto<?> LCGPlayerDataSave(GameData gameData) {

        try {
            List<ParticipantIdentities> list1 = gameData.getParticipantIdentities();

            for(int i=0; i<list1.size(); i++) {
                ParticipantIdentities participantIdentities = list1.get(i);
                Player playerData = participantIdentities.getPlayer();

                String puuid = playerData.getPuuid();
                String nickname = playerData.getGameName() + "#" + playerData.getTagLine();
                boolean existsCheck = lcgPlayerDataRepository.existsLCG_Player_DataByLcgSummonerPuuid(puuid);

                if(existsCheck) {
                    LCG_Player_Data lcgPlayerData = lcgPlayerDataRepository.findById(puuid)
                            .orElseThrow(() -> new IllegalArgumentException("해당 플레이어가 없습니다. Puuid. : " + puuid));

                    lcgPlayerData.playerDataUpdate(playerData, "");
                } else {
                    lcgPlayerDataRepository.save(LCG_Player_Data.builder()
                            .lcgSummonerPuuid(puuid)
                            .lcgPlayer("")
                            .lcgSummonerNickname(nickname)
                            .lcgSummonerId(playerData.getSummonerId())
                            .lcgSummonerName(playerData.getSummonerName())
                            .lcgSummonerTag(playerData.getTagLine())
                            .build());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Database Insert Failed !");
        }

        return CommonResponseDto.setSuccess("Success", "");
    }
}
