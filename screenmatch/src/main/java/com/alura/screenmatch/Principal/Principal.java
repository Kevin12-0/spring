package com.alura.screenmatch.Principal;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.alura.screenmatch.model.DatosEpisodio;
import com.alura.screenmatch.model.DatosSerie;
import com.alura.screenmatch.model.DatosTemporada;
import com.alura.screenmatch.model.Episodio;
import com.alura.screenmatch.service.ConsumoAPI;
import com.alura.screenmatch.service.ConvierteDatos;
import com.alura.screenmatch.model.Episodio;

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

        /* mostrar solo el titutlo de los episodios para las temporadas */
        for (int i = 0; i < datos.totalDeTemporadas(); i++) {
            List<DatosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
            /* traer el titulo para cada uno de los episodios */
            for (int j = 0; j < episodiosTemporada.size(); j++) {
                System.out.println(episodiosTemporada.get(j).titulo());
            }
        }
        System.out.println("--------Forma con lambda--------");
        /* mostrar solo el titulo de los episodios, con expreciones lambda */
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        /* convertir todas las informaciones a una lista de tipo DatosEpisodio */

        /*
         * creando lista
         * lista que va a traer la lista de las temporadas, convirtiendo cada episodio,
         * en una lista con sus elementos
         * 
         * .collect(Collectors.toList()) -> para mutar la lista, lo que causaria menos
         * errores y hace la lista mas eficaz de usar
         */

        List<DatosEpisodio> datosEpisodios = temporadas.stream().flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        /*
         * top 5 de episodios
         * se filtra para que las evaluaciones que sea N/A, no se ranquen
         * comprando el ranqueo del episodio e imprimiendo el resultado en reversa
         */
        System.out.println("Tops 5 de epiusodios");
        datosEpisodios.stream().filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed()).limit(5)
                .forEach(System.out::println);

        /* convertit los datatos a una lista de tipo episodio */

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d)))
                .collect(Collectors.toList());
        episodios.forEach(System.out::println);
    }
}
