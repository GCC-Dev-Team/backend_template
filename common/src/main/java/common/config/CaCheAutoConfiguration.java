package common.config;


import common.utils.cache.CaCheContext;
import common.utils.cache.CaffeineUtil;
import common.utils.cache.RedisCache;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;



/**
 * springboot启动自动装配
 */
@Component
public class CaCheAutoConfiguration {

    static boolean useRedis=false;

    @Resource
    RedisCache redisCache;

    @Resource
    CaffeineUtil caffeineUtil;

    @Resource
    void setUseRedis(CacheProperties cacheProperties){

        if (cacheProperties != null) {
            CaCheAutoConfiguration.useRedis = cacheProperties.isUseRedis();
        }
    }
    @Bean
    CaCheContext getCaCheContext(){

        if (useRedis){

            return new CaCheContext(redisCache);

        }else {

            return new CaCheContext(caffeineUtil);

        }

    }


}
