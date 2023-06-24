package com.unir.pizzams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PizzaMsApplication {

	public static void main(String[] args) {

		// Obtenemos perfil de ejecucion de variable de entorno. Si no hay, perfil default.
		String profile = System.getenv("PROFILE");
		System.setProperty("spring.profiles.active", profile != null ? profile : "default");
		SpringApplication.run(PizzaMsApplication.class, args);
	}

}
