package com.base.rest.controller;

import com.base.common.enums.VerificationCodeActionTypeEnum;
import com.base.rest.converter.AuthConvert;
import com.base.rest.handel.ApiResponse;
import com.base.rest.model.req.LoginReq;
import com.base.rest.model.req.RegisterReq;
import com.base.rest.model.req.SendVerificationCodeReq;
import com.base.rest.model.resp.LoginResp;
import com.base.rest.service.MemberService;
import com.base.rest.service.VerificationCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "授權相關服務", description = "提供授權相關 Api")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AuthController {

    private final VerificationCodeService verificationCodeService;
    private final MemberService memberService;
    private final AuthConvert authConvert;

    @Operation(
        summary = "發送註冊驗證碼",
        description = "根據用戶提供的 Email，發送一次性註冊驗證碼。",
        tags = {"授權相關服務"}
    )
    @PostMapping("/send/register/verification/code")
    public ApiResponse<Void> sendRegisterVerificationCode(
        @Valid @RequestBody SendVerificationCodeReq req
    ) {
        verificationCodeService.sendVerificationCode(
            req.getEmail(), VerificationCodeActionTypeEnum.REGISTER
        );
        return ApiResponse.success();
    }

    @Operation(
        summary = "註冊",
        description = "根據用戶提供的 Email、密碼、確認密碼以及驗證碼，完成註冊。",
        tags = {"授權相關服務"}
    )
    @PostMapping("/register")
    public ApiResponse<Void> register(
        @Valid @RequestBody RegisterReq req
    ) {
        memberService.register(authConvert.toBo(req));
        return ApiResponse.success();
    }

    @Operation(
        summary = "發送登入驗證碼",
        description = "根據用戶提供的 Email，發送一次性登入驗證碼。",
        tags = {"授權相關服務"}
    )
    @PostMapping("/send/login/verification/code")
    public ApiResponse<Void> sendLoginVerificationCode(
        @Valid @RequestBody SendVerificationCodeReq req
    ) {
        verificationCodeService.sendVerificationCode(
            req.getEmail(), VerificationCodeActionTypeEnum.LOGIN
        );
        return ApiResponse.success();
    }

    @Operation(
        summary = "登入",
        description = "根據用戶提供的 Email、密碼以及驗證碼，完成登入。",
        tags = {"授權相關服務"}
    )
    @PostMapping("/login")
    public ApiResponse<LoginResp> login(
        @Valid @RequestBody LoginReq req
    ) {
        return ApiResponse.success(
            LoginResp.builder()
                .token(memberService.login(authConvert.toBo(req)))
                .build()
        );
    }

    @Operation(
        summary = "登出",
        description = "用戶登出。",
        tags = {"授權相關服務"}
    )
    @SecurityRequirements({
        // 選項 1: 帶入 JWT Token（如果用戶想將當前 Token 加入黑名單）
        @SecurityRequirement(name = "BearerAuth"),
        // 選項 2: 允許不帶任何認證資訊（Token 已過期或用戶未登入）
        @SecurityRequirement(name = "")
    })
    @PostMapping("/logout")
    public ApiResponse<Void> logout(
        @AuthenticationPrincipal String jwtToken
    ) {
        // 將 Token 加入黑名單
        memberService.logout(jwtToken);
        return ApiResponse.success();
    }
}
