package shop.prettydigits.repository;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/22/2024 11:30 AM
@Last Modified 6/22/2024 11:30 AM
Version 1.0
*/

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.prettydigits.model.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findAllByIsAvailableTrue(Pageable pageable);

    Optional<Product> findByIdAndIsAvailableTrue(Integer productId);
}
