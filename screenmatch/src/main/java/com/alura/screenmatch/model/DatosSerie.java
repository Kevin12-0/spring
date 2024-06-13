package com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/* se ignoran los datos que no queremos mapear */
@JsonIgnoreProperties(ignoreUnknown = true)

public record DatosSerie(
        /* asignar un alias para tomarlo como referencia en el json */@JsonAlias("Title") String titulo,
        @JsonAlias("totalSeasons") Integer totalDeTemporadas, @JsonAlias("imdbRating") String evaluacion
/*
 * la diferencia con Alias, es que este nos oermite
 * leer y escribir o sobre escribir el objeto @JsonProperty("")
 */) {

}
