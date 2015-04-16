package se.ferrologic.rnd.mqtt.routes;

import java.util.UUID;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.paho.PahoComponent;
import org.apache.camel.component.paho.PahoEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MockSensorRoute extends RouteBuilder {

	@Autowired
	PahoComponent pahoComponent;
	@Autowired
	private SimpMessagingTemplate template;
	@Value("${mqtt.broker.url}")
	String brokerUrl;

	@Override
	public void configure() throws Exception {

		PahoEndpoint pahoHelloWorld = new PahoEndpoint("paho://sensordata", pahoComponent);
		pahoHelloWorld.setBrokerUrl(brokerUrl);

		// @formatter:off
		from("timer://mockSensor")
			.process(e -> {
				// Get sensor data
				String sensorData = UUID.randomUUID().toString();
				// Prepare camel message for further routing of sensordata
				e.getIn().setBody(sensorData);
				
			})
			// Send camel message to MQTT-topic
			.to(pahoHelloWorld)
			.to("log:SENSOR")
			.routeId("timer.mocksensor");
		
		// @formatter:on

	}
}
