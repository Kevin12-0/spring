package com.alura.screenmatch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.alura.screenmatch.Principal.Principal;


/* para trabajar con spring es nesesario implemntar
 *
 * CommandLineRunner
 */

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ScreenmatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal();
        principal.muestraElMenu();

        /*
         * Ejemplo ejemplo = new Ejemplo();
         * ejemplo.muestraEjemplo();
         */

    }

}
