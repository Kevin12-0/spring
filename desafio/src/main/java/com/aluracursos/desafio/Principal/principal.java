package main.java.com.aluracursos.desafio.Principal;

import main.java.com.aluracursos.desafio.service.ConsumoAPI;
import main.java.com.aluracursos.desafio.service.ConvierteDatos;

public class principal {

    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books";

    public void muestraElMenu() {
        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);

    }
}
