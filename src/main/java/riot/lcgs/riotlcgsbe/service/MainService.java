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

import java.util.List;

@RequiredArgsConstructor
@Service
public class MainService {

    private final HttpService httpService;

    public CommonResponseDto<?> LolCustomGameDataSave(CustomGameRequestDto requestDto) {

        GameData gameData = requestDto.getGameData();
        TeamData teamData = requestDto.getTeamData();
        Long gameId = gameData.getGameId();

        // 동일한 gameId로 insert 불가
        // info 테이블에 각 게임 데이터(champion, item ...) 버전 insert (필드 새로 생성해야함)
        // spellId는 DB에 넣기 (자동화 만들기, 게임 버전이 변경될 때 마다 업데이트)
        // json 통신 데이터 자바 객체에 매핑 하기
        // spellId, perkId, championId 는 이미지 주소가 id 값이 아닌 name 값으로 되어 있기에 json 에서 name 추출해야함 (item 은 id 값임)

        CommonResponseDto<?> commonResponseDto = httpService.DataDragonAPIVersion();

        System.out.println(commonResponseDto.getData());


        return CommonResponseDto.setSuccess("Success", "");
    }
}
