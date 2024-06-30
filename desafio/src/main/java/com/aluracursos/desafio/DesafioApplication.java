package com.aluracursos.desafio;

import java.security.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesafioApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DesafioApplication.class, args);

	}

	@Override
	public void run(String ... args) throws Exception{
		Principal principal = new Principal();

	}

}
