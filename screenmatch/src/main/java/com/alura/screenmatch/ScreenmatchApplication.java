package com.alura.screenmatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alura.screenmatch.Principal.Principal;
import com.alura.screenmatch.repository.SerieRepositiry;

/* para trabajar con spring es nesesario implemntar
 *
 * CommandLineRunner
 */

@SpringBootApplication
public class ScreenmatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScreenmatchApplication.class, args);
    }

    /* clase de control */

    
}
