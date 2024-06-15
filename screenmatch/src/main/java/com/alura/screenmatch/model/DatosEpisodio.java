package com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/* Ignorar objetos que no nesesitamos */
@JsonIgnoreProperties(ignoreUnknown = true)
/* definicion de variables a mapear y asiganacion de Alias */
public record DatosEpisodio(@JsonAlias("Title") String titulo, @JsonAlias("Episode") Integer numeroEpisodio,
                @JsonAlias("imdbRating") String evaluacion,
                @JsonAlias("Released") String fechaDeLanzamiento) {

}
