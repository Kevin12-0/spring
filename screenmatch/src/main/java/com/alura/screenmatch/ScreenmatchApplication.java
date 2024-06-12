package com.alura.screenmatch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alura.screenmatch.service.ConsumoAPI;

/* para trabajar con spring es nesesario implemntar
 * 
 * CommandLineRunner
 */

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/* hola mundo con spring */
		/* System.out.println("Hola mundo con Spring"); */

		/* variable para consumir la API */

		var consumoAPI = new ConsumoAPI();
		var json = consumoAPI.obtenerDatos("http://www.omdbapi.com/?t=it&apikey=14991e95");
		System.out.println(json);

	}

}
