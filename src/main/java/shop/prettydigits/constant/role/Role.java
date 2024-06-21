package shop.prettydigits.constant.role;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/21/2024 8:24 PM
@Last Modified 6/21/2024 8:24 PM
Version 1.0
*/

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    USER, ADMIN;

    @JsonCreator
    public static Role forRole(String role) {
        for (Role x : Role.values()) {
            if (x.name().equalsIgnoreCase(role)) {
                return x;
            }
        }
        return null;
    }
}
