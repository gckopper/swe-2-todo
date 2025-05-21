# Escolha do banco de dados
<!-- https://github.com/joelparkerhenderson/architecture-decision-record/tree/main/locales/en/templates/decision-record-template-by-michael-nygard -->
## Status

Aceito

## Contexto

É um requisito funcional deste trabalho o uso de um banco de dados para manter a persistência das tarefas. O banco pode
ser tanto relacional quanto não relacional.

## Decisão

Como o time já possui experiência com o Postgres, que é um banco relacional, e ele é gratuito, o Postgres foi escolhido
para este projeto. Alguns bancos de dados exigem licenças dependendo da forma que são usados, mas o Postgres é FOSS e,
portanto, grátis.

## Consequências

A facilidade de deploy dele utilizando o docker nos permite utilizar um banco de dados real para testes locais e em
pipelines de CI.