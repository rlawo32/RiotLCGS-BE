package riot.lcgs.riotlcgsbe.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/insertGameData")
    public CommonResponseDto<?> insertGameData(@RequestBody CustomGameRequestDto requestDto) {
        return mainService.LolCustomGameDataSave(requestDto);
    }

    @PostMapping("/insertTest")
    public CommonResponseDto<?> insertTest(@RequestBody CustomGameRequestDto requestDto) {
        return mainService.insertTestService(requestDto);
    }

    @PostMapping("/apiTest")
    public Map<String, String> apiTest(@RequestBody ApiTestDataRequestDto requestDto) {
        return mainService.apiTestService(requestDto);
    }

    @GetMapping("/uploadImage")
    public void uploadImage() {
        mainService.LCGCustomGameImageSave(true);
    }

    @GetMapping("/updatePatchNote")
    public void updatePatchNote() {
        mainService.LCGPatchNoteSave(true);
    }

    @PostMapping("/test")
    public CommonResponseDto<?> test() {
        return mainService.testService();
    }
}
