package common.request.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.web.cors.CorsUtils;

/**
 * 使用security的防火墙功能，但不使用security的认证授权登录
 * 其实就是配置了所有请求都不拦截,然后使用的是过滤链
 * @author I Nhrl
 */
@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class WebSecurityConfigurerAdapter {
  //配置springsecurity的放行路径等信息
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        //对所有请求按照以下约定进行拦截和放行
        http.authorizeHttpRequests(
            		//requestMatchers 指定匹配路径
            		//permitAll 让security跳过之前通过requestMatchers匹配到的路径，
                auth -> auth
               .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                       .requestMatchers("/**").permitAll()
//                        .anyRequest().permitAll()

            		//anyRequest 指定除requestMatchers匹配路径之外的其他路径
            		//authenticated 让anyRequest匹配到的所有路径都通过security校验
//                        .anyRequest().authenticated()
        );

        //关闭 防止客户端的 csrf（跨站伪造） 攻击行为 的能力
        // 从security过滤器链中撤出 CsrfFilter
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
