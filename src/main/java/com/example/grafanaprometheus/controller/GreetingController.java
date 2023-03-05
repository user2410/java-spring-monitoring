package com.example.grafanaprometheus.controller;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.example.grafanaprometheus.model.greeting.Greeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	final static Logger logger = LoggerFactory.getLogger(GreetingController.class);

	private static final String greetingTemplate = "Hello, %s!";
	private static final String warningTemplate = "The 10th visitor is %s, resetting visitor count!";
	private static final String infoTemplate = "The %s visitor is %s";
	private final AtomicInteger counter = new AtomicInteger();

	private static String ordinalNo(int value) {
		int hunRem = value % 100;
		int tenRem = value % 10;
		if (hunRem - tenRem == 10) {
			return "th";
		}
		switch (tenRem) {
			case 1:
				return "st";
			case 2:
				return "nd";
			case 3:
				return "rd";
			default:
				return "th";
		}
	}

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		if(counter.get() == 10){
			logger.warn(String.format(warningTemplate, name));
			counter.set(0);
		}else{
			logger.info(String.format(infoTemplate, ordinalNo(counter.incrementAndGet()), name));
		}
		return new Greeting(counter.get(), String.format(greetingTemplate, name));
	}
}
