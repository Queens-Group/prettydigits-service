package shop.prettydigits.config.properties;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/23/2024 7:48 PM
@Last Modified 6/23/2024 7:48 PM
Version 1.0
*/

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("app")
@Setter
@Getter
public class AppProperties {


    private String MIDTRANS_CLIENT_KEY;
    private String MIDTRANS_SERVER_KEY;
    private boolean MIDTRANS_IS_PRODUCTION;
    private String MIDTRANS_SNAP_URL;

    private long MIDTRANS_EXPIRY_DURATION = 5;
    private String MIDTRANS_EXPIRY_UNIT = "MINUTES";

    private long JWT_TOKEN_AGE = 3600;

    private String JWT_ISSUER = "shop.pretty-digits";

    private long MAX_ORDER_EXPIRED = 10;

}
