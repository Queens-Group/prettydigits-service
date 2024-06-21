package shop.prettydigits.service;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/21/2024 9:49 PM
@Last Modified 6/21/2024 9:49 PM
Version 1.0
*/

import shop.prettydigits.dto.request.LoginRequest;
import shop.prettydigits.dto.request.RegisterRequest;
import shop.prettydigits.dto.response.ApiResponse;
import shop.prettydigits.dto.response.LoginResponse;
import shop.prettydigits.dto.response.RegisterResponse;
import shop.prettydigits.model.User;

import java.security.Principal;

public interface AppUserService {

    ApiResponse<LoginResponse> login(LoginRequest loginRequest);

    ApiResponse<RegisterResponse> registerUser(RegisterRequest request);

    ApiResponse<User> getCurrentUserInfo(Principal principal);
}
