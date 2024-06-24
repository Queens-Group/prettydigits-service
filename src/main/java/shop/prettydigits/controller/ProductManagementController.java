package shop.prettydigits.controller;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/22/2024 11:48 AM
@Last Modified 6/22/2024 11:48 AM
Version 1.0
*/

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.prettydigits.config.constant.Route;
import shop.prettydigits.constant.role.RoleType;
import shop.prettydigits.dto.request.ProductRequest;
import shop.prettydigits.dto.response.ApiResponse;
import shop.prettydigits.model.Product;
import shop.prettydigits.service.ProductService;
import shop.prettydigits.utils.AuthUtils;

import java.security.Principal;

@RestController
@RequestMapping(Route.API_V1 + Route.ADMIN_PRODUCTS)
@RolesAllowed({RoleType.ADMIN})
@SecurityRequirement(name = "bearerJWT")
public class ProductManagementController {

    private final ProductService productService;

    @Autowired
    public ProductManagementController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = Route.NEW_PRODUCT)
    public ResponseEntity<ApiResponse<Product>> createProduct(Principal principal, @RequestBody @Valid ProductRequest request) {
        ApiResponse<Product> response = productService.createProduct(AuthUtils.getCurrentUsername(principal.getName()), request);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping(value = Route.PRODUCT_ID_VAR + Route.REMOVE)
    public ResponseEntity<ApiResponse<Void>> removeProduct(Principal principal, @PathVariable Integer productId) {
        ApiResponse<Void> response = productService.deleteProduct(AuthUtils.getCurrentUsername(principal.getName()), productId);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
