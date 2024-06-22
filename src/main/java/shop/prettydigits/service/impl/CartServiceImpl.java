package shop.prettydigits.service.impl;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/22/2024 1:56 PM
@Last Modified 6/22/2024 1:56 PM
Version 1.0
*/

import com.fasterxml.jackson.databind.ObjectMapper;
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
import shop.prettydigits.utils.AuthUtils;

import java.security.Principal;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final ProductRepository productRepository;

    private final ObjectMapper mapper;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository, ObjectMapper mapper) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public ApiResponse<Boolean> addItemToCart(Principal principal, CartItemDTO cartItemDTO) throws ExecutionException, InterruptedException {
        Long userId = AuthUtils.getCurrentUserId(principal);
        CompletableFuture<Cart> userCart = CompletableFuture.supplyAsync(() -> cartRepository.findByUser_userId(userId));
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
    public ApiResponse<Cart> getUserCart(Principal principal) {
        Long userId = AuthUtils.getCurrentUserId(principal);
        Cart cart = cartRepository.findByUser_userId(userId);
        return ApiResponse.<Cart>builder()
                .code(200)
                .message("success get user cart")
                .data(cart)
                .build();
    }
}
