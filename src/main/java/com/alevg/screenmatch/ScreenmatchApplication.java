package com.alevg.screenmatch;

import com.alevg.screenmatch.model.DatosOmdEpisodio;
import com.alevg.screenmatch.model.DatosOmdTemporada;
import com.alevg.screenmatch.model.DatosOmdbSerie;
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
        System.out.println("Hello there!");

        Properties props = new Properties();

        props.load(new FileInputStream("config.properties"));
        String API_KEY = props.getProperty("API_KEY");

        var consumoApi = new ConsumoAPI();
        var json = consumoApi.obtenerDatos("http://www.omdbapi.com/?t=icarly&apikey="+API_KEY);
        System.out.println(json);

        ConvierteDatos conversorDatos = new ConvierteDatos();
        var datos = conversorDatos.obtenerDatos(json, DatosOmdbSerie.class);
        System.out.println(datos);

        // Episodio
        json = consumoApi.obtenerDatos("http://www.omdbapi.com/?t=icarly&Season=1&episode=1&apikey="+API_KEY);
        var datosEpisodios = conversorDatos.obtenerDatos(json, DatosOmdEpisodio.class);

        System.out.println(datosEpisodios);

        // Temporadas
        List<DatosOmdTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i <= datos.totalTemporadas(); i++) { //datos es de la serie
            json = consumoApi.obtenerDatos("http://www.omdbapi.com/?t=icarly&Season=" + i + "&apikey=" + API_KEY);
            var datosTemporadas = conversorDatos.obtenerDatos(json, DatosOmdTemporada.class);
            temporadas.add(datosTemporadas);
        }
        temporadas.forEach(System.out::println);


    }
}
