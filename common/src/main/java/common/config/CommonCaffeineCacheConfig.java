package common.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import lombok.Builder;
import lombok.Data;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author I Nhrl
 * 本地高速缓存使用
 */
@Configuration
public class CommonCaffeineCacheConfig {

    @Bean
    public Cache<String, Value> commonCaffeineCache() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfter(new Expiry<String, Value>() {
                    @Override
                    public long expireAfterCreate(@NonNull String key, @NonNull Value value, long currentTime) {
                        return value.getExpiryDuration()==null?Long.MAX_VALUE:TimeUnit.SECONDS.toNanos(value.getExpiryDuration());
                    }

                    @Override
                    public long expireAfterUpdate(@NonNull String key, @NonNull Value value, long currentTime, @NonNegative long currentDuration) {
                        return value.getExpiryDuration()==null?Long.MAX_VALUE:TimeUnit.SECONDS.toNanos(value.getExpiryDuration());
                    }

                    @Override
                    public long expireAfterRead(@NonNull String key, @NonNull Value value, long currentTime, @NonNegative long currentDuration) {
                        return currentDuration;
                    }
                })
                // 初始的缓存空间大小
                .initialCapacity(100)
                // 缓存的最大条数
                .maximumSize(100000)
                .build();
    }

    @Data
    @Builder
    public static class Value {
        /**
         * 写入时间(毫秒)
         */
        private Long writeTime;
        /**
         * 有效期单位(秒)
         */
        private Long expiryDuration;
        /**
         * 缓存内容
         */
        private Object cacheValue;
    }
}
