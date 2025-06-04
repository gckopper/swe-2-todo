# Escolha da arquitetura do projeto
<!-- https://github.com/joelparkerhenderson/architecture-decision-record/tree/main/locales/en/templates/decision-record-template-by-michael-nygard -->
## Status

Proposto

## Contexto

Como serão estruturadas as classes java para manter um projeto claro e de facil manutenibilidade.

## Decisão

### Package application.controller
Define os endpoints do projeto

### Package domain.model
Define as regras de negócio. Tanto entidades quanto relação entre entidades.

### Package domain.repository
Define interfaces(ports) que devem ser implementadas fora do dominio(adapters),
assim modularizando a comunicação com a camada de persistência.

### Package domain.service
Define interfaces(ports) que devem ser implementadas no package domain.model
relacionadas as regras de negócio que a aplicação possui

### Package infrastructure.repository
Define adapters dos repositories definidos no domain utilizando algum framework como JPA.

## Consequências

Modularização e manutenibilidade do sistema.
