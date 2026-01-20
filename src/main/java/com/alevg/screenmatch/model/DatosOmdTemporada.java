package com.alevg.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosOmdTemporada(
        @JsonAlias("Season") Integer numero,
        @JsonAlias("Episodes") List<DatosOmdEpisodio> episodios
) {
}
