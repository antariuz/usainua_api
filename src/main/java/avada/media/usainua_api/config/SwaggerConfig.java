package avada.media.usainua_api.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .securitySchemes(new LinkedList<>(apiKey()))
                .apiInfo(
                        new ApiInfo(
                                "USAINUA RESTful API",
                                "Api Documentation",
                                "1.0",
                                null,
                                null,
                                null,
                                null,
                                Collections.emptyList()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("avada.media.usainua_api.rest.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private List<ApiKey> apiKey() {
        return new LinkedList<>(Arrays.asList(
                new ApiKey("accessToken", "Authorization", "header"),
                new ApiKey("refreshToken", "Authorization", "header")
        ));
    }

    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .docExpansion(DocExpansion.LIST) // or DocExpansion.NONE or DocExpansion.FULL
                .build();
    }

}
