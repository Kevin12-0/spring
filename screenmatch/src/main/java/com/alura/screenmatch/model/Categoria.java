package com.alura.screenmatch.model;

public enum Categoria {
    /*
     * categorias de la serie
     * 
     * se va escribir la referencia como estan en la API
     */
    ACCION("Action"), ROMANCE("Romance"), COMEDIA("Comedy"), CRIMEN("Crime"), DRAMA("Drama");

    /* se crea un atributo de tipo privado para la categoria */
    private String categoriaOmdb;

    /* constructor que va a resivir la categoria */
    Categoria(String categoriaOmdb) {
        this.categoriaOmdb = categoriaOmdb;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("No se encontro ninguna categoria: " + text);
    }
}
