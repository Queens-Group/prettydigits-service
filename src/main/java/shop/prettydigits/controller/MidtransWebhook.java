package shop.prettydigits.controller;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/23/2024 3:53 PM
@Last Modified 6/23/2024 3:53 PM
Version 1.0
*/

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.prettydigits.config.constant.Route;
import shop.prettydigits.dto.response.ApiResponse;
import shop.prettydigits.service.impl.MidtransService;

import java.util.Map;

@RestController
@RequestMapping(Route.MIDTRANS)
@Slf4j
public class MidtransWebhook {


    private final MidtransService midtransService;

    @Autowired
    public MidtransWebhook(MidtransService midtransService) {
        this.midtransService = midtransService;
    }

    @PostMapping(Route.NOTIFICATION)
    public ResponseEntity<ApiResponse<Void>> handleNotification(@RequestBody Map<String, Object> payload) {
        log.info("[MIDTRANS NOTIFICATION PAYLOAD] {}", payload);
        JSONObject jsonPayload = new JSONObject(payload);
        ApiResponse<Void> response = midtransService.handleNotification(jsonPayload);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
