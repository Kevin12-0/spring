package com.alura.screenmatch.service;

public interface IConvierteDatos {
    /* creando metodos
     * <T> T: Datos genericos
     */
    <T> T obtenerDatos(String json, Class<T> clase);
}
