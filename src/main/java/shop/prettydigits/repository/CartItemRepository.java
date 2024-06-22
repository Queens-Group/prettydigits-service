package shop.prettydigits.repository;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/22/2024 1:55 PM
@Last Modified 6/22/2024 1:55 PM
Version 1.0
*/

import org.springframework.data.jpa.repository.JpaRepository;
import shop.prettydigits.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    int deleteAllInBatchByProduct_id(Integer productId);

}
