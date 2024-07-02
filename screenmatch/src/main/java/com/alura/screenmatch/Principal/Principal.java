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

public class Principal {
        private Scanner teclado = new Scanner(System.in);
        private ConsumoAPI consumoApi = new ConsumoAPI();
        private final String URL_BASE = "https://www.omdbapi.com/?t=";
        private final String API_KEY = "&apikey=14991e95";
        private ConvierteDatos conversor = new ConvierteDatos();

        /**
         * 
         */
        private List<DatosSerie> datosSeries = new ArrayList<>();

        public void muestraElMenu() {
                var opcion = -1;
                while (opcion != 0) {
                        var menu = """
                                        1 - Buscar series
                                        2 - Buscar episodios
                                        3 - Mostrar series buscadas

                                        0 - Salir
                                        """;
                        System.out.println(menu);
                        opcion = teclado.nextInt();
                        teclado.nextLine();

                        switch (opcion) {
                                case 1:
                                        buscarSerieWeb();
                                        break;
                                case 2:
                                        buscarEpisodioPorSerie();
                                        break;
                                case 3:
                                        muestrarSeriesBuscadas();
                                        break;

                                case 0:
                                        System.out.println("Cerrando la aplicación...");
                                        break;
                                default:
                                        System.out.println("Opción inválida");
                        }
                }

        }

        private DatosSerie getDatosSerie() {
                System.out.println("Escribe el nombre de la serie que deseas buscar");
                var nombreSerie = teclado.nextLine();
                var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
                System.out.println(json);
                DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
                return datos;
        }

        private void buscarEpisodioPorSerie() {
                DatosSerie datosSerie = getDatosSerie();
                List<DatosTemporada> temporadas = new ArrayList<>();

                for (int i = 1; i <= datosSerie.totalDeTemporadas(); i++) {
                        var json = consumoApi.obtenerDatos(
                                        URL_BASE + datosSerie.titulo().replace(" ", "+") + "&season=" + i + API_KEY);
                        DatosTemporada datosTemporada = conversor.obtenerDatos(json, DatosTemporada.class);
                        temporadas.add(datosTemporada);
                }
                temporadas.forEach(System.out::println);
        }

        private void buscarSerieWeb() {
                DatosSerie datos = getDatosSerie();
                datosSeries.add(datos);
                System.out.println(datos);
        }

        private void muestrarSeriesBuscadas() {
                datosSeries.forEach(System.out::println);
        }

}