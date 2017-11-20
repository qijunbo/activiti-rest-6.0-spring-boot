package com.sunway.activiti.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		//@formatter:off
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				// "org.activiti.rest.service.api"
				.apis(RequestHandlerSelectors.basePackage("org.activiti.rest"))
				//.paths(PathSelectors.ant("/api/*"))
				.paths(PathSelectors.any())
				.build().apiInfo(apiInfo());
		//@formatter:on
	}

	public ApiInfo apiInfo() {
		//@formatter:off
		  return new ApiInfoBuilder()
	                .title("Activiti6.0 RESTful APIs")
	                .description("Activiti6.0 RESTful APIs")
	                .termsOfServiceUrl("http://www.sunwayworld.com")
	                .contact(new Contact("Sunway", "http://www.sunwayworld.com", "http://www.sunwayworld.com"))
	                .license("http://www.sunwayworld.com")
	                .licenseUrl("http://www.sunwayworld.com")
	                .version("1.2")
	                .build();
		//@formatter:on

	}
}
