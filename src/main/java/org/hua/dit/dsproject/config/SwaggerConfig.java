package org.hua.dit.dsproject.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI petManagementOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Pet Management System API")
                        .description("Distributed Systems Assignment - Pet Management System with role-based access")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("DS Project Team")));
    }
}