package shop.prettydigits.controller;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/21/2024 9:43 PM
@Last Modified 6/21/2024 9:43 PM
Version 1.0
*/

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.prettydigits.config.constant.Route;
import shop.prettydigits.dto.request.LoginRequest;
import shop.prettydigits.dto.request.RegisterRequest;
import shop.prettydigits.dto.response.ApiResponse;
import shop.prettydigits.dto.response.LoginResponse;
import shop.prettydigits.dto.response.RegisterResponse;
import shop.prettydigits.model.User;
import shop.prettydigits.service.AppUserService;

import java.security.Principal;

@RestController
@RequestMapping(Route.API_V1 + Route.AUTH)
public class AuthController {

    private final AppUserService appUserService;

    @Autowired
    public AuthController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping(Route.LOGIN)
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest request) {
        ApiResponse<LoginResponse> response = appUserService.login(request);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PostMapping(Route.REGISTER)
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@RequestBody @Valid RegisterRequest request) {
        ApiResponse<RegisterResponse> response = appUserService.registerUser(request);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping(Route.USER_INFO)
    @SecurityRequirement(name = "bearerJWT")
    public ResponseEntity<ApiResponse<User>> getCurrentUserInfo(Principal principal) {
        ApiResponse<User> response = appUserService.getCurrentUserInfo(principal);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
