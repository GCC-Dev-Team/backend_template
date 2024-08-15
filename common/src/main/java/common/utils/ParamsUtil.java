package common.utils;


import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public class ParamsUtil {

    /**
     * 将request中的参数转换成Map
     */
    public static Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
        Map<String, String> retMap = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        requestParams.keySet().forEach(f->{
            String[] values = requestParams.get(f);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            retMap.put(f, valueStr);
        });
        return retMap;
    }
}
