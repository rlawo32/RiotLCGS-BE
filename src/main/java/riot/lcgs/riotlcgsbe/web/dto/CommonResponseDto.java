package riot.lcgs.riotlcgsbe.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "set")
public class CommonResponseDto<D> {
    private boolean result;
    private String message;
    private D data;

    public static <D> CommonResponseDto<D> setSuccess(String message, D data) {
        System.out.println(data + " : " + message);
        return CommonResponseDto.set(true, message, data);
    }

    public static <D> CommonResponseDto<D> setFailed(String message) {
        return CommonResponseDto.set(false, message, null);
    }
}

