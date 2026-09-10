package riot.lcgs.riotlcgsbe.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import riot.lcgs.riotlcgsbe.jpa.domain.LCG_Match_Info;
import riot.lcgs.riotlcgsbe.jpa.repository.LCG_Match_Info_Repository;
import riot.lcgs.riotlcgsbe.web.dto.CommonResponseDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DateTimeTool {

    private final LCG_Match_Info_Repository lcgMatchInfoRepository;

    public static CommonResponseDto<String> dateTimeCurrent() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String current = localDateTime.format(dtf);

        return CommonResponseDto.setSuccess("Success", current);
    }

    public CommonResponseDto<String> gameSetProvider() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minus = now.minusHours(4);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
        String todayGameSet = minus.format(formatter);
        String gameSet;

        Optional<LCG_Match_Info> lcgMatchInfo = lcgMatchInfoRepository.findTopByLcgGameSetStartingWithOrderByLcgGameIdDesc(todayGameSet);

        if(lcgMatchInfo.isPresent()) {
            String prevSet = lcgMatchInfo.get().getLcgGameSet(); // ex. 09/18-SET_01
            String[] setParts = prevSet.split("_");
            int nextNumber = Integer.parseInt(setParts[1]) + 1;
            gameSet = setParts[0] + "_" +String.format("%02d", nextNumber); // ex. 09/18-SET_02
        } else {
            gameSet = todayGameSet + "-SET_01";
        }

        return CommonResponseDto.setSuccess("Success", gameSet);
    }
}
