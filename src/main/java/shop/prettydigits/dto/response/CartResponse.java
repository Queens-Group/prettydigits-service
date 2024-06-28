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

import java.time.ZonedDateTime;
import java.util.Set;

@Data
public class CartResponse {

    private Integer cartId;


    private Set<CartItemResponse> cartItems;


    @JsonProperty("id")
    public void setId(Integer id) {
        this.cartId = id;
    }

    @JsonProperty("id")
    public Integer getId(Integer id) {
        return this.cartId;
    }

    private ZonedDateTime createdAt;


    private ZonedDateTime updatedAt;

    private Double totalPrice;
}

