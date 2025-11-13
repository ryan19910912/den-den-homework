package com.base.rest.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用戶註冊 Request")
public class RegisterReq {

    @NotBlank(message = "Email 不能為空")
    @Email(message = "Email 格式不正確")
    @Schema(description = "帳號 Email (必須是有效格式)", example = "test.user@example.com")
    private String email;

    @NotBlank(message = "密碼不能為空")
    @Size(min = 8, max = 30, message = "密碼長度必須在 8 到 30 個字符之間")
    @Schema(description = "密碼 (將被 sha256 加密儲存)", example = "ryanHsu.123")
    @ToString.Exclude // toString() 不印
    private String password;

    @NotBlank(message = "確認密碼不能為空")
    @Size(min = 8, max = 30, message = "確認密碼長度必須在 8 到 30 個字符之間")
    @Schema(description = "密碼 (將被 sha256 加密儲存)", example = "ryanHsu.123")
    @ToString.Exclude // toString() 不印
    private String confirmPassword;

    @NotBlank(message = "驗證碼不能為空")
    @Schema(description = "驗證碼", example = "123456")
    @ToString.Exclude // toString() 不印
    private String verificationCode;
}
