package com.movieverse.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI movieVerseOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MovieVerse API")
                        .description("""
                                Comprehensive Movie Management Platform API.
                                
                                Services:
                                - **Movie Catalogue** – Browse movies by year, genre, actors, directors
                                - **Financial Service** – Budget vs revenue, weekly box office tracking
                                - **Rating Service** – Submit and retrieve movie ratings
                                - **Snapshot Service** – Capture iconic movie moments
                                - **Recognition Service** – Awards and nominations tracker
                                - **Ticketing Service** – Cinema seat availability and booking
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("MovieVerse Engineering")
                                .email("api@movieverse.io"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Development")
                ));
    }
}
