package shop.prettydigits.dto.response;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/21/2024 8:30 PM
@Last Modified 6/21/2024 8:30 PM
Version 1.0
*/

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private Integer code;
    private String message;
    private T data;



    public static <U> ApiResponse<U> setSuccess() {
        return ApiResponse.<U>builder()
                .code(200)
                .message("Success")
                .build();
    }

    public  static ApiResponse<Void> setSuccess(String message) {
        return ApiResponse.<Void>builder()
                .code(200)
                .message(message)
                .build();
    }

    @JsonIgnore
    public HttpStatus getHttpStatus() {
        return HttpStatus.valueOf(this.code);
    }
}
