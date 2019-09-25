package com.lambda.oladbe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// http://localhost:2019/swagger-ui.html
@Configuration
@EnableSwagger2
public class Swagger2Config
{
    @Bean
    public Docket api()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.lambda.oladbe"))
                .paths(PathSelectors.regex("/.*"))
                //.paths(PathSelectors.any())  shows all possible links
                .build()
                .useDefaultResponseMessages(false)
                .ignoredParameterTypes(Pageable.class)
                .apiInfo(apiEndPointsInfo());
    }

    private ApiInfo apiEndPointsInfo()
    {
        return new ApiInfoBuilder().title("Java Spring Back End Starting Project").description("Deployment for Java Back End Lambda School Spring Challenge").contact(new Contact("Kristin B.", "http://www.lambdaschool.com", "john@lambdaschool.com")).license("MIT").licenseUrl("https://github.com/LambdaSchool/java-starthere/blob/master/LICENSE").version("1.0.0").build();
    }
}
