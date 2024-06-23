package shop.prettydigits.service.impl;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/22/2024 1:56 PM
@Last Modified 6/22/2024 1:56 PM
Version 1.0
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.prettydigits.dto.request.CartItemDTO;
import shop.prettydigits.dto.response.ApiResponse;
import shop.prettydigits.model.Cart;
import shop.prettydigits.model.CartItem;
import shop.prettydigits.model.Product;
import shop.prettydigits.repository.CartItemRepository;
import shop.prettydigits.repository.CartRepository;
import shop.prettydigits.repository.ProductRepository;
import shop.prettydigits.service.CartService;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final ProductRepository productRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    @Override
    public ApiResponse<Boolean> addItemToCart(Long userId, CartItemDTO cartItemDTO) throws ExecutionException, InterruptedException {
        CompletableFuture<Cart> userCart = CompletableFuture.supplyAsync(() -> cartRepository.findByUserUserId(userId));
        CompletableFuture<Optional<Product>> product = CompletableFuture.supplyAsync(() -> productRepository.findByIdAndIsAvailableTrue(cartItemDTO.getProductId()));

        CompletableFuture.allOf(userCart, product).join();

        if (product.get().isEmpty()) {
            return ApiResponse.<Boolean>builder()
                    .code(404)
                    .message("failed added to cart, product does not exist or not available")
                    .data(false)
                    .build();
        }


        CartItem cartItem = new CartItem();
        cartItem.setCart(userCart.get());
        product.get().ifPresent(cartItem::setProduct);
        cartItemRepository.save(cartItem);


        return ApiResponse.<Boolean>builder()
                .code(200)
                .message("success add item to cart")
                .data(true)
                .build();
    }

    @Override
    public ApiResponse<Cart> getUserCart(Long userId) {
        Cart cart = cartRepository.findByUserUserId(userId);
        return ApiResponse.<Cart>builder()
                .code(200)
                .message("success get user cart")
                .data(cart)
                .build();
    }

    @Transactional
    @Override
    public ApiResponse<Boolean> removeCartItem(Long userId, Integer cartId, Integer itemId) {
        int affectedRow = cartItemRepository.deleteByIdAndCartIdAndCartUserUserId(itemId, cartId, userId);
        return ApiResponse.<Boolean>builder()
                .code(200)
                .message("success remove item from cart")
                .data(affectedRow > 0)
                .build();
    }
}
