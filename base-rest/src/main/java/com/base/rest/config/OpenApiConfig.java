package com.base.rest.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "BearerAuth"; // 自定義的安全機制名稱

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
            .info(new Info().title("Ryan DenDen Homework").version("1.0"))
            .components(new Components()
                .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                    .name("Authorization")            // Header 的名稱，即 "Authorization"
                    .type(SecurityScheme.Type.HTTP)   // 安全類型為 HTTP (常用於 JWT)
                    .scheme("bearer")                 // 使用 bearer scheme (Bearer Token)
                    .bearerFormat("JWT")              // 格式描述為 JWT (可選)
                    .in(SecurityScheme.In.HEADER)     // Token 位於 Header 中
                )
            );
    }
}
