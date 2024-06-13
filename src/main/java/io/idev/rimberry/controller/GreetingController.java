package io.idev.rimberry.controller;

import java.time.OffsetTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class GreetingController {

	private SimpMessagingTemplate template;
	
	@Autowired
	public GreetingController(SimpMessagingTemplate template) {
		this.template = template;
	}
	
	@MessageMapping("portfolio")
	public void greet(@Valid @RequestBody String greeting) {
		String text = "[" + OffsetTime.now() + "]:" + greeting;
		this.template.convertAndSend("topic/install", text);
	}
	
	
}
