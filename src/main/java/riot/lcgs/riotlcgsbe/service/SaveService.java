package riot.lcgs.riotlcgsbe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import riot.lcgs.riotlcgsbe.jpa.domain.*;
import riot.lcgs.riotlcgsbe.jpa.repository.*;
import riot.lcgs.riotlcgsbe.web.dto.CommonResponseDto;
import riot.lcgs.riotlcgsbe.web.dto.object.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static riot.lcgs.riotlcgsbe.util.ExtractionTool.*;

@RequiredArgsConstructor
@Service
public class SaveService {

    private final LCG_Match_Info_Repository lcgMatchInfoRepository;
    private final LCG_Match_Main_Repository lcgMatchMainRepository;
    private final LCG_Match_Sub_Repository lcgMatchSubRepository;
    private final LCG_Match_Team_Repository lcgMatchTeamRepository;
    private final LCG_Team_Log_Repository lcgTeamLogRepository;

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

            lcgTeamLogRepository.save(LCG_Team_Log.builder()
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
                        .lcgSummonerId(playerData.getSummonerId())
                        .lcgSummonerName(playerData.getSummonerName())
                        .lcgSummonerTag(playerData.getTagLine())
                        .lcgChampionId(participants.getChampionId())
                        .lcgChampionName(championName)
                        .lcgChampionLevel(statsData.getChampLevel())
                        .lcgSpellName1(ExtractionSummoner(participants.getSpell1Id()).getData())
                        .lcgSpellName2(ExtractionSummoner(participants.getSpell2Id()).getData())
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

            for(int i=0; i<list1.size(); i++) {
                ParticipantIdentities participantIdentities = list1.get(i);
                Participants participants = list2.get(i);
                Stats statsData = participants.getStats();

                lcgMatchSubRepository.save(LCG_Match_Sub.builder()
                        .lcgGameId(gameId)
                        .lcgParticipantId(participantIdentities.getParticipantId())
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
                        .lcgDamageTower(statsData.getDamageDealtToTurrets()).build());
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

            for(int i=0; i<list1.size(); i++) {
                Teams teams = list1.get(i);
                List<Bans> bans = teams.getBans();

                lcgMatchTeamRepository.save(LCG_Match_Team.builder()
                        .lcgGameId(gameId)
                        .lcgTeamId(teams.getTeamId())
                        .lcgTeamWin(teams.getWin().equals("Win") ? "Y" : "N")
                        .lcgFirstDragon(teams.isFirstDargon() ? "Y" : "N")
                        .lcgFirstBaron(teams.isFirstBaron() ? "Y" : "N")
                        .lcgFirstKill(teams.isFirstBlood() ? "Y" : "N")
                        .lcgFirstTower(teams.isFirstTower() ? "Y" : "N")
                        .lcgFirstInhibitor(teams.isFirstInhibitor() ? "Y" : "N")
                        .lcgDragonTotal(teams.getDragonKills())
                        .lcgBaronTotal(teams.getBaronKills())
                        .lcgTowerTotal(teams.getTowerKills())
                        .lcgHordeTotal(teams.getHordeKills())
                        .lcgHeraldTotal(teams.getRiftHeraldKills())
                        .lcgBansName1(ExtractionName(bans.get(0).getChampionId()).getData())
                        .lcgBansName2(ExtractionName(bans.get(1).getChampionId()).getData())
                        .lcgBansName3(ExtractionName(bans.get(2).getChampionId()).getData())
                        .lcgBansName4(ExtractionName(bans.get(3).getChampionId()).getData())
                        .lcgBansName5(ExtractionName(bans.get(4).getChampionId()).getData())
                        .build());

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Database Insert Failed !");
        }

        return CommonResponseDto.setSuccess("Success", "");
    }
}
