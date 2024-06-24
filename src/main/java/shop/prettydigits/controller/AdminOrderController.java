package shop.prettydigits.controller;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/24/2024 12:57 PM
@Last Modified 6/24/2024 12:57 PM
Version 1.0
*/

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.prettydigits.config.constant.Route;
import shop.prettydigits.constant.order.OrderStatus;
import shop.prettydigits.constant.role.RoleType;
import shop.prettydigits.dto.response.ApiResponse;
import shop.prettydigits.dto.response.OrderResponse;
import shop.prettydigits.service.OrderService;

@RestController
@RequestMapping(Route.API_V1 + Route.ADMIN + Route.ORDERS)
@RolesAllowed({RoleType.ADMIN})
@SecurityRequirement(name = "bearerJWT")
public class AdminOrderController {

    private final OrderService orderService;

    @Autowired
    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<PagedModel<OrderResponse>>> getOrders(@RequestParam(value = "userId", required = false) Long userId,
                                                                            @RequestParam(value = "status", required = false) OrderStatus orderStatus,
                                                                            @ParameterObject Pageable pageable) {
        ApiResponse<PagedModel<OrderResponse>> response;


        if (orderStatus == null && userId != null) {
            response = orderService.getAllOrders(userId, pageable);
        } else if (orderStatus != null && userId != null) {
            response = orderService.getUserOrderByStatus(userId, orderStatus, pageable);
        } else if (orderStatus == null) {
            response = orderService.getAllOrders(pageable);
        } else  {
            response = orderService.getOrderByStatus(orderStatus, pageable);
        }

        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
