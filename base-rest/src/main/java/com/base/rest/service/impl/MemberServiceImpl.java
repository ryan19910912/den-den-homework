package com.base.rest.service.impl;

import com.base.common.bo.LoginRecordBo;
import com.base.common.bo.MemberBo;
import com.base.common.enums.VerificationCodeActionTypeEnum;
import com.base.common.repo.LoginRecordRepo;
import com.base.common.repo.MemberRepo;
import com.base.rest.handel.ApiException;
import com.base.rest.model.bo.LoginBo;
import com.base.rest.model.bo.RegisterBo;
import com.base.rest.service.BlacklistService;
import com.base.rest.service.MemberService;
import com.base.rest.service.VerificationCodeService;
import com.base.rest.utils.JwtUtils;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepo memberRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    private final BlacklistService blacklistService;
    private final VerificationCodeService verificationCodeService;
    private final LoginRecordRepo loginRecordRepo;

    @Override
    @Transactional
    public void register(RegisterBo registerBo) {

        verificationCodeService.validateVerificationCode(
            registerBo.getEmail(),
            VerificationCodeActionTypeEnum.REGISTER,
            registerBo.getVerificationCode()
        );

        if (!registerBo.getPassword().equals(registerBo.getConfirmPassword())) {
            throw new ApiException("密碼與確認密碼不符");
        }

        memberRepo.insertMember(
            MemberBo.builder()
                .email(registerBo.getEmail())
                .password(passwordEncoder.encode(registerBo.getPassword()))
                .createTime(Timestamp.from(Instant.now()))
                .build()
        );
    }

    @Override
    @Transactional
    public String login(LoginBo loginBo) {

        MemberBo memberBo =
            Optional.ofNullable(memberRepo.findMemberByEmail(loginBo.getEmail()))
                .orElseThrow(() -> new ApiException("帳號不存在"));

        verificationCodeService.validateVerificationCode(
            loginBo.getEmail(),
            VerificationCodeActionTypeEnum.LOGIN,
            loginBo.getVerificationCode()
        );

        if (!passwordEncoder.matches(loginBo.getPassword(), memberBo.getPassword())) {
            throw new ApiException("密碼錯誤");
        }

        // 新增登入紀錄
        loginRecordRepo.insertLoginRecord(
            LoginRecordBo.builder()
                .email(loginBo.getEmail())
                .lastLoginTime(Timestamp.from(Instant.now()))
                .build()
        );

        return jwtUtils.generateToken(loginBo.getEmail());
    }

    @Override
    public void logout(String token) {
        if (Objects.nonNull(token)) {
            blacklistService.add(token);
        }
    }

    @Override
    public Timestamp findMemberLastLoginTime(String email) {
        return Optional.ofNullable(loginRecordRepo.findLastLoginRecordByEmail(email))
            .map(LoginRecordBo::getLastLoginTime)
            .orElse(Timestamp.from(Instant.now()));
    }
}
