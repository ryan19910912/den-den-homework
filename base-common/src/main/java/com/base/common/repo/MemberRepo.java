package com.base.common.repo;

import com.base.common.bo.MemberBo;

public interface MemberRepo {

    /**
     * 取得用戶資料 by email
     *
     * @param email 用戶信箱
     * @return 用戶資訊
     */
    MemberBo findMemberByEmail(String email);

    /**
     * 新增用戶資料
     *
     * @param memberBo 用戶資訊
     * @return 是否成功
     */
    boolean insertMember(MemberBo memberBo);

    /**
     * 更新用戶資料
     *
     * @param memberBo 用戶資訊
     * @return 是否成功
     */
    boolean updateMember(MemberBo memberBo);
}
