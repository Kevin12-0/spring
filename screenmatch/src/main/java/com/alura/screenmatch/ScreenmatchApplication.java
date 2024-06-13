package com.alura.screenmatch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alura.screenmatch.model.DatosSerie;
import com.alura.screenmatch.service.ConsumoAPI;
import com.alura.screenmatch.service.ConvierteDatos;

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
		/* consumir la api */
		var json = consumoAPI.obtenerDatos("http://www.omdbapi.com/?t=Criminal+Minds&apikey=14991e95");
		/* imprime el json */
		System.out.println(json);
		/* variable de tipo convierte datos */
		ConvierteDatos conversor = new ConvierteDatos();
		/* pasamos los datos nesesarios */
		var datos = conversor.obtenerDatos(json, DatosSerie.class);
		/* se imprimen los datos que se quieren obtener */
		System.out.println(datos);

	}

}
