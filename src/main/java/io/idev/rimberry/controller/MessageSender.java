package io.idev.rimberry.controller;

import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageSender {

	@Autowired
	private SimpMessagingTemplate  messageSendingOperation;
	
	@PostMapping("/send")
	void sendMessage(@RequestBody Message message) throws InterruptedException, ExecutionException {
		messageSendingOperation.convertAndSend("/topic/install", message);
	}
}
