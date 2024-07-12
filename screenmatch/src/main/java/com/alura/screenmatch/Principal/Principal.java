package com.alura.screenmatch.Principal;

// Importaciones necesarias para la funcionalidad del código
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.alura.screenmatch.model.Categoria;
import com.alura.screenmatch.model.DatosSerie;
import com.alura.screenmatch.model.DatosTemporada;
import com.alura.screenmatch.model.Episodio;
import com.alura.screenmatch.model.Serie;
import com.alura.screenmatch.repository.SerieRepositiry;
import com.alura.screenmatch.service.ConsumoAPI;
import com.alura.screenmatch.service.ConvierteDatos;

public class Principal {
        // Instancia de Scanner para leer la entrada del usuario
        private Scanner teclado = new Scanner(System.in);
        // Instancia para consumir la API externa
        private ConsumoAPI consumoApi = new ConsumoAPI();
        // URL base de la API y clave de API
        private final String URL_BASE = "https://www.omdbapi.com/?t=";
        private final String API_KEY = "&apikey=14991e95";
        // Instancia para convertir los datos obtenidos de la API
        private ConvierteDatos conversor = new ConvierteDatos();

        // Lista para almacenar los datos de las series que se han buscado
        private List<DatosSerie> datosSeries = new ArrayList<>();

        private SerieRepositiry repositorio;

        /* se hace la lista como varibale global */
        private List<Serie> series;

        public Principal(SerieRepositiry repositiry) {
                this.repositorio = repositiry;
        }

        // Método principal que muestra el menú y maneja la interacción del usuario
        public void muestraElMenu() {
                // Variable para almacenar la opción del usuario
                var opcion = -1;
                // Bucle para mantener la aplicación activa hasta que el usuario decida salir
                while (opcion != 0) {
                        // Menú de opciones
                        var menu = """
                                        1 - Buscar series
                                        2 - Buscar episodios
                                        3 - Mostrar series buscadas
                                        4 - Buscar serie por titulo
                                        5 - Top 5 series
                                        6 - Buscar por categoria
                                        7 - filtrara por temporadas y evalaucion

                                        0 - Salir
                                        """;
                        System.out.println(menu);
                        // Lectura de la opción elegida por el usuario
                        opcion = teclado.nextInt();
                        teclado.nextLine(); // Consumir la línea sobrante

                        // Estructura de control para manejar cada opción del menú
                        switch (opcion) {
                                case 1:
                                        // Llamar al método para buscar una serie
                                        buscarSerieWeb();
                                        break;
                                case 2:
                                        // Llamar al método para buscar episodios de una serie
                                        buscarEpisodioPorSerie();
                                        break;
                                case 3:
                                        // Llamar al método para mostrar las series buscadas
                                        muestrarSeriesBuscadas();
                                        break;
                                case 4:
                                        buscarSeriePoritulo();
                                        break;
                                case 5:
                                        topSeries();
                                        break;
                                case 6:
                                        buscarSeriePorCategoria();
                                        break;
                                case 7:
                                        buscarPorEvaluacionYTotalTemporadas();
                                        break;
                                case 0:
                                        // Mensaje de cierre de la aplicación
                                        System.out.println("Cerrando la aplicación...");
                                        break;
                                default:
                                        // Mensaje para opciones inválidas
                                        System.out.println("Opción inválida");
                        }
                }
        }

        private void buscarPorEvaluacionYTotalTemporadas() {
                /* prefgunta el numero de temporadas y de evaluacion para empézar a filtrar */
                System.out.print("numero de temporadas para filtrar: -> ");
                var totalDeTemporadas = teclado.nextInt();
                teclado.nextLine();
                System.out.print("con que valor de evaluacion: ->");
                var evaluacion = teclado.nextDouble();
                teclado.nextLine();
                /* funvion del filtro */
                List<Serie> filtroSerie = repositorio.seriesPorTemporadaYEvaluacion(totalDeTemporadas, evaluacion);
                System.out.println("----Series encontradas----");
                filtroSerie.forEach(s -> System.out.println(s.getTitulo() + " -evalaucion: -> " + s.getEvaluacion()));
        }

