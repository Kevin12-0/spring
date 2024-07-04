package com.alura.screenmatch.model;

import java.util.Optional;
import java.util.OptionalDouble;

import javax.swing.text.html.Option;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Serie {
    private String titulo;
    private Integer totalDeTemporadas;
    private Double evaluacion;
    private Categoria genero;
    private String poster;
    private String actors;
    private String plot;

    /* creacion del metodo constructor */
    public Serie(DatosSerie datosSerie) {
        this.titulo = datosSerie.titulo();
        this.totalDeTemporadas = datosSerie.totalDeTemporadas();
        /* Convertir en caso de que sea n/a a un 0 */
        this.evaluacion = OptionalDouble.of(Double.valueOf(datosSerie.evaluacion())).orElse(0);
        this.poster = datosSerie.poster();
        /* trae el primer genero y no debe trare algun luno */
        this.genero = Categoria.fromString(datosSerie.genero().split(",")[0].trim());
        this.plot = datosSerie.plot();
    }

    /* creacion de metodos get */
    public String getActors() {
        return actors;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public Categoria getGenero() {
        return genero;
    }

    public String getPlot() {
        return plot;
    }

    public String getPoster() {
        return poster;
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getTotalDeTemporadas() {
        return totalDeTemporadas;
    }

    /* creacion de set */
    public void setActors(String actors) {
        this.actors = actors;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setTotalDeTemporadas(Integer totalDeTemporadas) {
        this.totalDeTemporadas = totalDeTemporadas;
    }

    /* creacion de toString */

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "genero -> " + genero + " titulo -> " + titulo + " plot-> " + plot + " Actors-> " + actors + " Poster-> "
                + poster
                + " Total de temporadas-> " + totalDeTemporadas + " evaluacion-> " + evaluacion;
    }
}
