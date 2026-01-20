package com.alevg.screenmatch;

import com.alevg.screenmatch.model.DatosOmdEpisodio;
import com.alevg.screenmatch.model.DatosOmdTemporada;
import com.alevg.screenmatch.model.DatosOmdbSerie;
import com.alevg.screenmatch.principal.Principal;
import com.alevg.screenmatch.service.ConsumoAPI;
import com.alevg.screenmatch.service.ConvierteDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal();
        principal.muestraMenu();
    }
}
