package se.ferrologic.rnd.mqtt.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.paho.PahoComponent;
import org.apache.camel.component.paho.PahoEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class TjatRoute extends RouteBuilder {

	@Autowired
	PahoComponent pahoComponent;
	@Autowired
	private SimpMessagingTemplate template;
	@Value("${mqtt.broker.url}")
	String brokerUrl;

	@Override
	public void configure() throws Exception {

		PahoEndpoint pahoTjat = new PahoEndpoint("paho://chat", pahoComponent);
		pahoTjat.setBrokerUrl(brokerUrl);

		PahoEndpoint pahoTjatSensorData = new PahoEndpoint("paho://sensordata", pahoComponent);
		pahoTjatSensorData.setBrokerUrl(brokerUrl);

		// @formatter:off
		from(pahoTjatSensorData).process(e -> {
				template.convertAndSend("/topic/chat.sensordata", e.getIn().getBody(String.class));
			})
			.to("log:CHAT_SENSORDATA")
			.routeId("ws.topic.chat.sensordata");
		
		from(pahoTjat).process(e -> {
				template.convertAndSend("/topic/chat.message", e.getIn().getBody(String.class));
			})
			.to("log:CHAT_MESSAGE")
			.routeId("ws.topic.chat.message");
		// @formatter:on

	}
}
