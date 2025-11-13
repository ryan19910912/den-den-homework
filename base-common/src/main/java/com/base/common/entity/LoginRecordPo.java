package com.base.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@TableName("login_record")
public class LoginRecordPo {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用戶信箱
     */
    private String email;

    /**
     * 用戶最後登入時間
     */
    private Timestamp lastLoginTime;
}
