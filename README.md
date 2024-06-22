
# Primer vercion de codigo de java Spring

```java
/* hola mundo con spring */
        /* System.out.println("Hola mundo con Spring"); */

        /* variable para consumir la API */

        var consumoAPI = new ConsumoAPI();
        /* consumir la api */
        var json = consumoAPI.obtenerDatos("http://www.omdbapi.com/?t=Criminal+Minds&apikey=14991e95");
        /* imprime el json */
        System.out.println(json);
        /* variable de tipo convierte datos */
        ConvierteDatos conversor = new ConvierteDatos();
        /* pasamos los datos nesesarios */
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        /* se imprimen los datos que se quieren obtener */
        System.out.println(datos);
        /* cambiar valor de la bariable de nombre json */
        json = consumoAPI.obtenerDatos("https://www.omdbapi.com/?t=Criminal+Minds&Season=1&episode=1&apikey=14991e95");
        /* imprimir el json obtenido */
        System.out.println(json);
        /*
         * Convertir los datos (Obtener los datos que nesesitamos)
         * con ayuda del record creado Datas episodio
         */
        DatosEpisodio episodio = conversor.obtenerDatos(json, DatosEpisodio.class);
        /* imprimir los resultados */
        System.out.println(episodio);
        /* cambiar valor de variable json, para obtener el valore por temporada */
        json = consumoAPI.obtenerDatos("https://www.omdbapi.com/?t=Criminal+Minds&Season=1&apikey=14991e95");
        /* crear lista, para alamacenar los datos de */
        List<DatosTemporada> temporadas = new ArrayList<>();
        /*
         * creando for para el numero de temporadas
         * como no hay temporada 0, se empieza en temporada 1
         */
        for (int i = 1; i <= datos.totalDeTemporadas(); i++) {
            json = consumoAPI
                    .obtenerDatos("https://www.omdbapi.com/?t=Criminal+Minds&Season=" + i + "&apikey=14991e95");
            /* convir cada una de las temporadas */
            var datosTemporadas = conversor.obtenerDatos(json, DatosTemporada.class);
            /* agregar a la lista */
            temporadas.add(datosTemporadas);
        }
        /* imprimir cada objeto del array */
        temporadas.forEach(System.out::println);
```