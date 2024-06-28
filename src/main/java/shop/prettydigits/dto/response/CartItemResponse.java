package shop.prettydigits.dto.response;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/28/2024 3:14 PM
@Last Modified 6/28/2024 3:14 PM
Version 1.0
*/

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import shop.prettydigits.model.Product;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Data
public class CartItemResponse {

    private Integer id;


    private Integer productId;


    private String simCardNumber;


    private String productDescription;

    private Boolean isProductAvailable;


    private String productType;


    private Double price;


    private String validityPeriod;

    @JsonProperty("product")
    public void setProduct(Product product) {
        this.productId = product.getId();
        this.simCardNumber = product.getNumber();
        this.productDescription = product.getDescription();
        this.isProductAvailable = product.getIsAvailable();
        this.productType = product.getProvider();
        this.price = product.getPrice();
        this.validityPeriod = Optional.ofNullable(product.getValidityPeriod())
                .map(localDate -> localDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")))
                .orElse("No Expiration");
    }
}
