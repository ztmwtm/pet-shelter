package com.example.petshelter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class PetShelterApplication {
	private final Logger logger = LoggerFactory.getLogger(PetShelterApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PetShelterApplication.class, args);
	}

}
