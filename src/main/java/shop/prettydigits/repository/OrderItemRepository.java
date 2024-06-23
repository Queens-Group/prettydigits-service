package shop.prettydigits.repository;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/23/2024 12:22 PM
@Last Modified 6/23/2024 12:22 PM
Version 1.0
*/

import org.springframework.data.jpa.repository.JpaRepository;
import shop.prettydigits.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
