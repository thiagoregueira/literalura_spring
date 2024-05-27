package com.liter.alura.Principal;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.liter.alura.models.Dados;
import com.liter.alura.models.DadosLivro;
import com.liter.alura.models.Livro;
import com.liter.alura.repositories.AutorRepository;
import com.liter.alura.repositories.LivrosRepository;
import com.liter.alura.services.ConsumoAPI;
import com.liter.alura.services.ConverteDadosAPI;


@Component
public class Principal implements CommandLineRunner {

    @Autowired
    private LivrosRepository livrosRepository;

    @Autowired
    private AutorRepository autorRepository;


    private Scanner scanner = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_API = "htttps://gutendex.com/books";
    private ConverteDadosAPI converteDadosAPI = new ConverteDadosAPI();


    @Override
    public void run (String... args) throws Exception {
        menu();
    }

    public void menu(){
        int opcao = 999;
        while (opcao != 0) {
            try {
                var menu = """
                        -------------------------
                        Escolha uma opção:
                        1 - Buscar livro por título
                        2 - Listar livros registrados
                        3 - Listar autores registrados
                        4 - Listar autores vivos em um determinado ano
                        5 - Buscar livros por idioma
                        0 - Sair
                        -------------------------
                        """;
                System.out.println(menu);
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1 -> buscarLivroPorTitulo();
                    case 2 -> listarLivros();
                    case 3 -> listarAutores();
                    case 4 -> listarAutoresVivos();
                    case 5 -> buscarLivrosPorIdioma();
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida");
                }
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida");
                scanner.nextLine();
            }
        }
    }

    private void buscarLivroPorTitulo() {
        while(true) {
            System.out.println("Digite o título do livro que deseja buscar:  , ou 0 para sair");
            var titulo = scanner.nextLine();

            if (titulo.equals("0")) {
                break;
            }

            if (livrosRepository.findByTituloContainsIgnoreCase(titulo).isPresent()) {
                System.out.println("Livro já registrado: " + livrosRepository.findByTituloContainsIgnoreCase(titulo).get());
                continue;
            }

            var json = consumoAPI.consumirAPI(URL_API + "?search=" + titulo.replace(" ", "+"));

            var dadosBuscados = converteDadosAPI.converte(json, Dados.class);
            if (dadosBuscados.resultados().isEmpty()) {
                System.out.println("Nenhum livro encontrado com o título informado");
                continue;
            }

            System.out.println("-------------------------");
            System.out.println("Livros encontrados:");
            for (int i = 0; i < Math.min(10, dadosBuscados.resultados().size()); i++) {
                System.out.println((i + 1) + ". " + dadosBuscados.resultados().get(i).titulo());
        }
        System.out.println("Deseja salvar algum livro? (Digite o número do livro ou 0 para sair)");
        int selecao = scanner.nextInt();
        scanner.nextLine();

        if (selecao == 0) {
            continue;
        }

        if (selecao > 0 && selecao <= dadosBuscados.resultados().size()) {
            DadosLivro livroSelecionado = dadosBuscados.resultados().get(selecao - 1);
            Livro livro = new Livro(livroSelecionado);

            if (livro.getIdiomas() == null || livro.getIdiomas().isEmpty()) {
                System.out.println("Livro não possui idioma informado");
            } else {
                detalhesLivro(livro);
                livrosRepository.save(livro);
                System.out.println("Livro salvo com sucesso");
            }
        } else {
            System.out.println("Opção inválida");
        }
        }
    }

    private void detalhesLivro(Livro livro) {
        System.out.println("-------------------------");
        System.out.println("Detalhes do livro:");
        System.out.println("Título: " + livro.getTitulo());
        System.out.println("Autores: ");
        livro.getAutores().forEach(autor -> System.out.println(autor.getNome()));
        System.out.println("Idiomas: ");
        livro.getIdiomas().forEach(System.out::println);
        System.out.println("Quantidade de downloads: " + livro.getQuantidadeDownloads());
        System.out.println("-------------------------");
    }

    private void listarLivros() {
        System.out.println("Livros registrados:");
        livrosRepository.findAll().forEach(livro -> detalhesLivro(livro));
    }

    private void listarAutores() {
        System.out.println("Autores registrados:");
        autorRepository.findAll().stream()
                .distinct()
                .forEach(autor -> System.out.println("Autor: " + autor.getNome()));
    }

    private void listarAutoresVivos() {
        System.out.println("Digite o ano para verificar autores vivos: ");
        try {
            var ano = scanner.nextInt();
            scanner.nextLine();

            var autoresVivos = autorRepository.findAutoresVivos(ano);
            if (autoresVivos.isEmpty()) {
                System.out.println("Nenhum autor vivo encontrado no ano informado");
            } else {
                System.out.println("Autores vivos no ano " + ano + ":");
                autoresVivos.forEach(System.out::println);
            }
        } catch (InputMismatchException e) {
            System.out.println("Ano inválido");
            scanner.nextLine();

        }
    }

    private void buscarLivrosPorIdioma() {
        System.out.println("Digite o idioma que deseja buscar: ES, EN, PT, FR");
        var idioma = scanner.nextLine().toLowerCase();

        var livroPorIdioma = livrosRepository.findLivrosByIdioma(idioma);
        if (livroPorIdioma.isEmpty()) {
            System.out.println("Nenhum livro encontrado com o idioma " + idioma);
        } else {
            System.out.println("Livros encontrados com o idioma " + idioma + ":");
            livroPorIdioma.forEach(livro -> detalhesLivro(livro));
        }
    }
    
}
