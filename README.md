# Trabalho do grau B de engenharia de software: Arquitetura e padrões

## Descrição do trabalho

As equipes deverão desenvolver uma API para um sistema de Gestão de tarefas Colaborativas, permitindo que usuários
criem, editem, atribuam e concluam tarefas. A API deve seguir uma arquitetura bem definida (por exemplo, Arquitetura
Hexagonal, Clean Architecture ou MVC), garantindo boas práticas de desacoplamento e modularização.

## Developing

Inicie uma instância do postgres com o docker caso ainda não tenha uma rodando.

```bash
docker compose -f docker/postgresql-dev.yaml up
```

Inicie a aplicação.

```bash
mvn spring-boot:run
```

### Testes

Para rodar os testes utilize o comando abaixo.

```bash
mvn test
```