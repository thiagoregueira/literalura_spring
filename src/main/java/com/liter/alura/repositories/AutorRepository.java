package com.liter.alura.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.liter.alura.models.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long>{
    @Query("SELECT a FROM Autor a WHERE a.nome LIKE %:nome%")
    List<Autor> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT a.nome FROM Autor a WHERE a.anoNascimento <= :ano AND (a.anoFalecimento IS NULL OR a.anoFalecimento >= :ano)")
    List<String> findAutoresVivos(int ano);
}
