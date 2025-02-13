package riot.lcgs.riotlcgsbe.util;

import riot.lcgs.riotlcgsbe.web.dto.CommonResponseDto;

public class ValidationTool {
  
    public static String validationChkString(String param) {
		    if(param == null || param.length() == 0) {
			    throw new NullPointerException();
		    } else {
			    return param;
		    }
	  }

    public static Integer validationChkInteger(Integer param) {
		    if(param == null) {
			    throw new NullPointerException();
		    } else {
			    return param;
		    }
	  }
  
    public static Long validationChkLong(Long param) {
		    if(param == null) {
			    throw new NullPointerException();
		    } else {
			    return param;
		    }
	  }
  
    public static Boolean validationChkBoolean(Boolean param) {
		    if(param == null) {
			    throw new NullPointerException();
		    } else {
			    return param;
		    }
	  }
}
