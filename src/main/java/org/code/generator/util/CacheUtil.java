package org.code.generator.util;

import org.code.generator.constant.SystemKey;

import java.util.HashMap;
import java.util.Map;

public class CacheUtil {

    private static Map<String, String> map = null;
    private static CacheUtil instance = null;

    private CacheUtil() {
        map = new HashMap<>(16);
    }

    public static CacheUtil getInstance() {
        if (instance == null) {
            throw new RuntimeException("CacheUtil not init.");
        }
        return instance;
    }

    public static void init() {
        if (instance == null) {
            instance = new CacheUtil();
        }
    }

    public void put(String key, String value) {
        map.put(key, value);
    }

    public String get(String key) {
        return map.get(key);
    }

    public String get(SystemKey key) {
        return map.get(key.value());
    }

}
