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
import shop.prettydigits.model.Address;
import shop.prettydigits.model.User;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Data
public class CartResponse {

    private Integer cartId;


    private Set<CartItemResponse> cartItems;

    private List<Address> addresses;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private Double totalPrice;

    @JsonProperty("id")
    public void setId(Integer id) {
        this.cartId = id;
    }

    @JsonProperty("id")
    public Integer getId(Integer id) {
        return this.cartId;
    }

    @JsonProperty("user")
    public void setUser(User user) {
        this.addresses = Optional.ofNullable(user)
                .map(u -> new ArrayList<>(u.getAddresses()))
                .orElse(new ArrayList<>());
    }

}

