package com.base.rest.service;

import com.base.common.bo.MemberBo;
import com.base.rest.model.bo.LoginBo;
import com.base.rest.model.bo.RegisterBo;
import java.sql.Timestamp;

public interface MemberService {

    /**
     * 用戶註冊
     *
     * @param registerBo 用戶註冊請求資訊
     */
    void register(RegisterBo registerBo);

    /**
     * 用戶登入
     *
     * @param loginBo 用戶登入資訊
     */
    String login(LoginBo loginBo);

    /**
     * 用戶登出
     *
     * @param email 用戶信箱
     */
    void logout(String email);

    /**
     * 取得用戶最後登入時間
     */
    Timestamp findMemberLastLoginTime(String email);
}
