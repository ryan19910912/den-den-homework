package com.base.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.base.common.enums.VerificationCodeActionTypeEnum;
import com.base.common.enums.VerificationCodeStateEnum;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("verification_code")
public class VerificationCodePo {

    @TableId(type = IdType.AUTO)
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
