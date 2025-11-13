package com.base.common.converter;

import com.base.common.entity.VerificationCodePo;
import com.base.common.bo.VerificationCodeBo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VerificationCodeConvert {

    VerificationCodePo toPo(VerificationCodeBo bo);

    VerificationCodeBo toBo(VerificationCodePo po);
}
