package io.idev.rimberry.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.idev.rimberry.StoreApiConstants;

@RequestMapping(StoreApiConstants.API_BASE_URI)
@RestController
public class HelloController {

	@GetMapping("/hello")
	public String hello() {
		return "Hello Rimberry!";
	}
}
