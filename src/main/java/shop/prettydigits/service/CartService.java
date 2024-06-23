package shop.prettydigits.service;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/22/2024 1:41 PM
@Last Modified 6/22/2024 1:41 PM
Version 1.0
*/

import shop.prettydigits.dto.request.CartItemDTO;
import shop.prettydigits.dto.response.ApiResponse;
import shop.prettydigits.model.Cart;

import java.util.concurrent.ExecutionException;

public interface CartService {

    ApiResponse<Boolean> addItemToCart(Long userId, CartItemDTO cartItem) throws ExecutionException, InterruptedException;

    ApiResponse<Cart> getUserCart(Long userId);

    ApiResponse<Boolean> removeCartItem(Long userId, Integer cartId, Integer itemId);
}
