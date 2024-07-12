package com.alura.screenmatch.model;

import org.hibernate.mapping.List;

public enum Categoria {
    /*
     * categorias de la serie
     *
     * se va escribir la referencia como estan en la API
     */
    ACCION("Action", "Accion"), ROMANCE("Romance", "Romance"), COMEDIA("Comedy", "Comedia"), CRIMEN("Crime", "Crimen"),
    DRAMA("Drama", "Drama");

    /* se crea un atributo de tipo privado para la categoria */
    private String categoriaOmdb;
    private String categoriaEspanol;

    /* constructor que va a resivir la categoria */
    Categoria(String categoriaOmdb, String categoriaEspanol) {
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaEspanol = categoriaEspanol;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("No se encontro ninguna categoria: " + text);
    }

    public static Categoria fromEspanol(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaEspanol.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("No se encontro ninguna categoria: " + text);
    }
}
