package shop.prettydigits.service.impl;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 5/28/2024 6:39 PM
@Last Modified 5/28/2024 6:39 PM
Version 1.0
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shop.prettydigits.repository.UserRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameOrPhone(username, username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("%s doesn't exist", username)));
    }


}
