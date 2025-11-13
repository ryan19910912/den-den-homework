package com.base.common.repo.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.base.common.converter.MemberConvert;
import com.base.common.entity.MemberPo;
import com.base.common.mapper.MemberMapper;
import com.base.common.bo.MemberBo;
import com.base.common.repo.MemberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberRepoImpl implements MemberRepo {

    private final MemberMapper memberMapper;
    private final MemberConvert memberConvert;

    @Override
    public MemberBo findMemberByEmail(String email) {

        LambdaQueryWrapper<MemberPo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemberPo::getEmail, email);

        return memberConvert.toBo(memberMapper.selectOne(wrapper));
    }

    @Override
    public boolean insertMember(MemberBo memberBo) {
        return memberMapper.insert(memberConvert.toPo(memberBo)) > 0;
    }

    @Override
    public boolean updateMember(MemberBo memberBo) {
        return memberMapper.updateById(memberConvert.toPo(memberBo)) > 0;
    }
}
