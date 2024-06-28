package shop.prettydigits.service;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/22/2024 1:29 AM
@Last Modified 6/22/2024 1:29 AM
Version 1.0
*/

import shop.prettydigits.dto.response.ApiResponse;
import shop.prettydigits.model.Address;

import java.util.List;

public interface AddressService {

    ApiResponse<Address> saveAddress(Address address, Long userId);
    ApiResponse<Boolean> deleteAddressById(Integer addressId, Long userId);
    ApiResponse<List<Address>> getAddressByUserId(Long userId);

    ApiResponse<Object> setDefaultAddress(Integer addressId, Long userId);
}
