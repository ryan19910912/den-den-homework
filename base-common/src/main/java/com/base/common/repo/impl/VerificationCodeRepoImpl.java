package com.base.common.repo.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.base.common.bo.VerificationCodeBo;
import com.base.common.converter.VerificationCodeConvert;
import com.base.common.entity.VerificationCodePo;
import com.base.common.enums.VerificationCodeActionTypeEnum;
import com.base.common.enums.VerificationCodeStateEnum;
import com.base.common.mapper.VerificationCodeMapper;
import com.base.common.repo.VerificationCodeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VerificationCodeRepoImpl implements VerificationCodeRepo {

    private final VerificationCodeMapper verificationCodeMapper;
    private final VerificationCodeConvert verificationCodeConvert;

    @Override
    @Transactional
    public boolean insertVerificationCode(VerificationCodeBo verificationCodeBo) {

        // 新增之前先把以前的驗證碼都變成 USED
        LambdaUpdateWrapper<VerificationCodePo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(VerificationCodePo::getState, VerificationCodeStateEnum.USED);
        wrapper.eq(VerificationCodePo::getEmail, verificationCodeBo.getEmail());
        wrapper.eq(VerificationCodePo::getActionType, verificationCodeBo.getActionType());
        wrapper.eq(VerificationCodePo::getState, VerificationCodeStateEnum.ACTIVE);

        verificationCodeMapper.update(wrapper);

        return verificationCodeMapper.insert(verificationCodeConvert.toPo(verificationCodeBo)) > 0;
    }

    @Override
    public boolean updateVerificationCode(VerificationCodeBo verificationCodeBo) {
        return verificationCodeMapper.updateById(verificationCodeConvert.toPo(verificationCodeBo)) > 0;
    }

    @Override
    public VerificationCodeBo findVerificationCodeByEmailAndActionType(
        String email,
        VerificationCodeActionTypeEnum actionType,
        String verificationCode
    ) {

        LambdaQueryWrapper<VerificationCodePo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VerificationCodePo::getEmail, email);
        wrapper.eq(VerificationCodePo::getActionType, actionType);
        wrapper.eq(VerificationCodePo::getCode, verificationCode);
        wrapper.eq(VerificationCodePo::getState, VerificationCodeStateEnum.ACTIVE);
        wrapper.orderByDesc(VerificationCodePo::getId);

        return verificationCodeConvert.toBo(verificationCodeMapper.selectOne(wrapper));
    }
}
