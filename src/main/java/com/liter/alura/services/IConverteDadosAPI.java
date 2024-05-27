package com.liter.alura.services;

public interface IConverteDadosAPI {
    <T> T converte(String json, Class<T> classe);
}
