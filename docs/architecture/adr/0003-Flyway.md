# Migração de banco de dados
<!-- https://github.com/joelparkerhenderson/architecture-decision-record/tree/main/locales/en/templates/decision-record-template-by-michael-nygard -->
## Status

Aceito

## Contexto

Gerenciar migrações de bancos de dados é um trabalho difícil.

## Decisão

Utilizar o flyway nos permite gerenciar as migrações do banco de dados com facilidade.

## Consequências

Adicionamos uma dependência ao projeto. Por outro lado, os arquivos de migração servem de documentação e facilitam a
criação de novos bancos de dados.