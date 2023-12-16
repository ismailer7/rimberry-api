package io.idev.rimberry.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class BodyGenerationProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		System.out.println("Generating Body Processor..");
	}

}
