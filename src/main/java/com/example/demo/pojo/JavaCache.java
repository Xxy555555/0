package com.example.demo.pojo;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import org.checkerframework.checker.index.qual.NonNegative;

import java.util.concurrent.TimeUnit;
public class JavaCache {
    // 创建缓存，支持自定义过期时间
         private static final    Cache<String, String> cache = Caffeine.newBuilder()
                    .expireAfter(new Expiry<String, String>() {
                        // 设置写入后的过期时间
                        @Override
                        public long expireAfterCreate(String key, String value, long currentTime) {
                            if (key.equals("code")) {
                                return TimeUnit.MINUTES.toNanos(5); // 5 分后过期
                            } else if (key.equals("token")) {
                                return TimeUnit.MINUTES.toNanos(30); // 30分后过期
                            } else {
                                return TimeUnit.SECONDS.toNanos(20); // 20 秒后过期
                            }
                        }

                        // 设置更新后的过期时间
                        @Override
                        public long expireAfterUpdate(String key, String value, long currentTime, @NonNegative long currentDuration) {
                            return currentDuration; // 保持原有过期时间
                        }

                        // 设置读取后的过期时间
                        @Override
                        public long expireAfterRead(String key, String value, long currentTime, @NonNegative long currentDuration) {
                            return currentDuration; // 保持原有过期时间
                        }
                    })
                    .build();
    public static void put(String key, String value) {
        cache.put(key, value);
    }

    public static String get(String key) {
        return cache.getIfPresent(key);
    }




}
