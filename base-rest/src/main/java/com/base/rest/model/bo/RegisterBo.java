package com.base.rest.model.bo;

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
public class RegisterBo {

    /**
     * 用戶信箱
     */
    private String email;

    /**
     * 密碼
     */
    private String password;

    /**
     * 確認密碼
     */
    private String confirmPassword;

    /**
     * 驗證碼
     */
    private String verificationCode;

    /**
     * 驗證 密碼與確認密碼是否一致
     */
    public boolean validateSamePassword() {
        return password.equals(confirmPassword);
    }
}
