package common.request.config;


import common.request.context.BaseUserCommon;
import common.request.context.UserHolderTypeBase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author I Nhrl
 */
@Configuration
public class DefaultConfig {

    @Bean
    @ConditionalOnMissingBean(UserHolderTypeBase.class)
    public UserHolderTypeBase defaultUserHolderType(){
        return new UserHolderTypeBase(BaseUserCommon.class){};
    }
}
