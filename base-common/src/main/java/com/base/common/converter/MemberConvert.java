package com.base.common.converter;

import com.base.common.entity.MemberPo;
import com.base.common.bo.MemberBo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberConvert {

    MemberBo toBo(MemberPo po);

    MemberPo toPo(MemberBo bo);
}
