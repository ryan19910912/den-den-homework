package com.base.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@TableName("member")
public class MemberPo {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用戶信箱
     */
    private String email;

    /**
     * 用戶密碼(hash)
     */
    private String password;

    /**
     * 創建時間
     */
    private Timestamp createTime;
}
