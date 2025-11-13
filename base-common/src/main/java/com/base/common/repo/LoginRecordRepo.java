package com.base.common.repo;

import com.base.common.bo.LoginRecordBo;

public interface LoginRecordRepo {

    /**
     * 新增用戶登入紀錄
     *
     * @param loginRecordBo 驗證碼資訊
     * @return 是否成功
     */
    boolean insertLoginRecord(LoginRecordBo loginRecordBo);

    /**
     * 取得用戶最後登入資訊 by email
     *
     * @param email 用戶 email
     * @return 驗證碼資訊
     */
    LoginRecordBo findLastLoginRecordByEmail(String email);
}
