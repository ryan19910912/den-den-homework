package com.base.rest.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用戶發送驗證碼 Request")
public class SendVerificationCodeReq {

    @NotBlank(message = "Email 不能為空")
    @Email(message = "Email 格式不正確")
    @Schema(description = "帳號 Email (必須是有效格式)", example = "test.user@example.com")
    private String email;
}
