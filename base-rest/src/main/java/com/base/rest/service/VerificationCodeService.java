package com.base.rest.service;

import com.base.common.enums.VerificationCodeActionTypeEnum;

public interface VerificationCodeService {

    /**
     * 發送驗證碼
     *
     * @param email      用戶信箱
     * @param actionType 操作類型
     */
    void sendVerificationCode(
        String email,
        VerificationCodeActionTypeEnum actionType
    );

    /**
     * 校驗驗證碼
     *
     * @param email            用戶信箱
     * @param actionType       操作類型
     * @param verificationCode 驗證碼
     */
    void validateVerificationCode(
        String email,
        VerificationCodeActionTypeEnum actionType,
        String verificationCode
    );
}
