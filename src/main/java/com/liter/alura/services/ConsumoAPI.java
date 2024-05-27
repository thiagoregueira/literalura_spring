package com.liter.alura.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {
    public String consumirAPI(String url) {
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest requisicao = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> resposta = null;
        try {
            resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao consumir a API: " + e.getMessage());
        }
        String json = resposta.body();
        return json;
    }
    
}
