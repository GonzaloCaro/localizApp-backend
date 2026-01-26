package com.gestion_transporte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GestionTransporteApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionTransporteApplication.class, args);
	}

}
