package riot.lcgs.riotlcgsbe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Match_Info;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Match_Main;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Match_Sub;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Match_Team;
import riot.lcgs.riotlcgsbe.jpa.repository.LCG_Match_Info_Repository;
import riot.lcgs.riotlcgsbe.jpa.repository.LCG_Match_Main_Repository;
import riot.lcgs.riotlcgsbe.jpa.repository.LCG_Match_Sub_Repository;
import riot.lcgs.riotlcgsbe.jpa.repository.LCG_Match_Team_Repository;
import riot.lcgs.riotlcgsbe.web.dto.CommonResponseDto;
import riot.lcgs.riotlcgsbe.web.dto.CustomGameRequestDto;
import riot.lcgs.riotlcgsbe.web.dto.object.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MainService {

    private final LCG_Match_Info_Repository lcgMatchInfoRepository;
    private final LCG_Match_Main_Repository lcgMatchMainRepository;
    private final LCG_Match_Sub_Repository lcgMatchSubRepository;
    private final LCG_Match_Team_Repository lcgMatchTeamRepository;

    public CommonResponseDto<?> LolCustomGameDataSave(CustomGameRequestDto requestDto) {

        GameData gameData = requestDto.getGameData();
        TeamData teamData = requestDto.getTeamData();
        Long gameId = gameData.getGameId();

        // 동일한 gameId로 insert 불가

        /*
        lcgMatchInfoRepository.save(LCG_Match_Info.builder()
                .lgcGameId(gameId)
                .lgcGameDate(gameData.getGameCreationDate())
                .lgcGameMode(gameData.getGameMode())
                .lgcGameType(gameData.getGameType())
                .lgcGameDuration(gameData.getGameDuration())
                .lgcGameMap(gameData.getMapId()).build());
         */
        List<ParticipantIdentities> list1 = gameData.getParticipantIdentities();
        List<Participants> list2 = gameData.getParticipants();
        List<Teams> list3 = gameData.getTeams();

        int matchLen = list1.size();
        int teamsLen = list3.size();

        DataDragonAPIVersion();
/*
        for(int i=0; i<matchLen; i++) {
            int gbnId = list1.get(i).getParticipantId();

            ParticipantIdentities participantIdentities = list1.get(i);
            Participants participants = list2.get(i);
            Player playerData = participantIdentities.getPlayer();
            Stats statsData = participants.getStats();

            lcgMatchMainRepository.save(LCG_Match_Main.builder()
                    .lgcGameId(gameId)
                    .lgcParticipantId(participantIdentities.getParticipantId())
                    .lgcTeamId(participants.getTeamId())
                    .lgcSummonerId(playerData.getSummonerId())
                    .lgcSummonerName(playerData.getSummonerName())
                    .lgcSummonerTag(playerData.getTagLine())
                    .lgcChampionId(participants.getChampionId())
                    .lgcChampionName("NONE")
                    .lgcChampionLevel(statsData.getChampLevel())
                    .lgcSpellId1(participants.getSpell1Id()).lgcSpellId2(participants.getSpell2Id())
                    .lgcPerkId1(statsData.getPerk0()).lgcPerkId2(statsData.getPerkSubStyle())
                    .lgcItemId1(statsData.getItem0()).lgcItemId2(statsData.getItem1())
                    .lgcItemId3(statsData.getItem2()).lgcItemId4(statsData.getItem3())
                    .lgcItemId5(statsData.getItem4()).lgcItemId6(statsData.getItem5()).lgcItemId7(statsData.getItem6())
                    .lgcKillCount(statsData.getKills())
                    .lgcAssistCount(statsData.getAssists())
                    .lgcDeathCount(statsData.getDeaths())
                    .lgcDamageTotal(statsData.getTotalDamageDealtToChampions())
                    .lgcDamageTaken(statsData.getTotalDamageTaken())
                    .lgcMinionCount(statsData.getTotalMinionsKilled())
                    .lgcJungleCount(statsData.getNeutralMinionsKilled())
                    .lgcVisionScore(statsData.getVisionScore()).build());

            lcgMatchSubRepository.save(LCG_Match_Sub.builder()
                    .lgcGameId(gameId)
                    .lgcParticipantId(participantIdentities.getParticipantId())
                    .lgcFirstKill(statsData.isFirstBloodKill() ? "Y" : "N")
                    .lgcFirstTower(statsData.isFirstTowerKill() ? "Y" : "N")
                    .lgcDoubleKill(statsData.getDoubleKills())
                    .lgcTripleKill(statsData.getTripleKills())
                    .lgcQuadraKill(statsData.getQuadraKills())
                    .lgcPentaKill(statsData.getPentaKills())
                    .lgcNormalWard(statsData.getWardsPlaced())
                    .lgcVisionWard(statsData.getVisionWardsBoughtInGame())
                    .lgcGoldTotal(statsData.getGoldEarned())
                    .lgcHealTotal(statsData.getTotalHeal())
                    .lgcCrowdTime(statsData.getTimeCCingOthers())
                    .lgcDestroyTower(statsData.getTurretKills())
                    .lgcDamageTower(statsData.getDamageDealtToTurrets()).build());
        }


        for(int i=0; i<teamsLen; i++) {
            Teams teams = list3.get(i);
            List<Bans> bans = teams.getBans();

            lcgMatchTeamRepository.save(LCG_Match_Team.builder()
                    .lgcGameId(gameId)
                    .lgcTeamId(teams.getTeamId())
                    .lgcFirstDragon(teams.isFirstDargon() ? "Y" : "N")
                    .lgcFirstBaron(teams.isFirstBaron() ? "Y" : "N")
                    .lgcFirstKill(teams.isFirstBlood() ? "Y" : "N")
                    .lgcFirstTower(teams.isFirstTower() ? "Y" : "N")
                    .lgcFirstInhibitor(teams.isFirstInhibitor() ? "Y" : "N")
                    .lgcDragonTotal(teams.getDragonKills())
                    .lgcBaronTotal(teams.getBaronKills())
                    .lgcTowerTotal(teams.getTowerKills())
                    .lgcHordeTotal(teams.getHordeKills())
                    .lgcHeraldTotal(teams.getRiftHeraldKills())
                    .lgcBansChamp1(teams.getTeamId() == 100 ? bans.get(0).getChampionId() : 0)
                    .lgcBansName1("")
                    .lgcBansChamp2(teams.getTeamId() == 100 ? bans.get(1).getChampionId() : 0)
                    .lgcBansName2("")
                    .lgcBansChamp3(teams.getTeamId() == 100 ? bans.get(2).getChampionId() : 0)
                    .lgcBansName3("")
                    .lgcBansChamp4(teams.getTeamId() == 100 ? bans.get(3).getChampionId() : 0)
                    .lgcBansName4("")
                    .lgcBansChamp5(teams.getTeamId() == 100 ? bans.get(4).getChampionId() : 0)
                    .lgcBansName5("")
                    .lgcBansChamp6(teams.getTeamId() == 200 ? bans.get(0).getChampionId() : 0)
                    .lgcBansName6("")
                    .lgcBansChamp7(teams.getTeamId() == 200 ? bans.get(1).getChampionId() : 0)
                    .lgcBansName7("")
                    .lgcBansChamp8(teams.getTeamId() == 200 ? bans.get(2).getChampionId() : 0)
                    .lgcBansName8("")
                    .lgcBansChamp9(teams.getTeamId() == 200 ? bans.get(3).getChampionId() : 0)
                    .lgcBansName9("")
                    .lgcBansChamp10(teams.getTeamId() == 200 ? bans.get(4).getChampionId() : 0)
                    .lgcBansName10("")
                    .build());

        }

 */

        return CommonResponseDto.setSuccess("Success", "");
    }

    public CommonResponseDto<?> DataDragonAPIVersion() {

        try {
            URL url = new URL("https://ddragon.leagueoflegends.com/realms/na.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setDoOutput(true);

            BufferedReader br = null;
            if(conn.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            }

            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            conn.disconnect();

            System.out.println(sb.toString());
        } catch (IOException ex1) {
            ex1.printStackTrace();
            return CommonResponseDto.setSuccess("Failed", ex1.getMessage());
        }


        return CommonResponseDto.setSuccess("Success", "");
    }
}
