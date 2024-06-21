package shop.prettydigits.dto.response;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/21/2024 9:47 PM
@Last Modified 6/21/2024 9:47 PM
Version 1.0
*/

import lombok.Data;
import shop.prettydigits.constant.role.Role;

@Data
public class LoginResponse {

    private String username;
    private String accessToken;
    private Role role;
    private Boolean enabled;
}
