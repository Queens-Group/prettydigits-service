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
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.prettydigits.constant.order.OrderStatus;
import shop.prettydigits.dto.request.UpdateOrderStatusReq;
import shop.prettydigits.model.Order;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {

    Optional<Order> findByIdAndUserUserId(String orderId, Long userId);

    Page<Order> findByUserUserIdAndStatus(Long userId, OrderStatus orderStatus, Pageable pageable);

    Page<Order> findByStatus(OrderStatus status, Pageable pageable);


    Page<Order> findByUserUserId(Long userId, Pageable pageable);

    @Modifying(flushAutomatically = true)
    @Query(value = "UPDATE Order o SET o.status = :#{#params.status}, o.modifiedBy = :#{#params.adminUsername}, o.updatedAt = :#{#params.updatedAt} WHERE o.id = :#{#params.orderId}")
    int updateOrderStatusByOrderId(@Param("params") UpdateOrderStatusReq params);


}
