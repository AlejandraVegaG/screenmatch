package com.alevg.screenmatch.principal;

import com.alevg.screenmatch.model.DatosOmdEpisodio;
import com.alevg.screenmatch.model.DatosOmdTemporada;
import com.alevg.screenmatch.model.DatosOmdbSerie;
import com.alevg.screenmatch.service.ConsumoAPI;
import com.alevg.screenmatch.service.ConvierteDatos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private final String URL_API_OMDB = "http://www.omdbapi.com/?t=";
    private String API_KEY = "&apikey=";
    private ConvierteDatos conversor = new ConvierteDatos();

    public void muestraMenu() throws IOException {
        // Recibir nombre de la serie
        System.out.println("Escriba el nombre de la serie a buscar: ");
        var nombreSerie = scanner.nextLine();

        // Obtener la API Key
        Properties props = new Properties();
        props.load(new FileInputStream("config.properties"));
        this.API_KEY = this.API_KEY +  props.getProperty("API_KEY") ;

        // Llamar a la API OMDB
        var consumoApi = new ConsumoAPI();

        // Obtener datos de la serie
        var json = consumoApi.obtenerDatos(this.URL_API_OMDB + nombreSerie.replace(" ", "+") + this.API_KEY);
        var datos = conversor.obtenerDatos(json, DatosOmdbSerie.class);

        // Obtener todas las temporadas de la serie
        List<DatosOmdTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i <= datos.totalTemporadas(); i++) { //datos es de la serie
            json = consumoApi.obtenerDatos(this.URL_API_OMDB + nombreSerie.replace(" ", "+") + "&Season=" + i + API_KEY);
            var datosTemporadas = conversor.obtenerDatos(json, DatosOmdTemporada.class);
            temporadas.add(datosTemporadas);
        }
        temporadas.forEach(System.out::println);

        // Mostrar solo el titulo de los episodios de cada temporada
        /*
        for (int i = 0; i < datos.totalTemporadas(); i++) {
            List<DatosOmdEpisodio> episodiosTemporada = temporadas.get(i).episodios();
            for (int j = 0; j < episodiosTemporada.size(); j++) {
                System.out.println(episodiosTemporada.get(j).titulo());
            }
        }
         */ // Equivale en lambda
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
    }
}
