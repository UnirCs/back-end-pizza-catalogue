package com.unir.pizzams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class PizzaMsApplication {

	public static void main(String[] args) {

		// Obtenemos perfil de ejecucion de variable de entorno. Si no hay, perfil default.
		String profile = System.getenv("PROFILE");
		System.setProperty("spring.profiles.active", profile != null ? profile : "default");
		
		log.debug("Waiting fo Internal Interface to start...");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		SpringApplication.run(PizzaMsApplication.class, args);
	}

}
