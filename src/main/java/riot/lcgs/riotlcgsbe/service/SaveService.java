package riot.lcgs.riotlcgsbe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Match_Info;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Match_Main;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Match_Sub;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Match_Team;
import riot.lcgs.riotlcgsbe.jpa.repository.LCG_Match_Info_Repository;
import riot.lcgs.riotlcgsbe.jpa.repository.LCG_Match_Main_Repository;
import riot.lcgs.riotlcgsbe.jpa.repository.LCG_Match_Sub_Repository;
import riot.lcgs.riotlcgsbe.jpa.repository.LCG_Match_Team_Repository;
import riot.lcgs.riotlcgsbe.web.dto.CommonResponseDto;
import riot.lcgs.riotlcgsbe.web.dto.object.*;

import java.util.List;
import java.util.Map;

import static riot.lcgs.riotlcgsbe.service.HttpService.DataDragonAPIChampion;
import static riot.lcgs.riotlcgsbe.service.HttpService.DataDragonAPIVersion;
import static riot.lcgs.riotlcgsbe.util.ExtractionTool.*;

@RequiredArgsConstructor
@Service
public class SaveService {

    private final LCG_Match_Info_Repository lcgMatchInfoRepository;
    private final LCG_Match_Main_Repository lcgMatchMainRepository;
    private final LCG_Match_Sub_Repository lcgMatchSubRepository;
    private final LCG_Match_Team_Repository lcgMatchTeamRepository;

    @Transactional
    public CommonResponseDto<?> LCGMatchInfoSave(Long gameId, GameData gameData, Map<String, String> version) {

        try {
            lcgMatchInfoRepository.save(LCG_Match_Info.builder()
                    .lgcGameId(gameId)
                    .lgcGameDate(gameData.getGameCreationDate())
                    .lgcGameMode(gameData.getGameMode())
                    .lgcGameType(gameData.getGameType())
                    .lgcGameDuration(gameData.getGameDuration())
                    .lgcGameMap(gameData.getMapId())
                    .lgcVerMain(version.get("ver"))
                    .lgcVerCdn(version.get("cdn"))
                    .lgcVerLang(version.get("lang"))
                    .lgcVerItem(version.get("item"))
                    .lgcVerRune(version.get("rune"))
                    .lgcVerMastery(version.get("mastery"))
                    .lgcVerSummoner(version.get("summoner"))
                    .lgcVerChampion(version.get("champion")).build());
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setSuccess("Failed", ex.getMessage());
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
                        .lgcGameId(gameId)
                        .lgcParticipantId(participantIdentities.getParticipantId())
                        .lgcTeamId(participants.getTeamId())
                        .lgcSummonerId(playerData.getSummonerId())
                        .lgcSummonerName(playerData.getSummonerName())
                        .lgcSummonerTag(playerData.getTagLine())
                        .lgcChampionId(participants.getChampionId())
                        .lgcChampionName(championName)
                        .lgcChampionLevel(statsData.getChampLevel())
                        .lgcSpellName1(ExtractionSummoner(participants.getSpell1Id()).getData())
                        .lgcSpellName2(ExtractionSummoner(participants.getSpell2Id()).getData())
                        .lgcPerkName1(ExtractionPerk(statsData.getPerk0()).getData())
                        .lgcPerkName2(ExtractionPerk(statsData.getPerkSubStyle()).getData())
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
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setSuccess("Failed", "Database Insert Failed !");
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
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setSuccess("Failed", "Database Insert Failed !");
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
                        .lgcBansName1(ExtractionName(bans.get(0).getChampionId()).getData())
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
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setSuccess("Failed", "Database Insert Failed !");
        }

        return CommonResponseDto.setSuccess("Success", "");
    }
}
