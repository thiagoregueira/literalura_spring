package com.liter.alura.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDadosAPI implements IConverteDadosAPI {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T converte(String json, Class<T> classe) {
        try {
            return objectMapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    
}
