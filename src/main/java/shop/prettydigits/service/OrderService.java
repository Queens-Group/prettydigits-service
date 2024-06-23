package shop.prettydigits.service;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/23/2024 11:14 AM
@Last Modified 6/23/2024 11:14 AM
Version 1.0
*/

import shop.prettydigits.constant.order.OrderStatus;
import shop.prettydigits.dto.response.ApiResponse;
import shop.prettydigits.model.Order;

import java.util.List;

public interface OrderService {

    ApiResponse<Order> createOrder(Long userId, Integer cartId);

    ApiResponse<Object> updateOrderStatus(Long userId, String orderId, OrderStatus orderStatus);

    ApiResponse<List<Order>> getUserOrderByStatus(Long userId, OrderStatus orderStatus);

}
