package shop.prettydigits.dto.request;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/21/2024 9:34 PM
@Last Modified 6/21/2024 9:34 PM
Version 1.0
*/

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "username or phone must not be blank")
    private String username;

    @NotBlank(message = "password must not be blank")
    @Size(min = 6)
    private String password;
}
