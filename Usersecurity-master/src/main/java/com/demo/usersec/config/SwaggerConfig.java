
package com.demo.usersec.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {

	@Bean
	public Docket productApi() { 
		return new Docket(DocumentationType.SWAGGER_2).select()  
				.apis(RequestHandlerSelectors.basePackage("org.fsl.dto.ilm.controllers")).paths(PathSelectors.any())
				.build().apiInfo(metaInfo());

	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	private ApiInfo metaInfo() {

		@SuppressWarnings("rawtypes")
		ApiInfo apiInfo = new ApiInfo("ILM", "ILM API List", "1.0", "Terms of Service",
				new Contact("ILM Support", null, "support@ilm.postclosedlx.com"), null, null, new ArrayList<VendorExtension>());

		return apiInfo;
	}
}
