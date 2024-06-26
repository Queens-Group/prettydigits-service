package shop.prettydigits.controller;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/22/2024 2:07 PM
@Last Modified 6/22/2024 2:07 PM
Version 1.0
*/

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.prettydigits.config.constant.Route;
import shop.prettydigits.dto.request.CartItemDTO;
import shop.prettydigits.dto.response.ApiResponse;
import shop.prettydigits.dto.response.CartResponse;
import shop.prettydigits.service.CartService;
import shop.prettydigits.utils.AuthUtils;

import java.security.Principal;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(Route.API_V1 + Route.CART)
@SecurityRequirement(name = "bearerJWT")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping(value = Route.ADD_ITEM)
    public ResponseEntity<ApiResponse<Boolean>> addItemToCart(Principal principal, @RequestBody @Valid CartItemDTO cartItemDTO) throws ExecutionException, InterruptedException {
        ApiResponse<Boolean> response = cartService.addItemToCart(AuthUtils.getCurrentUserId(principal), cartItemDTO);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<CartResponse>> getUserCart(Principal principal) {
        ApiResponse<CartResponse> response = cartService.getUserCart(AuthUtils.getCurrentUserId(principal));
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping(Route.CART_ID + Route.ITEM + Route.ITEM_ID)
    public ResponseEntity<ApiResponse<Boolean>> removeItem(Principal principal, @PathVariable("cartId") Integer cartId, @PathVariable("itemId") Integer itemId) {
        ApiResponse<Boolean> response = cartService.removeCartItem(AuthUtils.getCurrentUserId(principal), cartId, itemId);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
