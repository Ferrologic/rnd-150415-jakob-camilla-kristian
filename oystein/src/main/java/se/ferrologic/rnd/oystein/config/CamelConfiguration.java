package se.ferrologic.rnd.oystein.config;

import org.apache.camel.CamelContext;
import org.apache.camel.component.metrics.routepolicy.MetricsRoutePolicyFactory;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.processor.interceptor.Tracer;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfiguration {

	@Autowired
	CamelContext camelContext;
	@Autowired
	ConfigurableApplicationContext context;

	private static final String CAMEL_URL_MAPPING = "/api/*";
	private static final String CAMEL_SOAP_URL_MAPPING = "/soap/*";
	private static final String CAMEL_SOAP_SERVLET_NAME = "CXFServlet";
	private static final String CAMEL_SERVLET_NAME = "CamelServlet";

	/*
	 * Camel REST servlet component configuration
	 */
	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new CamelHttpTransportServlet(),
				CAMEL_URL_MAPPING);
		registration.setName(CAMEL_SERVLET_NAME);

		return registration;
	}

	/*
	 * Camel SOAP servlet component configuration
	 */
	@Bean
	public ServletRegistrationBean soapServletRegistrationBean() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new CXFServlet(), CAMEL_SOAP_URL_MAPPING);
		registration.setName(CAMEL_SOAP_SERVLET_NAME);
		return registration;
	}

	/*
	 * Custom config of the camel context
	 */
	@Bean
	CamelContextConfiguration contextConfiguration() {
		return new CamelContextConfiguration() {
			@Override
			public void beforeApplicationStart(CamelContext context) {
				// enable performance metrics: http://www.davsclaus.com/2014/09/more-metrics-in-apache-camel-214.html
				context.addRoutePolicyFactory(new MetricsRoutePolicyFactory());
			}
		};
	}

	@Bean
	public Tracer camelTracer() {
		Tracer tracer = new Tracer();
		tracer.setTraceExceptions(false);
		tracer.setTraceInterceptors(true);
		tracer.setLogName("io.ipaas.tracer");
		return tracer;
	}

	@Bean
	Bus cxf() {
		return BusFactory.newInstance().createBus();
	}

}
