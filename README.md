# Serviço de Lista de Desejos (Wishlist)
Este serviço gerencia uma Lista de Desejos dos clientes.

## Funcionalidades
- Adicionar um produto na Wishlist do cliente;
- Remover um produto da Wishlist do cliente;
- Consultar todos os produtos da Wishlist do cliente;
- Consultar se um determinado produto está na Wishlist do cliente;
- Limpa a lista.

## Notas
- Mais de um produto pode ser adicionado por requisição;
- A qualquer momento o cliente poderá acessar sua lista completa.
- Cada cliente poderá adicionar até 20 produtos (limite da lista)

# Pré-requisitos
Para executar este projeto, você precisará das seguintes ferramentas instaladas em sua máquina:

- Java 17 (JRE/JDK)
- Maven 3.6 ou superior

# Como executar o projeto
1. Clone o repositório em sua máquina local usando o seguinte comando:
``` bash
git clone https://github.com/silvioricardo87/service--wishlist.git
```

2. A partir do prompt de comando, execute o maven
```bash
mvn spring-boot:run`
```

# Documentação
- Acesse a documentação da API em `http://localhost:8080/swagger-ui.html`
