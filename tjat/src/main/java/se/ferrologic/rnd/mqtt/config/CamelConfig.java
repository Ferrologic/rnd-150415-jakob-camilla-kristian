package se.ferrologic.rnd.mqtt.config;

import org.apache.camel.CamelContext;
import org.apache.camel.component.paho.PahoComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig {

	@Autowired
	CamelContext camelContext;

	@Bean
	PahoComponent pahoComponent() {

		PahoComponent paho = new PahoComponent();
		paho.setCamelContext(camelContext);

		return paho;
	}

}
