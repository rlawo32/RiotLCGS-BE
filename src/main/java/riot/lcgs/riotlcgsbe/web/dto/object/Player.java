package riot.lcgs.riotlcgsbe.web.dto.object;

import lombok.Data;

@Data
public class Player {
    private Integer accountId;
    private Integer currentAccountId;
    private String currentPlatformId;
    private String gameName;
    private String matchHistoryUri;
    private String platformId;
    private Integer profileIcon;
    private String puuid;
    private Long summonerId;
    private String summonerName;
    private String tagLine;
}
