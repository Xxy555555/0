package com.example.demo.util;

import java.util.Map;

public class ThreadLocalUtil {
    private static ThreadLocal<Map<String, Object>> LOCAL = new ThreadLocal<>();

    public static Map<String, Object> get() {
        return LOCAL.get();

    }

    public static void set(Map<String, Object> value) {
        LOCAL.set(value);
    }
    public static void remove() {
        LOCAL.remove();
    }
}
