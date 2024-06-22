package com.alura.screenmatch.Principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.alura.screenmatch.model.DatosSerie;
import com.alura.screenmatch.model.DatosTemporada;
import com.alura.screenmatch.service.ConsumoAPI;
import com.alura.screenmatch.service.ConvierteDatos;

/* menu para obtemer nombre de una serie */
public class Principal {
    /* se define el scanner para la pelicula */
    private Scanner teclado = new Scanner(System.in);
    /* estancia para consumir una API */
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    /* crear contantes */
    /* constante de url */
    private final String URL_BASE = "http://www.omdbapi.com/?t=";
    /* constante de la API KEY */
    private final String API_KEY = "&apikey=14991e95";
    /* crear la estancia para crear los datos */
    private ConvierteDatos conversor = new ConvierteDatos();

    public void muestraElMenu() {
        /* se pregunta el nomrebde la pelicula */
        System.out.println("Escribe el nombre de la seria que deseas buscar(Ingles): ");
        /* variable para pedir el nombre de la pelicula o serie */
        var nombreSerie = teclado.nextLine();
        /* generar la url de la API */
        var json = consumoAPI.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        /* imprimir el valore del Json */
        System.out.println(json);
        /* variable para obtener los datos del json */
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println(datos);
        /* lista para la informacion de la temporadas */
        List<DatosTemporada> temporadas = new ArrayList<>();
        /* busca los datos de todas la temporadas */
        for (int i = 1; i <= datos.totalDeTemporadas(); i++) {
            json = consumoAPI
                    .obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + "&Season=" + i + API_KEY);
            /* convir cada una de las temporadas */
            var datosTemporadas = conversor.obtenerDatos(json, DatosTemporada.class);
            /* agregar a la lista */
            temporadas.add(datosTemporadas);
        }
        /* imprimir cada objeto del array */
        temporadas.forEach(System.out::println);
    }
}
