package riot.lcgs.riotlcgsbe.util;

import riot.lcgs.riotlcgsbe.web.dto.CommonResponseDto;
import riot.lcgs.riotlcgsbe.web.dto.object.Metrics;
import riot.lcgs.riotlcgsbe.web.dto.object.Stats;
import riot.lcgs.riotlcgsbe.web.dto.object.Teams;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class DateTimeTool {

    public static CommonResponseDto<String> dateTimeCurrent() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String current = localDateTime.format(dtf);

        return CommonResponseDto.setSuccess("Success", current);
    }
}
