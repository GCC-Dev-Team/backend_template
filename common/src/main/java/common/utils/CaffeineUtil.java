package common.utils;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;

import com.github.benmanes.caffeine.cache.Cache;
import common.config.CommonCaffeineCacheConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Caffeine 键值缓存工具
 * @author I Nhrl
 */
@SuppressWarnings("unused")
@Component
public class CaffeineUtil {

    static Cache<String, CommonCaffeineCacheConfig.Value> cache;

    static void setCache(Cache<String, CommonCaffeineCacheConfig.Value> cache) {
        CaffeineUtil.cache = cache;
    }

    @Autowired
    public void setCacheConfig(@Qualifier("commonCaffeineCache") Cache<String, CommonCaffeineCacheConfig.Value> cache) {
        CaffeineUtil.setCache(cache);
    }


    /**
     * 添加或更新缓存
     *
     * @param key   键
     * @param value 值
     */
    public static void put(String key, Object value) {
        cache.put(key, CommonCaffeineCacheConfig.Value.builder()
                .writeTime(System.currentTimeMillis()).cacheValue(value).build());
    }

    /**
     * 添加或更新缓存
     *
     * @param key            键
     * @param value          值
     * @param expiryDuration 到期时间 单位(秒)
     */
    public static void put(String key, Object value, Long expiryDuration) {
        cache.put(key, CommonCaffeineCacheConfig.Value.builder().writeTime(System.currentTimeMillis())
                .expiryDuration(expiryDuration).cacheValue(value).build());
    }

    /**
     * 获取键缓存
     *
     * @param key 键
     * @return CommonCaffeineCacheConfig.Value
     */
    public static CommonCaffeineCacheConfig.Value get(String key) {
        if (key == null) {
            return null;
        }
        return cache.asMap().get(key);
    }


    /**
     * 获取键缓存
     *
     * @param key 键
     * @return T
     */
    public static <T> T get(String key, TypeReference<T> type) {
        CommonCaffeineCacheConfig.Value value = get(key);
        return value == null ? null : Convert.convert(type, value.getCacheValue());
    }

    /**
     * 获取键缓存到期时间
     *
     * @param key 键
     * @return 键的到期时间 单位(毫秒)
     */
    public static Long getExpire(String key) {
        CommonCaffeineCacheConfig.Value value = get(key);
        return value == null ? null : value.getWriteTime() + (value.getExpiryDuration() * 1000);
    }

    /**
     * 根据key删除缓存
     *
     * @param keys 键
     */
    public static void del(String... keys) {
        for (String key : keys) {
            cache.asMap().remove(key);
        }
    }
}
