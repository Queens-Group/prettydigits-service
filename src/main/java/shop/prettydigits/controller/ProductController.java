package shop.prettydigits.controller;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/22/2024 1:02 PM
@Last Modified 6/22/2024 1:02 PM
Version 1.0
*/

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.prettydigits.config.constant.Route;
import shop.prettydigits.dto.response.ApiResponse;
import shop.prettydigits.model.Product;
import shop.prettydigits.service.ProductService;

@RestController
@RequestMapping(Route.API_V1 + Route.PRODUCTS)
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = Route.AVAILABLE)
    public ResponseEntity<ApiResponse<PagedModel<Product>>> getAllAvailableProducts(@ParameterObject Pageable pageable) {
        ApiResponse<PagedModel<Product>> response = productService.getAllAvailableProduct(pageable);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
