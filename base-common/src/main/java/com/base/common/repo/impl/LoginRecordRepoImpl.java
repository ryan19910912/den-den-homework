package com.base.common.repo.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.base.common.bo.LoginRecordBo;
import com.base.common.converter.LoginRecordConvert;
import com.base.common.entity.LoginRecordPo;
import com.base.common.mapper.LoginRecordMapper;
import com.base.common.repo.LoginRecordRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginRecordRepoImpl implements LoginRecordRepo {

    private final LoginRecordMapper loginRecordMapper;
    private final LoginRecordConvert loginRecordConvert;

    @Override
    public boolean insertLoginRecord(LoginRecordBo loginRecordBo) {
        return loginRecordMapper.insert(loginRecordConvert.toPo(loginRecordBo)) > 0;
    }

    @Override
    public LoginRecordBo findLastLoginRecordByEmail(String email) {

        LambdaQueryWrapper<LoginRecordPo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LoginRecordPo::getEmail, email);
        wrapper.orderByDesc(LoginRecordPo::getId);
        wrapper.last("LIMIT 1");

        return loginRecordConvert.toBo(loginRecordMapper.selectOne(wrapper));
    }
}
