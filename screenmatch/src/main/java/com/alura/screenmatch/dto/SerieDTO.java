package com.alura.screenmatch.dto;

import com.alura.screenmatch.model.Categoria;

public record SerieDTO(String titulo,
        Integer totalDeTemporadas,
        Double evaluacion,
        Categoria genero,
        String poster,
        String actors,
        String plot) {

}
