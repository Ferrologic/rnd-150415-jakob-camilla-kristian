package se.ferrologic.rnd.mqtt;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

	@Autowired
	ProducerTemplate producer;

	@MessageMapping("/chat.message.out")
	public String filterMessage(@Payload String message) throws Exception {
		producer.sendBody("direct:chat-message-out", message);
		return message;
	}
}
