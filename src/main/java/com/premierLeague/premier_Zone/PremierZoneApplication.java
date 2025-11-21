package com.premierLeague.premier_Zone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@SpringBootApplication
@EnableWebSecurity
public class PremierZoneApplication {

	public static void main(String[] args) {

        SpringApplication.run(PremierZoneApplication.class, args);
	}

}
