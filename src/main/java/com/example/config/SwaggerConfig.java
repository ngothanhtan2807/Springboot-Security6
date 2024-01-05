package com.example.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.SpringDocUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;

@ConditionalOnProperty(name = "springdoc.api-docs.enabled", matchIfMissing = true)
@Configuration
public class SwaggerConfig {
    @Value("${spring.application.name:no}")
    private String applicationName;

    @Value("${spring.profiles.active:local}")
    private String activeProfiles;

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(getApiInfo())
                .addSecurityItem(new SecurityRequirement().addList("bearerToken"))
                .components(getComponents())
                ;

    }

    private Info getApiInfo() {
        return new Info()
                .title(applicationName + " API - " + activeProfiles)
                ;
    }

    private Components getComponents() {
        return new Components()
                .addSecuritySchemes("bearerToken",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                )
                ;
    }

    static {
        SpringDocUtils.getConfig().replaceWithSchema(LocalTime.class, new StringSchema()
                .pattern("^\\d{2}:\\d{2}:\\d{2}$")
                .example("13:55:59"));
        SpringDocUtils.getConfig().replaceWithSchema(LocalDateTime.class, new StringSchema()
                .pattern("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$")
                .example("2022-08-01 13:55:59"));
        SpringDocUtils.getConfig().replaceWithSchema(OffsetDateTime.class, new StringSchema()
                .pattern("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}(\\+\\d{1,2}(\\:)?(\\d{2})?|Z)$")
                .example("2022-08-01 13:55:59+09:00"));
    }
}