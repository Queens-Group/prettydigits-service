package shop.prettydigits.config;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/23/2024 1:00 PM
@Last Modified 6/23/2024 1:00 PM
Version 1.0
*/

import com.midtrans.Config;
import com.midtrans.ConfigFactory;
import com.midtrans.service.MidtransSnapApi;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shop.prettydigits.config.properties.AppProperties;

@Configuration
@Getter
@Setter
public class MidtransConfig {


    private final AppProperties appProperties;

    @Autowired
    public MidtransConfig(AppProperties appProperties) {
        this.appProperties = appProperties;
    }


    @Bean
    public Config getSnapConfig() {
        return Config.builder()
                .setServerKey(appProperties.getMIDTRANS_SERVER_KEY())
                .setClientKey(appProperties.getMIDTRANS_CLIENT_KEY())
                .setIsProduction(appProperties.isMIDTRANS_IS_PRODUCTION())
                .build();
    }

    @Bean
    public MidtransSnapApi getSnapAPI() {
        return new ConfigFactory(getSnapConfig()).getSnapApi();
    }

}
