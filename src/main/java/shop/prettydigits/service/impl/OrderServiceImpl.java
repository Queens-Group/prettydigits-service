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
import shop.prettydigits.config.properties.AppProperties;
import shop.prettydigits.constant.order.OrderStatus;
import shop.prettydigits.dto.response.ApiResponse;
import shop.prettydigits.dto.response.CheckOrderValidity;
import shop.prettydigits.model.*;
import shop.prettydigits.repository.*;
import shop.prettydigits.service.OrderService;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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

    private final AppProperties appProperties;

    @Autowired
    public OrderServiceImpl(CartRepository cartRepository,
                            OrderRepository orderRepository,
                            OrderItemRepository orderItemRepository,
                            MidtransService midtransService,
                            AddressRepository addressRepository,
                            CartItemRepository cartItemRepository,
                            ProductRepository productRepository,
                            AppProperties appProperties) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.midtransService = midtransService;
        this.addressRepository = addressRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.appProperties = appProperties;
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
        newOrder.setExpiredAt(ZonedDateTime.now(ZoneId.systemDefault()).plusMinutes(appProperties.getMAX_ORDER_EXPIRED()));

        orderRepository.save(newOrder);
        orderItemRepository.saveAllAndFlush(orderItems);
        newOrder.setOrderItems(new HashSet<>(orderItems));

        String snapToken = midtransService.createSnapToken(newOrder, shippingAddress);
        newOrder.setSnapToken(snapToken);

        orderItems.forEach(item -> item.setOrder(newOrder));


        cartItemRepository.deleteAllInBatchByCartId(userCart.getId());
        List<Product> products = updateProductAvailability(orderItems, false);
        productRepository.saveAllAndFlush(products);

        return ApiResponse.<Order>builder()
                .code(201)
                .message("success create order")
                .data(newOrder)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Override
    public ApiResponse<CheckOrderValidity> checkOrderBeforePayment(Long userId, String orderId) {
        Optional<Order> order = orderRepository.findByIdAndUserUserId(orderId, userId);
        CheckOrderValidity orderValidity = new CheckOrderValidity();
        if (order.isEmpty()) {
            orderValidity.setReadyForPayment(false);
            return ApiResponse.<CheckOrderValidity>builder()
                    .code(200)
                    .message("order does not exist")
                    .data(orderValidity)
                    .build();
        }
        ZonedDateTime expiredDatetime = order.get().getExpiredAt().withZoneSameInstant(ZoneId.systemDefault());
        boolean isExpired = expiredDatetime.isBefore(ZonedDateTime.now(ZoneId.systemDefault()));

        OrderStatus status = order.get().getStatus();
        boolean isReadyForPayment = !isExpired && status.equals(OrderStatus.UNPAID);
        orderValidity.setReadyForPayment(isReadyForPayment);


        if (isExpired && status.equals(OrderStatus.UNPAID)) {
            Order expiredOrder = order.get();
            expiredOrder.setStatus(OrderStatus.CANCELLED);
            List<Product> products = updateProductAvailability(new ArrayList<>(expiredOrder.getOrderItems()), true);
            productRepository.saveAllAndFlush(products);
        }
        return ApiResponse.<CheckOrderValidity>builder()
                .code(200)
                .message("success check order expiry time")
                .data(orderValidity)
                .build();
    }


    private List<Product> updateProductAvailability(List<OrderItem> orderItems, boolean isAvailable) {
        return orderItems.stream().map(item -> {
            Product p = item.getProduct();
            p.setIsAvailable(isAvailable);
            return p;
        }).toList();

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
