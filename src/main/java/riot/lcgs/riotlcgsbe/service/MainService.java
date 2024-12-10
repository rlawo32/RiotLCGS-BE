package riot.lcgs.riotlcgsbe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import riot.lcgs.riotlcgsbe.web.dto.CommonResponseDto;
import riot.lcgs.riotlcgsbe.web.dto.CustomGameRequestDto;

@RequiredArgsConstructor
@Service
public class MainService {

    public CommonResponseDto<?> GameDataOrganizeService(CustomGameRequestDto requestDto) {


        return CommonResponseDto.setSuccess("Success", "");
    }
}
