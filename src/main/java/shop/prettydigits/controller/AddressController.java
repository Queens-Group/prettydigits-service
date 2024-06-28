package shop.prettydigits.controller;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/22/2024 1:43 AM
@Last Modified 6/22/2024 1:43 AM
Version 1.0
*/

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.prettydigits.config.constant.Route;
import shop.prettydigits.dto.response.ApiResponse;
import shop.prettydigits.model.Address;
import shop.prettydigits.service.AddressService;
import shop.prettydigits.utils.AuthUtils;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(Route.API_V1 + Route.ADDRESS)
@SecurityRequirement(name = "bearerJWT")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<Address>> createAddress(Principal principal, @RequestBody @Valid Address address) {
        long userId = AuthUtils.getCurrentUserId(principal);
        ApiResponse<Address> response = addressService.saveAddress(address, userId);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<Address>>> getUserAddresses(Principal principal) {
        long userId = AuthUtils.getCurrentUserId(principal);
        ApiResponse<List<Address>> response = addressService.getAddressByUserId(userId);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteAddress(Principal principal, @PathVariable Integer addressId) {
        long userId = AuthUtils.getCurrentUserId(principal);
        ApiResponse<Boolean> response = addressService.deleteAddressById(addressId, userId);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PatchMapping("/{addressId}")
    public ResponseEntity<ApiResponse<Object>> setDefaultAddress(Principal principal, @PathVariable Integer addressId) {
        long userId = AuthUtils.getCurrentUserId(principal);
        ApiResponse<Object> response = addressService.setDefaultAddress(addressId, userId);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
