package com.fourcamp.NutriPlan.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationSwagger {
    @Bean
    public OpenAPI configOpenApi(){
        return new OpenAPI().info(
                new Info().description("Definição da Api da plataforma NutriPlan")
                        .version("1.0.0")
                        .title("Validações Api")
        );
    }
}
