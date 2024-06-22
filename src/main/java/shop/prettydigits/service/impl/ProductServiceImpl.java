package shop.prettydigits.service.impl;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/22/2024 11:37 AM
@Last Modified 6/22/2024 11:37 AM
Version 1.0
*/

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.prettydigits.dto.request.ProductRequest;
import shop.prettydigits.dto.response.ApiResponse;
import shop.prettydigits.model.Product;
import shop.prettydigits.repository.CartItemRepository;
import shop.prettydigits.repository.ProductRepository;
import shop.prettydigits.service.ProductService;
import shop.prettydigits.utils.AuthUtils;

import java.security.Principal;
import java.util.Optional;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CartItemRepository cartItemRepository;

    private final ObjectMapper mapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CartItemRepository cartItemRepository, ObjectMapper mapper) {
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public ApiResponse<Product> createProduct(Principal principal, ProductRequest request) {
        String adminName = AuthUtils.getCurrentUsername(principal.getName());
        Product product = mapper.convertValue(request, Product.class);
        product.setModifiedBy(adminName);

        productRepository.save(product);
        return ApiResponse.<Product>builder()
                .code(201)
                .message("success create product")
                .data(product)
                .build();
    }

    @Transactional
    @Modifying
    @Override
    public ApiResponse<Void> deleteProduct(Principal principal, Integer productId) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            return ApiResponse.<Void>builder()
                    .code(404)
                    .message("product is not present in database")
                    .build();
        }
        product.get().setIsAvailable(false);
        product.get().setModifiedBy(AuthUtils.getCurrentUsername(principal.getName()));


        productRepository.save(product.get());
        cartItemRepository.deleteAllInBatchByProduct_id(productId);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("success delete product")
                .build();
    }

    @Override
    public ApiResponse<PagedModel<Product>> getAllAvailableProduct(Pageable pageable) {
        Page<Product> page = productRepository.findAllByIsAvailableTrue(pageable);


        return ApiResponse.<PagedModel<Product>>builder()
                .code(200)
                .data(new PagedModel<>(page))
                .message("success get available products")
                .build();
    }
}
