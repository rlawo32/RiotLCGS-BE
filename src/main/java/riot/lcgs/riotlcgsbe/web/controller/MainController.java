package riot.lcgs.riotlcgsbe.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import riot.lcgs.riotlcgsbe.service.MainService;
import riot.lcgs.riotlcgsbe.service.SaveService;
import riot.lcgs.riotlcgsbe.web.dto.CommonResponseDto;
import riot.lcgs.riotlcgsbe.web.dto.CustomGameRequestDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/riot")
public class MainController {

    private final MainService mainService;
    private final SaveService saveService;

    @PostMapping("/insertPlayerData")
    public CommonResponseDto<?> insertPlayerData(@RequestBody CustomGameRequestDto requestDto) {
        return saveService.LCGPlayerDataSave(requestDto);
    }

    @PostMapping("/insertData")
    public CommonResponseDto<?> insertData(@RequestBody CustomGameRequestDto requestDto) {
        return mainService.LolCustomGameDataSave(requestDto);
    }

}
