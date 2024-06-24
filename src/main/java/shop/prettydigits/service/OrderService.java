package shop.prettydigits.service;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/23/2024 11:14 AM
@Last Modified 6/23/2024 11:14 AM
Version 1.0
*/

import com.midtrans.httpclient.error.MidtransError;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import shop.prettydigits.constant.order.OrderStatus;
import shop.prettydigits.dto.response.ApiResponse;
import shop.prettydigits.dto.response.CheckOrderValidity;
import shop.prettydigits.dto.response.OrderResponse;
import shop.prettydigits.dto.response.PatchResponse;
import shop.prettydigits.model.Order;

public interface OrderService {

    ApiResponse<Order> createOrder(Long userId, Integer addressId) throws MidtransError;

    ApiResponse<CheckOrderValidity> checkOrderBeforePayment(Long userId, String orderId);

    ApiResponse<PatchResponse> updateOrderStatus(String adminUsername, String orderId, OrderStatus orderStatus);

    ApiResponse<PagedModel<OrderResponse>> getUserOrderByStatus(Long userId, OrderStatus orderStatus, Pageable pageable);
    ApiResponse<PagedModel<OrderResponse>> getOrderByStatus(OrderStatus orderStatus, Pageable pageable);
    ApiResponse<PagedModel<OrderResponse>> getAllOrders(Long userId, Pageable pageable);
    ApiResponse<PagedModel<OrderResponse>> getAllOrders(Pageable pageable);


}
