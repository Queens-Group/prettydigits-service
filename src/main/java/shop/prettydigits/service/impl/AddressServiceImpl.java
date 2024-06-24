package shop.prettydigits.service.impl;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/22/2024 1:33 AM
@Last Modified 6/22/2024 1:33 AM
Version 1.0
*/

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.prettydigits.dto.response.ApiResponse;
import shop.prettydigits.model.Address;
import shop.prettydigits.model.User;
import shop.prettydigits.repository.AddressRepository;
import shop.prettydigits.repository.UserRepository;
import shop.prettydigits.service.AddressService;

import java.util.List;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    private final UserRepository userRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public ApiResponse<Address> saveAddress(Address address, Long userId) {
        User user = userRepository.findByUserId(userId);
        address.setUser(user);
        addressRepository.save(address);
        return ApiResponse.<Address>builder()
                .code(201)
                .message("success added new address")
                .data(address)
                .build();
    }

    @Transactional
    @Override
    public ApiResponse<Boolean> deleteAddressById(Integer addressId, Long userId) {
        int deleted = addressRepository.deleteByIdAndUserUserId(addressId, userId);
        return ApiResponse.<Boolean>builder()
                .code(200)
                .message("success delete address")
                .data(deleted > 0)
                .build();
    }

    @Override
    public ApiResponse<List<Address>> getAddressByUserId(Long userId) {
        return ApiResponse.<List<Address>>builder()
                .code(200)
                .message("success get user's addresses")
                .data(addressRepository.findAllByUserUserId(userId))
                .build();
    }
}
