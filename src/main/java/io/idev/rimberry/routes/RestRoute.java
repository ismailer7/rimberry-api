package io.idev.rimberry.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RestRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		
	}

//	private BodyGenerationProcessor bodyGenerationProcessor;
//	
//	@Override
//	public void configure() throws Exception {
//		restConfiguration().host("dummy.restapiexample.com").port(80);
//		
//		from("timer:rest-client?repeatCount=10s")
//			.process(bodyGenerationProcessor)
//			.to("rest:get:/api/v1/employees")
//			.log("${body}");
//	}
	
}
