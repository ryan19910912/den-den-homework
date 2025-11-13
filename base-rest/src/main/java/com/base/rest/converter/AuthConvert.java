package com.base.rest.converter;

import com.base.rest.model.bo.LoginBo;
import com.base.rest.model.bo.RegisterBo;
import com.base.rest.model.req.LoginReq;
import com.base.rest.model.req.RegisterReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthConvert {

    RegisterBo toBo(RegisterReq request);

    LoginBo toBo(LoginReq request);
}
