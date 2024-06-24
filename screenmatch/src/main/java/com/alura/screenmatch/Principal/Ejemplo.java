package com.alura.screenmatch.Principal;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class Ejemplo {
    public void muestraEjemplo() {
        List<String> nombres = Arrays.asList("Brenda", "Luis", "fer", "Pao", "black");

        /*
         * stream -> ejecutar varias funciones en cadena
         * sorted() -> ordenar una lista
         * limir -> numero maximo de registros que va a retornar, no importa el indice
         * como en Python
         * filter() -> condicion para hacer un filtro de la lista
         * startsWith -> que comiense con x caracter
         * map -> mapear los objetos
         * toUpperCase -> convertit el objeto a UpperCase
         */
        nombres.stream().sorted().limit(4).filter(n -> n.startsWith("L")).map(n -> n.toUpperCase())
                .forEach(System.out::println);
    }
}
