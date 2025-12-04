package com.premierLeague.premier_Zone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;


@SpringBootApplication
@EnableWebSecurity
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class PremierZoneApplication {

	public static void main(String[] args) {

        SpringApplication.run(PremierZoneApplication.class, args);
	}



}
