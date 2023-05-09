package com.sysmap.parrot.services.security;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        security = {
                @SecurityRequirement(name = "RequestedBy"),
                @SecurityRequirement(name = "JWT")
        }
)

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("JWT", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .name("Authorization")
                                .in(SecurityScheme.In.HEADER)
                                .description("Insira o token JWT com o Bearer. Por exemplo: `Bearer abcde12345 `."))
                        .addSecuritySchemes("RequestedBy", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .name("RequestedBy")
                                .in(SecurityScheme.In.HEADER)
                                .description("Insira o userId")))
                .info(new Info()
                        .title("Rede social Parrot - Back-end")
                        .description("Documentação SWAGGER do projeto final do Programa de Trainee SysMap de Excelência Full Stack | 3ª edição | Java")
                        .version("1.0.0"));
    }
}