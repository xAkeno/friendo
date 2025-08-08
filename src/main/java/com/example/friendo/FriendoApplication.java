package com.example.friendo;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FriendoApplication {
	private static Logger log = Logger.getLogger(FriendoApplication.class.getName());
	public static void main(String[] args) {
		SpringApplication.run(FriendoApplication.class, args);
		log.info("Im Working like slavee");
		//To launch front end cd src/main/resources/static/frontfriendo -npm run dev
	}
}
