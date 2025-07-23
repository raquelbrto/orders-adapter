# Desafio

API REST desenvolvida com Java 17 e Spring Boot, a API recebe um arquivo de
pedidos desnormalizado e retorna um json normalizado seguindo os padrões dos requisitos do desafio.

## Tecnologias Utilizadas

* Java 17
* Spring
* MongoDB
* Redis
* Docker
* JUnit
* Mockito

## Variáveis de Ambiente

- Java: JDK 17.
- Git: Para clonar o repositório.
- Docker: Para rodar a aplicação em um container.

##  Configuração do Projeto

1. Clone o Repositório

        git clone git@github.com:raquelbrto/orders-adapter.git

2. Acesse o diretorio

        cd orders-adapter

3. Crie a imagem docker

   ```bash
   docker build -t orders-adapter .
   ```

4. Execute

   ```bash
   docker compose up --build
   ```   
# Docker hub

Executando com a imagem do docker hub, projeto disponivel em: [`rakeobrto/orders-adapter`](https://hub.docker.com/r/rakeobrto/orders-adapter)

  ```bash
  docker run rakeobrto/orders-adapter:latest
  ```

# Documentação da API

## Orders

### Processa arquivo e retornar pedidos normalizados

Recebe o arquivo de dados desnormalizado e retorna a resposta em json contendo os dados dos pedidos normalizados. 

| Parâmetro  | Tipo             | Descrição                           |
   |:-----------|:-----------------| :---------------------------------- |
| `file`     | `txt`            | **Obrigatório**. Arquivo de texto de pedidos desnormalizado.

```bash
curl --request POST \
  --url http://localhost:8085/api/v1/orders \
  --header 'Content-Type: multipart/form-data' \
  --form file=@/caminho_do_arquivo/data_1.txt
```

Exemplo de resposta:

```json
[
  {
    "user_id": 1,
    "name": "Sammie Baumbach",
    "orders": [
      {
        "order_id": 2,
        "total": 2966.46,
        "date": "2021-10-28",
        "products": [
          {
            "product_id": 2,
            "value": 798.03
          },
          {
            "product_id": 5,
            "value": 1567.00
          },
          {
            "product_id": 2,
            "value": 601.43
          }
        ]
      },
      {
        "order_id": 9,
        "total": 3164.43,
        "date": "2021-04-14",
        "products": [
          {
            "product_id": 1,
            "value": 1465.30
          },
          {
            "product_id": 1,
            "value": 973.27
          },
          {
            "product_id": 2,
            "value": 725.86
          }
        ]
      },
      {
        "order_id": 10,
        "total": 2739.77,
        "date": "2021-06-23",
        "products": [
          {
            "product_id": 4,
            "value": 590.04
          },
          {
            "product_id": 3,
            "value": 499.87
          },
          {
            "product_id": 3,
            "value": 1649.86
          }
        ]
      }
    ]
  },
  {
    "user_id": 2,
    "name": "Augustus Aufderhar",
    "orders": [
      {
        "order_id": 17,
        "total": 274.31,
        "date": "2021-07-14",
        "products": [
          {
            "product_id": 3,
            "value": 274.31
          }
        ]
      },
      {
        "order_id": 18,
        "total": 538.18,
        "date": "2021-07-13",
        "products": [
          {
            "product_id": 1,
            "value": 538.18
          }
        ]
      },
      {
        "order_id": 19,
        "total": 1548.98,
        "date": "2021-09-02",
        "products": [
          {
            "product_id": 4,
            "value": 1548.98
          }
        ]
      }
    ]
  }
]
```

### Find by id

Buscar o pedido pelo id.

| Parâmetro | Tipo  | Descrição                           |
   |:----------|:------| :---------------------------------- |
| `id`      | `int` | **Obrigatório**. Id do pedido.

```bash
curl --request GET \
  --url http://localhost:8085/api/v1/orders/2 \
```
Exemplo de resposta:

```json
{
	"order_id": 2,
	"total": 2966.46,
	"date": "2021-10-28",
	"products": [
		{
          "product_id": 2,
          "value": 798.03
		},
		{
          "product_id": 5,
          "value": 1567.00
		},
		{
          "product_id": 2,
          "value": 601.43
		}
	],
	"user_id": 1,
	"name": "Sammie Baumbach"
}
```

### List

Retorna todos os pedidos se nâo for passado o filtro de intervalo de data de compra(start date e end date)

| Parâmetro | Tipo   | Descrição                           |
   |:----------|:-------| :---------------------------------- |
| `start_date`      | `Date` | **Opcional**. Data de início no formato `yyyy-MM-dd`.
| `end_date`      | `Date` | **Opcional**. Data fim no formato `yyyy-MM-dd`.

```bash
curl --request GET \
  --url 'http://localhost:8085/api/v1/orders?start_date=2020-01-01&end_date=2025-09-01' \
```
Exemplo de resposta:

```json
[
  {
    "user_id": 1,
    "name": "Sammie Baumbach",
    "orders": [
      {
        "order_id": 2,
        "total": 2966.46,
        "date": "2021-10-28",
        "products": [
          {
            "product_id": 2,
            "value": 798.03
          },
          {
            "product_id": 5,
            "value": 1567.00
          },
          {
            "product_id": 2,
            "value": 601.43
          }
        ]
      },
      {
        "order_id": 9,
        "total": 3164.43,
        "date": "2021-04-14",
        "products": [
          {
            "product_id": 1,
            "value": 1465.30
          },
          {
            "product_id": 1,
            "value": 973.27
          },
          {
            "product_id": 2,
            "value": 725.86
          }
        ]
      },
      {
        "order_id": 10,
        "total": 2739.77,
        "date": "2021-06-23",
        "products": [
          {
            "product_id": 4,
            "value": 590.04
          },
          {
            "product_id": 3,
            "value": 499.87
          },
          {
            "product_id": 3,
            "value": 1649.86
          }
        ]
      }
    ]
  },
  {
    "user_id": 2,
    "name": "Augustus Aufderhar",
    "orders": [
      {
        "order_id": 17,
        "total": 274.31,
        "date": "2021-07-14",
        "products": [
          {
            "product_id": 3,
            "value": 274.31
          }
        ]
      },
      {
        "order_id": 18,
        "total": 538.18,
        "date": "2021-07-13",
        "products": [
          {
            "product_id": 1,
            "value": 538.18
          }
        ]
      },
      {
        "order_id": 19,
        "total": 1548.98,
        "date": "2021-09-02",
        "products": [
          {
            "product_id": 4,
            "value": 1548.98
          }
        ]
      }
    ]
  }
]
```

## Swagger

Acesse a documentação em: [`swagger-ui/index.html`](http://localhost:8085/swagger-ui/index.html)

## Analises

Durante a leitura e entendimento do desafio e dos arquivos percebi, alem de que cada linha contem uma parte do pedido 
logo sendo necessario agrupar os produtos dos pedidos pelo orderId, que nos pedidos existem produtos com o mesmo id 
mas com preços diferentes, logo foi decidido nao salvar produto como documento nem usuario, API vai guardar so os pedidos como documento no MongoDB e 
retornar os dados normalizados. É possivel vizualizar um pouco de como foi pensado o desenvolvimento pelas issues.

Foram feitos testes unitarios e de integração, é possivel ver o relatorio dos testes baixando o artefato jacoco-report gerado 
pelo git hub actions ou quando rodar o projeto localmente.
