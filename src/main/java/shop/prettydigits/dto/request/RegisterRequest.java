package shop.prettydigits.dto.request;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/21/2024 9:34 PM
@Last Modified 6/21/2024 9:34 PM
Version 1.0
*/

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import shop.prettydigits.constant.role.Role;

@Data
public class RegisterRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String phone;

    @NotBlank
    private String fullName;

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotNull
    private Role role;
}
