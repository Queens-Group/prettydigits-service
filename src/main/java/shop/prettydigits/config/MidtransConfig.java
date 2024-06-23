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
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties("midtrans")
public class MidtransConfig {


    private String snapURL;

    private String clientKey;

    private String serverKey;

    private boolean isProduction;



    @Bean
    public Config getSnapConfig() {
        return Config.builder()
                .setServerKey(serverKey)
                .setClientKey(clientKey)
                .setIsProduction(isProduction)
                .build();
    }

    @Bean
    public MidtransSnapApi getSnapAPI() {
        return new ConfigFactory(getSnapConfig()).getSnapApi();
    }

}
