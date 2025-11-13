package com.base.rest.utils;

import com.base.rest.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtUtils {

    private final String secret;

    private final long expiration;

    private final String issuer;

    private SecretKey signingKey;

    public JwtUtils(JwtProperties jwtProperties) {
        this.secret = jwtProperties.getSecret();
        this.expiration = jwtProperties.getExpiration();
        this.issuer = jwtProperties.getIssuer();
    }

    /**
     * 初始化，將 Base64 編碼的密鑰轉換為 SecretKey 對象。
     */
    @PostConstruct
    public void init() {
        // 解碼 Base64 密鑰並用於 HS256 簽名
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
        log.info("JWT Signing Key initialized successfully.");
    }

    /**
     * 生成 JWT Token
     */
    public String generateToken(String email) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
            .setSubject(email) // Email
            .setIssuer(issuer) // 發行者
            .setIssuedAt(now) // 頒發時間
            .setExpiration(expiryDate) // 過期時間
            .signWith(signingKey, SignatureAlgorithm.HS256) // 簽名算法和密鑰
            .compact();
    }

    /**
     * 從 JWT Token 獲取用戶Email
     */
    public String getEmailFromToken(String token) {

        Claims claims = Jwts.parserBuilder()
            .setSigningKey(signingKey)
            .build()
            .parseClaimsJws(token)
            .getBody();

        return claims.getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        try {
            Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token) // 這會驗證簽名和檢查是否過期
                .getBody();

            return claims.getExpiration();

        } catch (ExpiredJwtException e) {
            // 處理 Token 已過期，但仍需要其過期時間的情況
            // ExpiredJwtException 物件本身包含了已解析的 Claims
            return e.getClaims().getExpiration();

        } catch (Exception e) {
            // 處理其他異常，如簽名無效 (SignatureException) 或格式錯誤 (MalformedJwtException)
            // 對於無效 Token，您可以選擇拋出RuntimeException或返回 null
            log.error("Token 解析失敗: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 驗證 JWT Token 是否有效 (簽名和過期)
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("無效的 JWT 簽名: {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("無效的 JWT Token 格式: {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("JWT Token 已過期: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("不支持的 JWT 格式: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT 字符串為空: {}", ex.getMessage());
        }
        return false;
    }
}
