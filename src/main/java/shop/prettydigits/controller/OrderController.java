package shop.prettydigits.controller;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/23/2024 2:28 PM
@Last Modified 6/23/2024 2:28 PM
Version 1.0
*/

import com.midtrans.httpclient.error.MidtransError;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.prettydigits.config.constant.Route;
import shop.prettydigits.dto.response.ApiResponse;
import shop.prettydigits.dto.response.CheckOrderValidity;
import shop.prettydigits.model.Order;
import shop.prettydigits.service.OrderService;
import shop.prettydigits.utils.AuthUtils;

import java.security.Principal;

@RestController
@RequestMapping(Route.API_V1 + Route.ORDERS)
@SecurityRequirement(name = "bearerJWT")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(Route.CHECKOUT)
    public ResponseEntity<ApiResponse<Order>> checkout(Principal principal, @RequestParam Integer addressId) throws MidtransError {
        Long userId = AuthUtils.getCurrentUserId(principal);
        ApiResponse<Order> response = orderService.createOrder(userId, addressId);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping(Route.CHECK_EXPIRY + Route.ORDER_ID)
    public ResponseEntity<ApiResponse<CheckOrderValidity>> checkOrderExpiry(Principal principal, @PathVariable("orderId") String orderId) {
        Long userId = AuthUtils.getCurrentUserId(principal);
        ApiResponse<CheckOrderValidity> response = orderService.checkOrderBeforePayment(userId, orderId);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
