package shop.prettydigits.dto.response;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/21/2024 9:50 PM
@Last Modified 6/21/2024 9:50 PM
Version 1.0
*/

import lombok.Data;

@Data
public class RegisterResponse {

    private String username;
    private String fullName;
    private String phone;
}
