package shop.prettydigits.dto.response;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/24/2024 1:38 PM
@Last Modified 6/24/2024 1:38 PM
Version 1.0
*/

import lombok.Data;

@Data
public class PatchResponse {

    private int affectedRows;
    private boolean success;
}
