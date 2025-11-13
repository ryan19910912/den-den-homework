package com.base.rest.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtProperties {

    /**
     * Jwt加解密私鑰
     */
    private String secret;

    /**
     * 過期時間(毫秒)
     */
    private Long expiration;

    /**
     * 發行者
     */
    private String issuer;
}
