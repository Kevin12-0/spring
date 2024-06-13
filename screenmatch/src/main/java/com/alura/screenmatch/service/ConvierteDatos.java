package com.alura.screenmatch.service;

import com.alura.screenmatch.model.DatosSerie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvierteDatos {
    /* crear una estancia para mapear los datos */
    private ObjectMapper objectMapper = new ObjectMapper();
    /* implementar clases */

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        // TODO Auto-generated method stub
        try {
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            System.out.println(e);
        }
        return null;

    }

}
