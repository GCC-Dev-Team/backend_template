package common.config;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ID生成器-Snowflake
 * @author I Nhrl
 */
@Configuration
public class SnowflakeIdConfig {
    @Bean
    @ConditionalOnMissingBean
    public SnowflakeGenerator snowflakeGenerator() {
        return new SnowflakeGenerator();
    }
}
