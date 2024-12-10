package riot.lcgs.riotlcgsbe.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import riot.lcgs.riotlcgsbe.service.MainService;
import riot.lcgs.riotlcgsbe.web.dto.CommonResponseDto;
import riot.lcgs.riotlcgsbe.web.dto.CustomGameRequestDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/riot")
public class MainController {

    private final MainService mainService;

    @PostMapping("/insertData")
    public CommonResponseDto<?> insertData(@RequestBody CustomGameRequestDto requestDto) {
        System.out.println(requestDto.getGameData().getGameCreation());
        System.out.println(requestDto.getGameData().getParticipantIdentities().get(0).getPlayer().getSummonerName());
        return mainService.GameDataOrganizeService(requestDto);
    }

}
