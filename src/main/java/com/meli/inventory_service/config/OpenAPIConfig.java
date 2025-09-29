package com.meli.inventory_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Inventory Service API")
                        .description("API para gestión de inventario con reservas optimistas")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("MELI Team")
                                .email("team@meli.com")));
    }
}
