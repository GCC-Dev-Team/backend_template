package common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存加载参数
 */
@Data
@Configuration
public class CacheProperties {

    @Value("${cache.useRedis}")
    private boolean useRedis;



}
