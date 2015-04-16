package se.ferrologic.rnd.mqtt;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.paho.PahoComponent;
import org.apache.camel.component.paho.PahoEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

	@Autowired
	ProducerTemplate producer;
	@Autowired
	PahoComponent pahoComponent;

	@Value("${mqtt.broker.url}")
	String brokerUrl;

	PahoEndpoint pahoTjat;

	@MessageMapping("/chat.message.out")
	public String filterMessage(@Payload String message) throws Exception {
		if (pahoTjat == null || !pahoTjat.isStarted()) {
			pahoTjat = new PahoEndpoint("paho://chat", pahoComponent);
			pahoTjat.setBrokerUrl(brokerUrl);
			pahoTjat.start();
		}

		producer.sendBody(pahoTjat, message);
		return message;
	}
}
