# Challenge Customer App

Aplicativo Android desenvolvido como desafio técnico para exibir uma lista de clientes consumida por uma API.

O projeto foi construído com foco em organização, separação de responsabilidades, tratamento de erros e boas práticas de desenvolvimento Android.

## Funcionalidades

- Listagem de clientes
- Exibição de nome, e-mail, telefone e status
- Carregamento de imagens com placeholder
- Visualização da imagem do cliente em tela cheia
- Abertura do perfil do cliente em uma WebView
- Tratamento de links e imagens indisponíveis
- Estados de carregamento, sucesso e erro
- Tratamento de falhas de conexão
- Opção de tentar novamente após um erro
- Navegação entre telas com Jetpack Navigation
- Testes unitários

## Tecnologias utilizadas

- Kotlin
- Jetpack Compose
- Material 3
- MVVM
- Clean Architecture
- Coroutines
- StateFlow
- Retrofit
- OkHttp
- Coil
- Koin
- Navigation Compose
- JUnit
- MockK
- Turbine

## Arquitetura

O projeto foi organizado em camadas para facilitar a manutenção, os testes e a evolução da aplicação.

```text
com.joaoneres.uolchallenge
├── core
├── data
│   ├── mapper
│   ├── remote
│   └── repository
├── di
├── domain
│   ├── model
│   └── repository
├── presentation
│   ├── components
│   ├── customerlist
│   ├── image
│   ├── navigation
│   └── webview
└── ui
    └── theme