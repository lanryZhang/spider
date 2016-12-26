package com.ifeng.pgc.utils;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Commons {

    public static final String API_VERSION = "2016-03-04";

    public static String getQueryString(Map<String, List<String>> parameters) {
        StringBuilder sb = new StringBuilder();

        for (Entry<String, List<String>> entry : parameters.entrySet()) {
            String key = entry.getKey();
            List<String> list = entry.getValue();
            if (list != null && list.size() > 0) {
                for (String string : list) {
                    sb.append(key).append("=").append(string).append("&");
                }
            } else {
                sb.append(key).append("=").append("&");
            }

        }
        return sb.substring(0, sb.length() - 1);
    }

}
