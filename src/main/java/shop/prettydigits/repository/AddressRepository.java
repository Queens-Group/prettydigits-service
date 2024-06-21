package shop.prettydigits.repository;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/22/2024 1:32 AM
@Last Modified 6/22/2024 1:32 AM
Version 1.0
*/

import org.springframework.data.jpa.repository.JpaRepository;
import shop.prettydigits.model.Address;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    int deleteByIdAndUser_userId(Integer addressId, Long userId);

    List<Address> findAllByUser_userId(Long userId);
}