        private void buscarSeriePorCategoria() {
                System.out.print("Escribe la categoria/genero por la cual deseas buscar la serie: ->");
                var genero = teclado.nextLine();
                var categoria = Categoria.fromEspanol(genero);
                List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);
                System.out.println("Series encontradas de la categoria ->" + genero);
                seriesPorCategoria.forEach(System.out::println);
        }

        private void topSeries() {
                List<Serie> topCinco = repositorio.findTop5ByOrderByEvaluacionDesc();
                topCinco.forEach(
                                s -> System.out.println(
                                                "Serie: -> " + s.getTitulo() + " evaluacion: ->" + s.getEvaluacion()));
        }

        private void buscarSeriePoritulo() {
                /* pedir el nombre de la serie */
                System.out.print("Escribe el nombre de la serie que deseas buscar: ->");
                var nombreSerie = teclado.nextLine();
                Optional<Serie> serieBuscada = repositorio.findByTituloContainsIgnoreCase(nombreSerie);
                if (serieBuscada.isPresent()) {
                        System.out.println("La serie buscada es: -> " + serieBuscada.get());
                } else {
                        System.out.println("error: Serie no buscada");
                }
        }

        // Método para obtener datos de una serie a partir del nombre ingresado por el
        // usuario
        private DatosSerie getDatosSerie() {
                // Solicitar el nombre de la serie al usuario
                System.out.println("Escribe el nombre de la serie que deseas buscar");
                var nombreSerie = teclado.nextLine();
                // Construir la URL de la API con el nombre de la serie y hacer la solicitud
                var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
                // Imprimir la respuesta JSON obtenida de la API
                System.out.println(json);
                // Convertir el JSON a un objeto de tipo DatosSerie
                DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
                // Devolver los datos de la serie
                return datos;
        }

        // Método para buscar episodios de una serie específica
        private void buscarEpisodioPorSerie() {
                // Obtener los datos de la serie ingresada por el usuario
                /* DatosSerie datosSerie = getDatosSerie(); */
                /* mostrarr las series que se buscaron */
                muestrarSeriesBuscadas();
                /* pedir el nombre de la serie */
                System.out.println("Escribe el nombre de la serie de la cual deseas ver los episodios: ->");
                var nombreSerie = teclado.nextLine();
                /* optional, para hacer coincider los resultados */
                Optional<Serie> serie = series.stream()
                                .filter(s -> s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                                .findFirst();
                if (serie.isPresent()) {
                        var serieEncontrada = serie.get();
                        // Lista para almacenar las temporadas de la serie
                        List<DatosTemporada> temporadas = new ArrayList<>();

                        // Bucle para obtener los datos de cada temporada de la serie
                        for (int i = 1; i <= serieEncontrada.getTotalDeTemporadas(); i++) {
                                // Construir la URL para obtener datos de la temporada específica
                                var json = consumoApi.obtenerDatos(
                                                URL_BASE + serieEncontrada.getTitulo().replace(" ", "+") + "&season="
                                                                + i
                                                                + API_KEY);
                                // Convertir el JSON a un objeto de tipo DatosTemporada
                                DatosTemporada datosTemporada = conversor.obtenerDatos(json, DatosTemporada.class);
                                // Agregar los datos de la temporada a la lista
                                temporadas.add(datosTemporada);
                        }
                        // Imprimir cada temporada obtenida
                        temporadas.forEach(System.out::println);
                        List<Episodio> episisodios = temporadas.stream()
                                        .flatMap(d -> d.episodios().stream().map(e -> new Episodio(d.numero(), e)))
                                        .collect(Collectors.toList());

                        serieEncontrada.setEpisodios(episisodios);
                        repositorio.save(serieEncontrada);
                }

        }

        // Método para buscar una serie y agregarla a la lista de series buscadas
        private void buscarSerieWeb() {
                // Obtener los datos de la serie ingresada por el usuario
                DatosSerie datos = getDatosSerie();
                // Agregar los datos de la serie a la lista de series buscadas
                datosSeries.add(datos);
                /* crear estancia de serie */
                Serie serie = new Serie(datos);
                /* pasarle los datos para almacenar */
                repositorio.save(serie);
                // Imprimir los datos de la serie
                System.out.println(datos);
        }

        // Método para mostrar todas las series que se han buscado
        private void muestrarSeriesBuscadas() {
                // Imprimir cada serie en la lista de series buscadas
                /* creacion de lista para alamacenar las series */
                /* retorna la base de datos con llos datos de las series guardadas */
                series = repositorio.findAll();
                series.stream().sorted(Comparator.comparing(Serie::getGenero)).forEach(System.out::println);
                /*
                 * new ArrayList<>();
                 * series = datosSeries.stream().map(d -> new
                 * Serie(d)).collect(Collectors.toList());
                 * series.stream().sorted(Comparator.comparing(Serie::getGenero)).forEach(System
                 * .out::println);
                 */
        }
}
