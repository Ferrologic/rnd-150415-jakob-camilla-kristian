package se.ferrologic.rnd.oystein.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.component.swagger.DefaultCamelSwaggerServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

	@Value("${swagger.title}")
	String title;

	@Value("${swagger.description}")
	String description;

	@Value("${swagger.termsOfServiceUrl}")
	String termsOfServiceUrl;

	@Value("${swagger.license}")
	String license;

	@Value("${swagger.licenseUrl}")
	String licenseUrl;

	/*
	 * Swagger Camel Configuration
	 */
	@Bean
	public ServletRegistrationBean swaggerServlet() {
		ServletRegistrationBean swagger = new ServletRegistrationBean(new DefaultCamelSwaggerServlet(), "/api-docs/*");
		Map<String, String> params = new HashMap<>();
		params.put("base.path", "api");
		params.put("api.path", "api-docs");
		params.put("api.title", title);
		params.put("api.description", description);
		params.put("api.termsOfServiceUrl", termsOfServiceUrl);
		params.put("api.license", license);
		params.put("api.licenseUrl", licenseUrl);
		swagger.setInitParameters(params);

		return swagger;
	}
}
