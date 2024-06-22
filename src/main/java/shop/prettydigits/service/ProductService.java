package shop.prettydigits.service;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/22/2024 11:31 AM
@Last Modified 6/22/2024 11:31 AM
Version 1.0
*/

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import shop.prettydigits.dto.request.ProductRequest;
import shop.prettydigits.dto.response.ApiResponse;
import shop.prettydigits.model.Product;

import java.security.Principal;

public interface ProductService {

    ApiResponse<Product> createProduct(Principal principal, ProductRequest request);

    ApiResponse<Void> deleteProduct(Principal principal, Integer productId);

    ApiResponse<PagedModel<Product>> getAllAvailableProduct(Pageable pageable);
}
