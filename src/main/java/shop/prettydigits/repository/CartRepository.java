package shop.prettydigits.repository;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/22/2024 1:50 PM
@Last Modified 6/22/2024 1:50 PM
Version 1.0
*/

import org.springframework.data.jpa.repository.JpaRepository;
import shop.prettydigits.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    Cart findByUser_userId(Long userId);
}
