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

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class CommonConfig {

    private final KeyPair keyPair;

    public CommonConfig() throws NoSuchAlgorithmException {
        this.keyPair = keyPairGenerator();
    }

    @Bean(AppConstant.NO_AUTH_PATHS)
    public String[] noAuthPaths() {
        return new String[]{

        };
    }

    @Bean(AppConstant.PUBLIC_KEY)
    public RSAPublicKey rsaPublicKey() {
        return (RSAPublicKey) this.keyPair.getPublic();
    }

    @Bean(AppConstant.PRIVATE_KEY)
    public RSAPrivateKey rsaPrivateKey() {
        return (RSAPrivateKey) this.keyPair.getPrivate();
    }


    private KeyPair keyPairGenerator() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("rsa");
        generator.initialize(2048);
        return generator.generateKeyPair();
    }
}
