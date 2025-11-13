package com.base.common.bo;

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
public class MemberBo {

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
