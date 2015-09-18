package se.ferrologic.rnd.oystein.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;

import se.ferrologic.rnd.oystein.models.User;

@Component
public class OysteinRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		// @formatter:off
		
		// servlet is configured in FerroRestApplication.java, requires dependecy on camel-servlet
		restConfiguration()
			.component("servlet")
			.bindingMode(RestBindingMode.json)
			.enableCORS(true);
		
		rest("oystein")
			.get("ping")
				.to("direct:ping")
			.get("greet/{name}")
				.param().name("name").type(RestParamType.path).description("The name of the person to greet").dataType("string").endParam()
				.to("direct:greet")
			.get("user/{id}").outType(User.class)
				.param().name("id").type(RestParamType.path).description("The id of the user to get").dataType("int").endParam()
				.to("direct:getUser")
			.post("user").type(User.class)
				.param().name("body").type(RestParamType.body).description("User object to overwrite").dataType("User object").endParam()
				.to("direct:updateUser")
		;
		
		from("direct:ping").transform().constant("pong");
		
		from("direct:greet")
			.process(e -> {
				String name = (String) e.getIn().getHeader("name");
				e.getIn().setBody("Hello " + name);
		});
		
		from("direct:getUser")
			.process(e -> {
				int id = Integer.parseInt((String) e.getIn().getHeader("id"));
				
				if (id == 1) {
					User jakob = new User();
					jakob.setId(1);
					jakob.setFirstName("Jakob");
					jakob.setLastName("Thun");
					e.getIn().setBody(jakob);
				}
				else if (id == 2) {
					User oystein = new User();
					oystein.setId(2);
					oystein.setFirstName("Öystein");
					oystein.setLastName("Andersen");
					e.getIn().setBody(oystein);
				}
				else {
					throw new Exception("User not found (try id 1 or 2)");
				}
				
		});
		
		from("direct:updateUser").transform().constant("Done.");
		
		// @formatter:on

	}
}
