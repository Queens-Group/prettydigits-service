package shop.prettydigits.controller;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/23/2024 3:53 PM
@Last Modified 6/23/2024 3:53 PM
Version 1.0
*/

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.prettydigits.config.MidtransConfig;
import shop.prettydigits.config.constant.Route;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(Route.MIDTRANS)
@Slf4j
public class MidtransWebhook {


    private final MidtransConfig midtransConfig;

    @Autowired
    public MidtransWebhook(MidtransConfig midtransConfig) {
        this.midtransConfig = midtransConfig;
    }



    //SHA512(order_id+status_code+gross_amount+ServerKey)
    @PostMapping(Route.NOTIFICATION)
    public ResponseEntity<Map<String, Object>> handleNotification(@RequestBody Map<String, Object> payload) {
        String orderId = (String) payload.get("order_id");
        String statusCode = (String) payload.get("status_code");
        String grossAmount = (String) payload.get("gross_amount");
        String serverKey = midtransConfig.getServerKey();
        log.info("SHA512 {}", Sha512DigestUtils.shaHex(orderId + statusCode + grossAmount + serverKey));
        log.info("MID {}", payload.get("signature_key"));
        log.info("{}", payload);
        return ResponseEntity.ok(new HashMap<>());
    }
}
