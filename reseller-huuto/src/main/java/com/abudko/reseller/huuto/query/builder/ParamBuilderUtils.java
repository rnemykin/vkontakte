package com.abudko.reseller.huuto.query.builder;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.util.StringUtils;

public class ParamBuilderUtils {
    
    private static final String PARAM_FORMAT = "%s=%s";
    private static final String RESTFUL_PARAM_FORMAT = "/%s/%s";

    public static String buildRestfulUrl(Map<String, String> parameters) {
        
        StringBuilder sb = new StringBuilder();
        
        Set<Entry<String, String>> entrySet = parameters.entrySet();
        for (Entry<String, String> entry : entrySet) {
            String key = entry.getKey();
            String value = entry.getValue();
            
            if (StringUtils.hasLength(value)) {
                String parameter = String.format(RESTFUL_PARAM_FORMAT, key, value);
                sb.append(parameter);
            }
        }
        
        String query = sb.toString();
        
        return query.trim();
    }

    public static String buildUrl(Map<String, String> parameters) {

        StringBuilder sb = new StringBuilder();

        Set<Entry<String, String>> entrySet = parameters.entrySet();
        boolean first = true;
        for (Entry<String, String> entry : entrySet) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (StringUtils.hasLength(value)) {
                if (first) {
                    first = false;
                    sb.append("?");
                }
                else {
                    sb.append("&");
                }
                String parameter = String.format(PARAM_FORMAT, key, value);
                sb.append(parameter);
            }
        }

        String query = sb.toString();

        return query.trim();
    }
}
