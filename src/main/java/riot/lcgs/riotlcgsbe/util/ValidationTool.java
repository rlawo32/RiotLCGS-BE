package riot.lcgs.riotlcgsbe.util;

public class ValidationTool {
  
    public static CommonResponseDto<String> validationChkString(String param) {
		    if(param == null || param.length() == 0) {
			    throw new NullPointerException();
		    } else {
			    return CommonResponseDto.setSuccess("Success", param);
		    }
	  }

    public static CommonResponseDto<Integer> validationChkInteger(Integer param) {
		    if(param == null) {
			    throw new NullPointerException();
		    } else {
			    return CommonResponseDto.setSuccess("Success", param);
		    }
	  }
  
    public static CommonResponseDto<Long> validationChkLong(Long param) {
		    if(param == null) {
			    throw new NullPointerException();
		    } else {
			    return CommonResponseDto.setSuccess("Success", param);
		    }
	  }
  
    public static CommonResponseDto<Boolean> validationChkBoolean(Boolean param) {
		    if(param == null) {
			    throw new NullPointerException();
		    } else {
			    return CommonResponseDto.setSuccess("Success", param);
		    }
	  }
}
