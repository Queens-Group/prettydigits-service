package shop.prettydigits.repository;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/23/2024 12:22 PM
@Last Modified 6/23/2024 12:22 PM
Version 1.0
*/

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.prettydigits.constant.order.OrderStatus;
import shop.prettydigits.model.Order;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {

    Optional<Order> findByIdAndUserUserId(String orderId, Long userId);

    Page<Order> findByUserUserIdAndStatus(Long userId, OrderStatus orderStatus, Pageable pageable);

    Page<Order> findByStatus(OrderStatus status, Pageable pageable);


    Page<Order> findByUserUserId(Long userId, Pageable pageable);


}
