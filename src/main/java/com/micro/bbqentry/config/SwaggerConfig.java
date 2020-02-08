package com.micro.bbqentry.config;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * swagger 配置类
 *
 * @author jockeys
 * @since 2020/2/7
 */
//@Configuration
//@EnableSwagger2
public class SwaggerConfig {
    private  static final String SWAGGER_SCAN_BASE_PACKAGE = "com.micro.bbqentry.controller";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("BBQ微服务框架")
                .description("BBQ")
                .termsOfServiceUrl("http://domain.com")
                .version("0.0.1")
                .build();
    }

}
