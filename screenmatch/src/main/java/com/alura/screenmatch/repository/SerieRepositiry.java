package com.alura.screenmatch.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alura.screenmatch.model.Categoria;
import com.alura.screenmatch.model.Serie;

/* se trabja con serie y long, esto pra guardar los datos */

public interface SerieRepositiry extends JpaRepository<Serie, Long> {
    /* buscar por */
    Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie);

    /* top 5 */
    List<Serie> findTop5ByOrderByEvaluacionDesc();

    /* categorias */
    List<Serie> findByGenero(Categoria categoria);
}
