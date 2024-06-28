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
import shop.prettydigits.dto.response.CartResponse;
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

    private final ObjectMapper mapper;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository,
                           CartItemRepository cartItemRepository,
                           ProductRepository productRepository,
                           ObjectMapper mapper) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public ApiResponse<Boolean> addItemToCart(Long userId, CartItemDTO cartItemDTO) throws ExecutionException, InterruptedException {
        CompletableFuture<Cart> userCart = CompletableFuture.supplyAsync(() -> cartRepository.findByUserUserId(userId));
        CompletableFuture<Optional<Product>> product = CompletableFuture.supplyAsync(() -> productRepository.findByIdAndIsAvailableTrue(cartItemDTO.getProductId()));
        CompletableFuture<Boolean> checkDuplicateProduct = CompletableFuture.supplyAsync(() -> cartItemRepository.existsByProductId(cartItemDTO.getProductId()));

        CompletableFuture.allOf(userCart, product, checkDuplicateProduct).join();

        if (product.get().isEmpty()) {
            return ApiResponse.<Boolean>builder()
                    .code(404)
                    .message("failed added to cart, product does not exist or not available")
                    .data(false)
                    .build();
        }

        if (Boolean.TRUE.equals(checkDuplicateProduct.get())) {
            return ApiResponse.<Boolean>builder()
                    .code(409)
                    .message("product already in cart")
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
    public ApiResponse<CartResponse> getUserCart(Long userId) {
        Cart cart = cartRepository.findByUserUserId(userId);
        double totalPrice = cart.getCartItems()
                .stream()
                .mapToDouble(item -> item.getProduct().getPrice())
                .sum();
        CartResponse cartResponse = mapper.convertValue(cart, CartResponse.class);
        cartResponse.setTotalPrice(totalPrice);
        return ApiResponse.<CartResponse>builder()
                .code(200)
                .message("success get user cart")
                .data(cartResponse)
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
