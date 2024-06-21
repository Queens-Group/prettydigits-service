package shop.prettydigits.controller;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/21/2024 11:18 PM
@Last Modified 6/21/2024 11:18 PM
Version 1.0
*/

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.prettydigits.constant.role.RoleType;

@RestController
@RequestMapping("/admin")
@RolesAllowed({RoleType.ADMIN})
@SecurityRequirement(name = "bearerJWT")
public class AdminController {

    @GetMapping("")
    public String test() {
        return "test";
    }
}
