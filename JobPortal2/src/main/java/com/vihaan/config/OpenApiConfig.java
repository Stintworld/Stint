package com.vihaan.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	 @Bean
	    public OpenAPI customOpenAPI() {
	        return new OpenAPI()
	                .info(new Info()
	                        .title("Spring Boot REST API")
	                        .description("Spring Boot REST API Documentation")
	                        .version("v1.0")
	                        .contact(new Contact().name("Your Name").email("your-email@example.com"))
	                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
	                .externalDocs(new ExternalDocumentation()
	                        .description("Spring Boot Wiki Documentation")
	                        .url("https://springshop.wiki.github.org/docs"));
	    }

	    @Bean
	    public GroupedOpenApi publicApi() {
	        return GroupedOpenApi.builder()
	                .group("springshop-public")
	                .pathsToMatch("/api/**")
	                .build();
	    }
}
