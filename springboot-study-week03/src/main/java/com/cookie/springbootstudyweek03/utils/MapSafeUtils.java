package com.cookie.springbootstudyweek03.utils;

import java.util.Map;

/**
 * Map安全取值工具（全项目复用，解决空指针问题）
 */
public class MapSafeUtils {

    /**
     * 从Map中安全获取字符串（为空返回默认值）
     */
    public static String getString(Map<?, ?> map, Object key, String defaultValue) {
        if (map == null || !map.containsKey(key) || map.get(key) == null) {
            return defaultValue;
        }
        return map.get(key).toString();
    }

    /**
     * 从Map中安全获取字符串（为空返回空字符串）
     */
    public static String getString(Map<?, ?> map, Object key) {
        return getString(map, key, "");
    }

    /**
     * 从Map中安全获取整数（为空返回默认值）
     */
    public static int getInt(Map<?, ?> map, Object key, int defaultValue) {
        String value = getString(map, key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
