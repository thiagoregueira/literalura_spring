package com.liter.alura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(

    @JsonAlias("title") String titulo,
    @JsonAlias("authors") List<DadosAutor> autores,
    @JsonAlias("subjects") List<String> temas,
    @JsonAlias("languages") List<String> idiomas,
    @JsonAlias("download_count") double quantidadeDownloads

) {
    
}
