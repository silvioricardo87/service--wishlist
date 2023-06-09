# Arquitetura - Lista de Desejos

## Visão Geral
A arquitetura do projeto é baseada em uma abordagem de microserviços, permitindo escalabilidade, flexibilidade e manutenibilidade.

Faz utilização de cache para aprimorar o desempenho e reduzir a carga no banco de dados.

## Componentes Principais
1. **Serviço de Autenticação:** Responsável pela autenticação das requisições via Chave de API.

2. **Serviço de Lista de Desejos:** Gerencia o processamento e a manipulação itens da lista de desejos. Ele utiliza uma cache Redis para melhorar o desempenho na recuperação de dados frequentemente acessados.

3. **Página Web:** É a interface do usuário. Ele se comunica com a API REST por meio de requisições HTTPS autenticadas com chave de API.

## Diagrama de Arquitetura
A seguir está o diagrama de arquitetura que ilustra a estrutura e as interações dos componentes do projeto.

![Diagrama da Arquitetura](images/diagram.png)

## Comunicação Segura
Para garantir a segurança na comunicação entre a página web e a API RESTful, é utilizado o protocolo HTTPS. Isso garante a criptografia dos dados transmitidos e a autenticação do servidor por meio de certificados SSL/TLS.

## Persistência de Dados
O serviço de gerenciamento de usuários utiliza o banco de dados MongoDB para persistir as informações dos usuários. O MongoDB é um banco de dados NoSQL que oferece flexibilidade e escalabilidade para lidar com grandes volumes de dados.

## Cache com Redis
Para melhorar o desempenho na recuperação de dados frequentemente acessados, o serviço de processamento de pedidos utiliza o Redis como uma camada de cache. O Redis é um armazenamento em cache de alto desempenho, permitindo acesso rápido aos dados previamente consultados.

## Autenticação via API-KEY
A autenticação nas requisições é realizada por meio do uso de Chave de API que é validada a cada requisição para garantir a autorização adequada.
