# LiterAlura

Este é um projeto de demonstração para cumprir o challenge literAlura, desenvolvido pela Alura e a Oracle para o programa Oracle Next Education (ONE).

## Estrutura do Projeto

O projeto segue a estrutura padrão do Maven:

- ``src/main/java``: Contém o código-fonte do projeto.
- ``src/main/resources``: Contém os arquivos de propriedades e recursos.

O ponto de entrada do aplicativo é a classe `AluraApplication`.

## Dependências

As dependências do projeto são gerenciadas pelo Maven e estão listadas no arquivo `pom.xml`. As principais dependências incluem:

- **Spring Boot Starter Data JPA:** Fornece suporte para a persistência de dados usando o Spring Data e o Hibernate.
- **PostgreSQL:** Driver JDBC para o banco de dados PostgreSQL.
- **Jackson Databind:** Biblioteca para serialização/deserialização de objetos Java para/de JSON.

## Serviços

O projeto contém um serviço para consumir APIs, implementado na classe `ConsumoAPI`.

## Modelos

O projeto define vários modelos de dados, incluindo `Autor`, `Dados`, `DadosAutor` e `DadosLivro`.

## Como Executar

Para executar o projeto, use o seguinte comando no terminal:

```sh
./mvnw spring-boot:run
```

## Configuração

A configuração do aplicativo é gerenciada pelo arquivo `application.properties`.

## Ignorando Arquivos

O arquivo `.gitignore` especifica os arquivos e diretórios que o Git deve ignorar.


## Licença

Este projeto é licenciado sob os termos da licença MIT.
