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
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import shop.prettydigits.constant.role.Role;

@Data
public class RegisterRequest {

    @NotBlank(message = "username should not be blank")
    private String username;

    @NotBlank(message = "phone should not be blank")
    @Pattern(message = "phone should be numerical characters", regexp = "^\\d+$")
    private String phone;

    @NotBlank(message = "fullname should not be blank")
    private String fullName;

    @NotBlank(message = "password should be blank")
    @Size(message = "password should be 6 characters long at minimum", min = 6)
    private String password;

    @NotNull(message = "role should not be null or blank")
    private Role role;
}
