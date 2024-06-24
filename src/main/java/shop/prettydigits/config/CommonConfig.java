package shop.prettydigits.config;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/21/2024 7:39 PM
@Last Modified 6/21/2024 7:39 PM
Version 1.0
*/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shop.prettydigits.config.constant.AppConstant;

@Configuration
public class CommonConfig {


    @Bean(AppConstant.NO_AUTH_PATHS)
    public String[] noAuthPaths() {
        return new String[]{
                "/error**", "/swagger-ui/**", "/midtrans/notification",
                "/api/v1/auth/login", "/rest-api-docs/**",
                "/api/v1/auth/register", "/", "/api/v1/products/**"
        };
    }
}
