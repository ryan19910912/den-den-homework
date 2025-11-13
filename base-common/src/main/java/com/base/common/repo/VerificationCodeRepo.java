package com.base.common.repo;

import com.base.common.bo.VerificationCodeBo;
import com.base.common.enums.VerificationCodeActionTypeEnum;

public interface VerificationCodeRepo {

    /**
     * 新增驗證碼資訊
     *
     * @param verificationCodeBo 驗證碼資訊
     * @return 是否成功
     */
    boolean insertVerificationCode(VerificationCodeBo verificationCodeBo);

    /**
     * 更新驗證碼資訊
     *
     * @param verificationCodeBo 驗證碼資訊
     * @return 是否成功
     */
    boolean updateVerificationCode(VerificationCodeBo verificationCodeBo);

    /**
     * 取得驗證碼資訊 by email
     *
     * @param email      用戶 email
     * @param actionType 操作類型
     * @return 驗證碼資訊
     */
    VerificationCodeBo findVerificationCodeByEmailAndActionType(
        String email,
        VerificationCodeActionTypeEnum actionType,
        String verificationCode
    );
}
