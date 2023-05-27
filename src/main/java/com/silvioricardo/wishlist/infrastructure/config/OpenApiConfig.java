package com.silvioricardo.wishlist.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  private final String appName;
  private final String description;
  private final String appVersion;

  @Autowired
  public OpenApiConfig(@Value("${info.app.name}") String appName,
      @Value("${info.app.description}")  String description,
      @Value("${info.app.version}") String appVersion) {
    this.appName = appName;
    this.description = description;
    this.appVersion = appVersion;
  }

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .components(new Components())
        .info(new Info()
            .title(appName)
            .version(appVersion)
            .description(description));
  }
}