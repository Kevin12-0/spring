package com.alura.screenmatch.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    /* forma de hacerlo con query nativo */
    /*
     * @Query(value =
     * "SELECT * FROM series WHERE total_de_temporadas <= 6 AND evaluacion >= 7",
     * nativeQuery = true)
     */

    /*
     * s -> todo
     * Serie -> nombre de la entidad (tabla)
     * s -> alias
     */
    @Query("SELECT s FROM Serie s WHERE s.totalDeTemporadas <= :totalDeTemporadas AND s.evaluacion >= :evaluacion")
    List<Serie> seriesPorTemporadaYEvaluacion(int totalDeTemporadas, Double evaluacion);
}
