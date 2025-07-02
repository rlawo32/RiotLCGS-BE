package riot.lcgs.riotlcgsbe.util;

import riot.lcgs.riotlcgsbe.web.dto.object.GameData;
import riot.lcgs.riotlcgsbe.web.dto.object.TeamData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountCheckTool {
  
    public static Map<String, Object> playerAccountChk(GameData gameData, List<TeamData> teamData) {

		// 경우2(원딜) -> 경우1(알코올) 변환
		for(int i=0; i<gameData.getParticipantIdentities().size(); i++) {
			if(gameData.getParticipantIdentities().get(i).getPlayer().getPuuid().equals("8c77580d-04d8-5885-94a0-6fea71350fa4")) {
				gameData.getParticipantIdentities().get(i).getPlayer().setPuuid("0d698fc9-7d69-5c0f-8c10-14a827e8de2d");
			}
		}
		for(int i=0; i<teamData.size(); i++) {
			if(teamData.get(i).getPuuid().equals("8c77580d-04d8-5885-94a0-6fea71350fa4")) {
				teamData.get(i).setPuuid("0d698fc9-7d69-5c0f-8c10-14a827e8de2d");
			}
		}

		// 25년 - 승준이가 현석 아이디 사용 / 현석 -> 승준 변환
		// 2월8일 all
		// 2월15일 3~6번째 게임
		// 5월4일 all
		if(gameData.getGameCreationDate().split("T")[0].equals("2025-02-08")
				|| (gameData.getGameCreationDate().split("T")[0].equals("2025-02-15") && gameData.getGameId() == 7523508506L)
				|| (gameData.getGameCreationDate().split("T")[0].equals("2025-02-15") && gameData.getGameId() == 7523613038L)
				|| (gameData.getGameCreationDate().split("T")[0].equals("2025-02-15") && gameData.getGameId() == 7523737287L)
				|| (gameData.getGameCreationDate().split("T")[0].equals("2025-02-15") && gameData.getGameId() == 7523820335L)
				|| gameData.getGameCreationDate().split("T")[0].equals("2025-05-04")
		) {
			for(int i=0; i<gameData.getParticipantIdentities().size(); i++) {
				if(gameData.getParticipantIdentities().get(i).getPlayer().getPuuid().equals("1127fed4-642a-5b70-bab9-1c7a326ca923")) {
					gameData.getParticipantIdentities().get(i).getPlayer().setPuuid("864ff5ac-b218-55fd-94ba-cb9cabe66ce4");
				}
			}
			for(int i=0; i<teamData.size(); i++) {
				if(teamData.get(i).getPuuid().equals("1127fed4-642a-5b70-bab9-1c7a326ca923")) {
					teamData.get(i).setPuuid("864ff5ac-b218-55fd-94ba-cb9cabe66ce4");
				}
			}
		}

		// 25년 - 성재가 다른 아이디 사용 / 다른 아이디 -> 성재 변환
		// 3월25일 1번째 게임
		if(gameData.getGameCreationDate().split("T")[0].equals("2025-03-25")) {
			for(int i=0; i<gameData.getParticipantIdentities().size(); i++) {
				if(gameData.getParticipantIdentities().get(i).getPlayer().getPuuid().equals("ce41bfe2-7ce4-5965-82d4-729a01bf4568")) {
					gameData.getParticipantIdentities().get(i).getPlayer().setPuuid("1e062cfe-c62e-53ef-9145-ab0d6c76d40d");
				}
			}
			for(int i=0; i<teamData.size(); i++) {
				if(teamData.get(i).getPuuid().equals("ce41bfe2-7ce4-5965-82d4-729a01bf4568")) {
					teamData.get(i).setPuuid("1e062cfe-c62e-53ef-9145-ab0d6c76d40d");
				}
			}
		}

		// 25년 - 승준이가 광호 아이디 사용 / 광호 -> 승준 변환
		// 4월26일 1~6번째 게임
		if((gameData.getGameCreationDate().split("T")[0].equals("2025-04-26") && gameData.getGameId() == 7615805208L)
				|| (gameData.getGameCreationDate().split("T")[0].equals("2025-04-26") && gameData.getGameId() == 7615880494L)
				|| (gameData.getGameCreationDate().split("T")[0].equals("2025-04-26") && gameData.getGameId() == 7615971149L)
				|| (gameData.getGameCreationDate().split("T")[0].equals("2025-04-26") && gameData.getGameId() == 7616037560L)
				|| (gameData.getGameCreationDate().split("T")[0].equals("2025-04-26") && gameData.getGameId() == 7616130103L)
				|| (gameData.getGameCreationDate().split("T")[0].equals("2025-04-26") && gameData.getGameId() == 7616180111L)
		) {
			for(int i = 0; i < gameData.getParticipantIdentities().size(); i++) {
				if (gameData.getParticipantIdentities().get(i).getPlayer().getPuuid().equals("60e3571d-2b64-5e2b-b9ba-c73789b86639")) {
					gameData.getParticipantIdentities().get(i).getPlayer().setPuuid("864ff5ac-b218-55fd-94ba-cb9cabe66ce4");
				}
			}
			for(int i = 0; i < teamData.size(); i++) {
				if (teamData.get(i).getPuuid().equals("60e3571d-2b64-5e2b-b9ba-c73789b86639")) {
					teamData.get(i).setPuuid("864ff5ac-b218-55fd-94ba-cb9cabe66ce4");
				}
			}
		}

		HashMap<String, Object> map = new HashMap<>();
		map.put("gameData", gameData);
		map.put("teamData", teamData);

		return map;
	}
}
