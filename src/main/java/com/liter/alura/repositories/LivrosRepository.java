package com.liter.alura.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.liter.alura.models.Livro;

public interface LivrosRepository extends JpaRepository<Livro, Long>{
    Optional<Livro> findByTituloContainsIgnoreCase(String titulo);

    List<Livro> findTop5ByOrderByQuantidadedeDownloadsDesc();

    @Query("SELLECT l FROM Livro l JOIN l.autores a WHERE a.nome LIKE %:nomeAutor%")
    List<Livro> findLivrosByAutorNome(String nomeAutor);

    @Query("SELECT l FROM Livro l JOIN l.idiomas i WHERE i = :idioma")
    List<Livro> findLivrosByIdioma(String idioma);
}
