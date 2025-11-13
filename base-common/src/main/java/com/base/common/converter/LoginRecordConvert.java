package com.base.common.converter;

import com.base.common.bo.LoginRecordBo;
import com.base.common.entity.LoginRecordPo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginRecordConvert {

    LoginRecordPo toPo(LoginRecordBo bo);

    LoginRecordBo toBo(LoginRecordPo po);
}
