package com.alura.screenmatch.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.alura.screenmatch.dto.SerieDTO;
import com.alura.screenmatch.model.Serie;
import com.alura.screenmatch.repository.SerieRepositiry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/* crearlo como controlador */
@RestController
public class SerieController {
    /*
     * ruta para mapear
     */

    @Autowired
    private SerieRepositiry repository;

    @GetMapping("/series")
    public List<SerieDTO> obtenerTodasLasSeries() {
        return repository.findAll().stream().map(s -> new SerieDTO(s.getTitulo(), s.getTotalDeTemporadas(),
                s.getEvaluacion(), s.getGenero(), s.getPoster(), s.getActors(), s.getPlot()))
                .collect(Collectors.toList());
    }

}
