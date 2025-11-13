package com.base.common.bo;

import com.base.common.enums.VerificationCodeActionTypeEnum;
import com.base.common.enums.VerificationCodeStateEnum;
import java.sql.Timestamp;
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
public class VerificationCodeBo {

    private Long id;

    /**
     * 用戶信箱
     */
    private String email;

    /**
     * 驗證碼
     */
    private String code;

    /**
     * 操作類型
     */
    private VerificationCodeActionTypeEnum actionType;

    /**
     * 狀態
     */
    private VerificationCodeStateEnum state;

    /**
     * 過期時間
     */
    private Timestamp expireTime;
}
