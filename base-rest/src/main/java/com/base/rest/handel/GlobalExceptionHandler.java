package com.base.rest.handel;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 業務異常
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Void>> handleApiException(ApiException ex) {
        // 返回 HTTP 500 Internal Server Error
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.error(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage()
            ));
    }

    // Bean Validation 異常 (例如 @NotBlank, @Email 驗證失敗)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        BindingResult result = ex.getBindingResult();

        // **只提取第一個驗證失敗的訊息 (通常只顯示一條錯誤即可)**
        String errorMessage = result.getFieldErrors().stream()
            .findFirst() // 找到第一個錯誤
            .map(DefaultMessageSourceResolvable::getDefaultMessage) // 獲取錯誤訊息
            .orElse("參數驗證失敗"); // 如果找不到錯誤訊息，給一個預設值

        // 返回 HTTP 400 Bad Request
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.error(
                HttpStatus.BAD_REQUEST.value(),
                errorMessage
            ));
    }

    // 其他未處理的異常 (兜底處理)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGlobalException(Exception ex) {
        // 返回 HTTP 500 Internal Server Error
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.error(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage()
            ));
    }
}
