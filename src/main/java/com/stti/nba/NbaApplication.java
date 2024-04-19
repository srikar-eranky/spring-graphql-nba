package com.stti.nba;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NbaApplication {

	private static final Logger log = LoggerFactory.getLogger(NbaApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(NbaApplication.class, args);
		log.info("Hello world");
	}

}