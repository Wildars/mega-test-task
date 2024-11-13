package kg.project.megatest.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi publicUserApi() {
        return GroupedOpenApi.builder()
                .group("api")
                .packagesToScan("kg.project.megatest.controller")
                .build();
    }

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .addServersItem(new Server().url("http://localhost:8081"))  // локалка
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info()
                        .title("Mega API")
                        .version("1.0")
                        .description("API для Mega"));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .name("Authorization")
                .in(SecurityScheme.In.HEADER)
                .description("Bearer_[token]");
    }

}
