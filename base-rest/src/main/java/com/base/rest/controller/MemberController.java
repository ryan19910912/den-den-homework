package com.base.rest.controller;

import com.base.rest.handel.ApiResponse;
import com.base.rest.model.resp.GetMemberLastLoginTimeResp;
import com.base.rest.service.MemberService;
import com.base.rest.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "用戶相關服務", description = "提供用戶相關服務 Api")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Validated
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final JwtUtils jwtUtils;

    @Operation(
        summary = "取得用戶最後登入時間",
        description = "取得用戶最後登入時間",
        tags = {"用戶相關服務"}
    )
    @SecurityRequirement(name = "BearerAuth") // 明確要求 JWT 認證
    @GetMapping("/lastLoginTime")
    public ApiResponse<GetMemberLastLoginTimeResp> getMemberLastLoginTime(
        @AuthenticationPrincipal String jwtToken
    ) {
        return ApiResponse.success(
            GetMemberLastLoginTimeResp.builder()
                .lastLoginTime(
                    memberService.findMemberLastLoginTime(
                        jwtUtils.getEmailFromToken(jwtToken)
                    )
                ).build()
        );
    }
}
