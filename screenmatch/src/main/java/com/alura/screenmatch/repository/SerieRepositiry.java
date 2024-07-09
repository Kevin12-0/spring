package com.alura.screenmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alura.screenmatch.model.Serie;

/* se trabja con serie y long, esto pra guardar los datos */

public interface SerieRepositiry extends JpaRepository<Serie,Long> {

}
