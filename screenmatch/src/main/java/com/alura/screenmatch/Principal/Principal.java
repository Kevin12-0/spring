package com.alura.screenmatch.Principal;

import java.text.CollationElementIterator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.cglib.core.Local;

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

    /**
     * 
     */
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
        datosEpisodios.stream()
                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A")) // Filter out episodes with "N/A" evaluation
                .peek(e -> System.out.println("Primer filtro (N/A): " + e)) // Print each episode after the first filter
                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed()) // Sort episodes by evaluation in
                                                                                    // descending order
                .peek(e -> System.out.println("Segundo filtro de mayor a menor: " + e)) // Print each episode after
                                                                                        // sorting
                .limit(5) // Limit to the top 5 episodes
                .peek(e -> System.out.println("Tercer filtro de limite: " + e)) // Print each episode after applying the
                                                                                // limit
                .forEach(System.out::println); // Print the final result

        /* convertit los datatos a una lista de tipo episodio */

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d)))
                .collect(Collectors.toList());
        episodios.forEach(System.out::println);

        /* ver episodios por fecha u año */

        System.out.println("Indica el año a partir de cual deseas ver los episodios: ");
        var fecha = teclado.nextInt();
        teclado.nextLine();

        /* desde el año, emepzando por el mes 1 y dia 1 */
        LocalDate fechaBusqueda = LocalDate.of(fecha, 1, 1);
        /* dar formato a la fecha */
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        /*
         * buscar el episodio
         * 
         * quitando las fecha que vienen en null
         */
        episodios.stream()
                .filter(e -> e.getFechaDeLanzamiento() != null && e.getFechaDeLanzamiento().isAfter(fechaBusqueda))
                .forEach(e -> System.out.println("Temporada: " + e.getTemporada() + " Episodio " + e.getTitulo()
                        + " Fecha de lanzamiento " + e.getFechaDeLanzamiento().format(dft)));

        /* bsuqueda de episodio por pedaso de titulo */
        System.out.println("Escriba el titulo del epoisodio que desea buscar: ");
        var pedasoTitulo = teclado.nextLine();
        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(pedasoTitulo.toUpperCase()))
                .findFirst();
        /* si se encuentra */
        if (episodioBuscado.isPresent()) {
            System.out.println("Episodio Encontrado");
            System.out.println("Los datos son " + episodioBuscado.get());
            /* si no se encuentra */
        } else {
            System.out.println("episodio no encontrado");
        }

        /* mapas */

        Map<Integer, Double> evaluacionesPorTemporada = episodios.stream().filter(e -> e.getEvaluacion() > 0.0).collect(
                Collectors.groupingBy(Episodio::getTemporada, Collectors.averagingDouble(Episodio::getEvaluacion)));
        System.out.println(evaluacionesPorTemporada);
        /* datos estadisticos sobre los capitulos de la serie (evaluacion) */
        DoubleSummaryStatistics est = episodios.stream().filter(e -> e.getEvaluacion() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));
        /* obtener los valores medios, maximos de Double SummaryStatics */
        System.out.println(
                "Media de las evaluaciones: " + est.getAverage() + " Episodio mejor evaluado: " + est.getMax()
                        + " Episodio mejor evaluado " + est.getMax() + " Episodio peor evaluado " + est.getMin());
    }
}
