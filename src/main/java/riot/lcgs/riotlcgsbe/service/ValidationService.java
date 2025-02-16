package riot.lcgs.riotlcgsbe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import riot.lcgs.riotlcgsbe.web.dto.CommonResponseDto;
import riot.lcgs.riotlcgsbe.web.dto.object.*;

import java.util.Arrays;
import java.util.List;

import static riot.lcgs.riotlcgsbe.util.CalculatorTool.*;
import static riot.lcgs.riotlcgsbe.util.ValidationTool.*;

@RequiredArgsConstructor
@Service
public class ValidationService {

    @Transactional
    public CommonResponseDto<?> ValidationCheckGameData(GameData gameData) {

        try {
            validationChkString(gameData.getGameCreationDate());

            return CommonResponseDto.setSuccess("Success", "Data 검사 완료");
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Database Insert Failed !");
        }
    }

    @Transactional
    public CommonResponseDto<?> ValidationCheckRankData(List<RankData> rankData) {

        try {

            return CommonResponseDto.setSuccess("Success", "Data 검사 완료");
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.setFailed("Database Insert Failed !");
        }
    }
}
