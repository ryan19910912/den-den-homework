package com.base.rest.config;

import com.base.rest.service.BlacklistService;
import com.base.rest.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final BlacklistService blacklistService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // 檢查並取得 Token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.replace("Bearer ", "");

            try {
                // 驗證 Token
                if (!jwtUtils.validateToken(token)) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
                    return;
                }

                // 解析 Email
                String email = jwtUtils.getEmailFromToken(token);

                // 檢查黑名單
                if (blacklistService.isBlacklisted(email)) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token blacklisted");
                    return;
                }

                // 檢查 Security Context 中是否已經有認證資訊
                if (SecurityContextHolder.getContext().getAuthentication() == null) {

                    // 建立認證物件 (包含用戶資料和權限)
                    UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                            token,
                            null,
                            Collections.emptyList()
                        );

                    // 設定到 Security Context
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            } catch (Exception e) {
                // 處理所有 JWT 相關的錯誤 (驗證失敗、解析失敗、用戶找不到等)
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token or user not found");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
