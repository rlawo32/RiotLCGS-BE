package riot.lcgs.riotlcgsbe.web.dto.object;

import lombok.Data;

import java.util.List;

@Data
public class GameData {
    private String endOfGameResult;
    private Long gameCreation;
    private String gameCreationDate;
    private int gameDuration;
    private Long gameId;
    private String gameMode;
    private String gameType;
    private String gameVersion;
    private int mapId;
    private List<ParticipantIdentities> participantIdentities;
    private List<Participants> participants;
    private Long queueId;
    private Long seasonId;
    private List<Teams> teams;
}
