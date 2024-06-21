package shop.prettydigits.repository;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/21/2024 8:19 PM
@Last Modified 6/21/2024 8:19 PM
Version 1.0
*/

import org.springframework.data.jpa.repository.JpaRepository;
import shop.prettydigits.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameOrPhone(String username, String phone);

    User findByUserId(Long userId);

    Optional<User> findFirstByUsernameOrPhoneAndEnabledTrue(String username, String phone);
}
