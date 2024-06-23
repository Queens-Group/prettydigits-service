package shop.prettydigits.service.impl;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/23/2024 12:18 PM
@Last Modified 6/23/2024 12:18 PM
Version 1.0
*/

import com.midtrans.httpclient.error.MidtransError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.prettydigits.constant.order.OrderStatus;
import shop.prettydigits.dto.response.ApiResponse;
import shop.prettydigits.model.*;
import shop.prettydigits.repository.*;
import shop.prettydigits.service.OrderService;

import java.util.HashSet;
import java.util.List;
import java.util.function.Function;

@Service
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final MidtransService midtransService;
    private final AddressRepository addressRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(CartRepository cartRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository, MidtransService midtransService,
                            AddressRepository addressRepository,
                            CartItemRepository cartItemRepository,
                            ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.midtransService = midtransService;
        this.addressRepository = addressRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    @Modifying
    @Override
    public ApiResponse<Order> createOrder(Long userId, Integer addressId) throws MidtransError {
        Cart userCart = cartRepository.findByUserUserId(userId);

        if (userCart.getCartItems().isEmpty()) {
            return ApiResponse.<Order>builder()
                    .code(200)
                    .message("cart is empty")
                    .build();
        }

        Address shippingAddress = addressRepository.findById(addressId).orElse(null);

        if (shippingAddress == null) {
            return ApiResponse.<Order>builder()
                    .code(404)
                    .message("address is not found")
                    .build();
        }

        Function<CartItem, OrderItem> mapToOrderItem = item -> OrderItem.builder().product(item.getProduct()).build();
        List<OrderItem> orderItems = userCart.getCartItems()
                .stream()
                .map(mapToOrderItem)
                .toList();

        Order newOrder = new Order();
        newOrder.setGrandTotal(getGrossAmount(orderItems));
        newOrder.setShippingAddress(shippingAddress);
        newOrder.setUser(userCart.getUser());
        newOrder.setStatus(OrderStatus.UNPAID);
        orderRepository.save(newOrder);
        orderItemRepository.saveAllAndFlush(orderItems);
        newOrder.setOrderItems(new HashSet<>(orderItems));

        String snapToken = midtransService.createSnapToken(newOrder);
        newOrder.setSnapToken(snapToken);

        orderItems.forEach(item -> item.setOrder(newOrder));


        cartItemRepository.deleteAllInBatchByCartId(userCart.getId());
        List<Product> product = orderItems.stream().map(item -> {
            Product p = item.getProduct();
            p.setIsAvailable(false);
            return p;
        }).toList();
        productRepository.saveAllAndFlush(product);

        return ApiResponse.<Order>builder()
                .code(201)
                .message("success create order")
                .data(newOrder)
                .build();
    }

    @Override
    public ApiResponse<Object> updateOrderStatus(Long userId, String orderId, OrderStatus orderStatus) {
        return null;
    }

    @Override
    public ApiResponse<List<Order>> getUserOrderByStatus(Long userId, OrderStatus orderStatus) {
        return null;
    }

    private double getGrossAmount(List<OrderItem> orderItems) {
        double grandTotal = 0;

        for (OrderItem item : orderItems) {
            Product product = item.getProduct();
            grandTotal += product.getPrice();
        }

        return grandTotal;
    }
}
