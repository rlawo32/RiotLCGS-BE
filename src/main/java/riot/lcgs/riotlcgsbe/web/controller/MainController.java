package riot.lcgs.riotlcgsbe.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import riot.lcgs.riotlcgsbe.service.MainService;
import riot.lcgs.riotlcgsbe.web.dto.ApiTestDataRequestDto;
import riot.lcgs.riotlcgsbe.web.dto.CommonResponseDto;
import riot.lcgs.riotlcgsbe.web.dto.CustomGameRequestDto;
import riot.lcgs.riotlcgsbe.web.dto.PlayerDataRequestDto;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/riot")
public class MainController {

    private final MainService mainService;

    @PostMapping("/insertPlayerData")
    public CommonResponseDto<?> insertPlayerData(@RequestBody PlayerDataRequestDto requestDto) {
        return mainService.LCGCustomGamePlayerSave(requestDto);
    }

    @PostMapping("/insertData")
    public CommonResponseDto<?> insertData(@RequestBody CustomGameRequestDto requestDto) {
        return mainService.LolCustomGameDataSave(requestDto);
    }

    @PostMapping("/apiTest")
    public Map<String, String> apiTest(@RequestBody ApiTestDataRequestDto requestDto) {
        return mainService.apiTestService(requestDto);
    }

    @PostMapping("/test")
    public CommonResponseDto<?> test() {
        return mainService.testService();
    }
}
