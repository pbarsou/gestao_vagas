# Gestão de Vagas

## Sobre o projeto

Desenvolvimento de uma API REST para empresas criarem e gerenciarem suas vagas, enquanto os
possÍveis candidatos podem fazer o cadastro, visualizar vagas, se inscrever em uma vaga, visualizar
informações relevantes do seu perfil e vagas que se candidatou. 

## Tecnologias Utilizadas.

- **Linguagem de Programação:** Java 17.
- **Framework:** Spring (Spring Initializr).
- **Gerenciamento de Dependências:** Maven.
- **Banco de Dados:** PostgresSQL, HyperSql (Testes).
- **Cloud:** AWS, Azure
- **Ferramentas Adicionais:** Hibernate (JPA), Lombok, Docker, Kubernets, SonarQube, Jacoco, Prometheus e Grafana.

## Rodando o projeto


 1. `$ docker-compose -build -d`

- As configurações do container estão conngiguradas no arquivo `docker-compose.yml`.
- Uma vez o container montado, não é necessáio mais buildar: `$ docker-compose up -d`.

## Funcionalidades.

### Para Empresas:

- **Cadastro de novas vagas:** As empresas podem cadastrar novas vagas, fornecendo detalhes como descrição, benefícios e level.
- **Vizualização vagas cadastradas:** As empresas podem visualizar dados detalhados de uma vaga específica, incluindo a lista de candidatos inscritos.
- **Vizualização e atualização de perfil:** As empresas podem visualizar o seu perfil de inscrição, visualizando seus dados cadastrados, como nome, username, email, website e descrição, incluindo a lista de vagas já criadas. As empresas também são capazes de atualizar os dados do seu perfil.

### Para Participantes:

- **Se candidatar a uma vaga:** Os participantes podem se candidatar a uma ou mais vagas disponíveis.
- **Visualização de perfil:** Os participantes podem visualizar o seu perfil de inscrição, visualizando seus dados cadastrados, como nome, username, e-mail, descrição e a lista vagas que se candidatou. O candidato também é capaz de atualizar os dados do seu perfil.

## Regras de Negócio

-  Um candidato só pode se candidatar a uma vaga uma única vez.
-  Não deve ser possível criar um novo candidato com um mesmo username ou email já cadastrado.
-  Não deve ser possível criar uma empresa com um mesmo username ou email já cadastrado.

## Estrutura do Projeto.

- **Controllers:** Responsáveis por receber e processar as requisições HTTP.
- **Services:** Contêm a lógica de negócios da aplicação.
- **Repositories:** Responsáveis pela interação com o banco de dados.
- **DTOs (Data Transfer Objects):** Utilizados para transferir dados entre o cliente e o servidor.
- **Models:** Representam as entidades do banco de dados.
- **Exceptions Handling:** Tratamento de exceções personalizado para lidar com erros de forma adequada.
- **Security:**  Módulo de segurança da aplicação.

## Documentação

A documentação da aplicação foi criada utilziando o Swagger, uma das ferramentas mais utilziadas para a criação de documentação.

- Rota de acesso a documentação: `http://localhost:8080/swagger-ui/index.html`

## Testes Unitários e de Integração

Para a realização dos testes foi utilizado o JUnity + Mockito para realização dos Mocks.

Foi utilizado o H2, que é um banco em memória para realização do testes.

## Qualidade do Código

#### SonarQube + Jacoco

Para qualidade do código, foi utilizado o Sonar Qube, ferramenta de análise de código que auxilia na detecção de problemas que podem ocorrer no código, em conjunto com o Jacoco, que é uma ferramenta de análise de cobertura de código que nos ajuda a medir o quanto do código-fonte Java foi testado

- Disponível no endpoint: `localhost:9000`. Efetue o cadastro manualmente para gerar a chave de acesso. A chave tem prazo de validade.

Exemplo de configuração de login do SonarQube:

```no mvn cli:
 mvn clean verify sonar:sonar -Dsonar.projectKey=gestao_vagas -Dsonar.host.url=http://localhost:9000  -Dsonar.login=chave_de_acesso
```

## Monitoramento

Uso do Spring Actuator para monitoramento, junto do Prometheus e do Grafana, para vizualização de métricas da aplicação de forma visual.

#### Informações relevantes

- Acesso as métricas da aplicação pelo actuator:

```json
{
  "_links": {
    "self": {
      "href": "http://localhost:8080/actuator",
      "templated": false
    },
    "health-path": {
      "href": "http://localhost:8080/actuator/health/{*path}",
      "templated": true
    },
    "health": {
      "href": "http://localhost:8080/actuator/health",
      "templated": false
    },
    "prometheus": {
      "href": "http://localhost:8080/actuator/prometheus",
      "templated": false
    },
    "metrics": {
      "href": "http://localhost:8080/actuator/metrics",
      "templated": false
    },
    "metrics-requiredMetricName": {
      "href": "http://localhost:8080/actuator/metrics/{requiredMetricName}",
      "templated": true
    }
  }
}

```
- Acesso ao Prometheus: `localhost:9090`
- Acesso ao Grafana: `localhost:3000`

## Implantação

Inicialmente foi-se utilizado o Docker com o Azure Kubernets Service + Azure Container Registry, mas devido a expiração do uso gratuito, migrei a aplicação para a AWS, criando um RDS com PostgresSQL como base e uma EC2, na qual configurei um pipeline de CI/CD para conexão com o banco + implantação na EC2 a cada git push realizado.

## Principais Endpoints.

**Endpoints Livres:**

- **POST /candidate:** Cria um novo candidato.
- **POST /company:** Cria uma nova empresa.
- **GET /candidate/auth:** Cria um token de acesso para o candidato.
- **GET /company/auth:** Cria um token de acesso para a empresa.
- **GET /candidate/auth/admin:** Cria um token de acesso para o candidato no modo administrador.
- **GET /company/auth/admin:** Cria um token de acesso para a empresa no modo administrador.

**Endpoints que necessitam de autorização:**

- **POST /candidate/job/apply:** Realiza a candidatura do candidato a vaga.
- **POST /company/job/** Cria uma nova vaga.

### Demais endpoints que necessitam de autenticação.

### Principais Bibliotecas (Packages)

- [`Spring Boot`](https://spring.io/)
- [`Project Lombok`](https://projectlombok.org/)
- [`Docker`](https://www.docker.com/)
- [`PostgreSQL`](https://www.postgresql.org/)
- [`java-jwt`](https://github.com/auth0/java-jwt)
- [`Swagger`](https://swagger.io/)
- [`JUinit`](https://junit.org/junit4/)
- [`H2 Database`](https://www.h2database.com/html/main.html)

##

<h5 align="center">
  2024 - <a href="https://github.com/pbarsou/">Pablo Barbosa</a>
</h5>
