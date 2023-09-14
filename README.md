# Compass_challenge_III

O projeto consiste na criação de 3 microserviços que permitem o registro de carros usando o microserviço ms-cars, o registro de races usando o ms-races e visualização do histórico de corridas usando o ms-history.

Para o desenvolvimento do projeto foi utilizado Java 17, Springboot 3.1.3, Maven, MongoDB, Open-feign e RabbitMQ.

As seguintes dependências foram usadas:
-Spring web
-MongoDB driver
-Spring Validation
-Spring dev tools
-Spring starter test
-Lombok
-ModelMapper
-Spring Rabbit
-Spring Open feign

## A Aplicação foi desenvolvida seguindo as seguintes regras:

Requisitos do Projeto:

- Três microsserviços são obrigatórios: ms-cars, ms-races, ms-history.

São obrigatórios:

- Documentação da API; favor especificar a abordagem escolhida.
- Uso do banco MongoDB em um ou mais serviços.
- Uso do OpenFeign.
- Uso do RabbitMQ.

É altamente recomendado:

- Uso do arquivo docker-compose.yml.
- Utilização do Swagger.
- Utilização do Kanban.
- Inclusão de documentação abrangente no README.

Funcionalidades do Microsserviço ms-cars:

- Não podem existir pilotos totalmente iguais.
- Deve haver apenas um CRUD.
- Não podem existir carros totalmente iguais.
- Os IDs precisam ser gerados automaticamente. Exemplo de estrutura JSON:

```json 
{
    "car": {
        "brand": "String",
        "model": "String",
        "pilot": {
            "name": "String",
            "age": Integer
        }, 
        "year": "Date"
}
```

Funcionalidades do Microsserviço ms-races:

- Deve consumir o ms-cars para obter no máximo 10 carros.
- Para que uma corrida aconteça, deve haver uma pista. Exemplo de estrutura JSON:

```json 
{
    "name": "String",
    "country": "String",
    "date": "Date"
}
```

- O aplicativo deve selecionar aleatoriamente de 3 a 10 carros.
- Um carro só pode ultrapassar o carro à sua frente.
- Deve enviar o resultado da corrida para uma fila RabbitMQ.

Funcionalidades do Microsserviço ms-history:

- Deve consumir a fila do ms-races e salvar no banco de dados.
- Deve incluir a data em que o registro foi inserido no banco de dados.
- Deve fornecer um endpoint para consultar a corrida por ID.
- Deve fornecer um endpoint para consultar todas as corridas.

Estrutura de Desenvolvimento e Formalização:

- Crie o repositório e inclua os instrutores no início do desenvolvimento.
- Forneça o link do repositório por e-mail.
- Respeite a data de entrega e envie um e-mail formalizando a entrega.

Estrutura de Código:

- Utilize o Git/GitHub com duas branches fixas: main e dev.
- Padronize os nomes das branches e informe o padrão escolhido.

Segurança e Qualidade:

- A segurança é opcional, mas recomendada.
- Requisito mínimo de 40% de cobertura nos testes.
- Valorização de uma cobertura de testes de mais de 70%.
- Foque em boas práticas e na legibilidade do código.

## Para o desenvolvimento do projeto a seguinte linha de raciocínio foi utilizada:

- No primeiro microserviço (ms-cars) um CRUD inicial se carros seria necessário, sendo possível criar um veículo com sua marca, modelo, ano, piloto e a idade do piloto. Em seguida seria possível visualizar a informação criada de maneira total e buscando pelo id do carro. Também seria possível editar o carro além de deletá-lo.
- No segundo microserviço (ms-races), um registro de carridas com seus respectivos carros seria necessário. Para isso de 3 a 10 carros aleatórios seriam selecionados de ms-cars usando o Open-feign para isso. Ainda nesse microserviço seria possível visualizar todas as corridas e visualizar individualmente por id.
Para finalizar as funcionalidades deste microserviço a opcão de realizar ultrapassagem de um veículo seria possível utilizando um endpoint para isso. E por fim seria possível finalizar a corrida para que a mesma fosse enviada para o próximo microserviço.
- No terceiro microserviço (ms-history) seria possível visualizar o resultado das corridas finalizadas usando 2 endpoints, um para ver todas as corridas e um para ver individualmente filtrando pelo id da corrida.

## Como rodar os microserviços:

1. É necessário fazer um import da collection do postman presente na pasta resources/docs no microserviço ms-cars.
2. Fazer a configuração do MongoDB nomeando o database como "compass_challenge_III", criando 3 collections: "car", "race" e "race_results".
3. fazer a configuração do RabbitMQ, instalando o programa e usando o usuário e senha como "guest". 
4. Iniciar os microserviços na seguinte ordem: ms-cars, ms-races, ms-history.
5. Criar um carro usando a seguinte requisição presente na collection do Postman -> "Post Car -> ms-cars". Isso criará um carro com todos od seus respectivos atributos. Assegure-se de criar ao menos 10 ou mais carro diferentes.
- POST localhost:8080/api/car
- INPUT:
- ```json
  {
    "brand" : "Car 1",
    "model": "Model 1",
    "pilot": {
        "name" : "Pilot 1",
        "age" : 40
    },
    "year" : "2010"
  }
  ```
- OUTPUT:
- ```json
  {
    "id": "64fb4fc420d0de63d8a93dbb",
    "brand": "Car 1",
    "model": "Model 1",
    "year": "2010",
    "pilot": {
        "name": "Pilot 1",
        "age": 40
    }
  }
  ```
6. Visualizar os carros criados usando a seguinte requisição -> "Get car by id -> ms-cars" para ver apenas um carro específico com seu id, ou para ver todos os veículos usar -> "Get all cars -> ms-cars". 
- GET localhost:8080/api/car/64fb4fc420d0de63d8a93dbb
- OUTPUT
- ```json
  {
    "id": "64fb4fc420d0de63d8a93dbb",
    "brand": "Car 1",
    "model": "Model 1",
    "year": "2010",
    "pilot": {
        "name": "Pilot 1",
        "age": 40
    }
  }
  ```
- GET localhost:8080/api/car
- OUTPUT:
- ```json
  {
    "id": "64fb4fc420d0de63d8a93dbb",
    "brand": "Car 1",
    "model": "Model 1",
    "year": "2010",
    "pilot": {
        "name": "Pilot 1",
        "age": 40
    }
  },
  {
    "id": "64fb4f4220d0de63d8a93db2",
    "brand": "Car 2",
    "model": "Model 2",
    "year": "2001",
    "pilot": {
        "name": "Pilot 2",
        "age": 31
    }
  }
  ....
  ```
7. Editar um veículo usando a seguinte requisição -> "Update car -> ms-cars". É necessário forneceer o id carro a ser editado.
- PUT localhost:8080/api/car/64fb4fc420d0de63d8a93dbb
- INPUT:
- ```json
  {
    "brand": "Ford",
    "model": "Mustang",
    "year": "2020",
    "pilot": {
        "name": "Anderson",
        "age": 38
    }
  }
  ```
- OUTPUT:
- ```json
  {
    "id": "64fb4fc420d0de63d8a93dbb",
    "brand": "Ford",
    "model": "Mustang",
    "year": "2020",
    "pilot": {
        "name": "Anderson",
        "age": 38
    }
  }
  ```
8. Deletar um carro usando -> "Delete car -> ms-cars". É necessário fornecer o id do carro a ser deletado.
- DELETE localhost:8080/api/car/64fb4fc420d0de63d8a93dbb
- OUTPUT: "Car deleted."
9. Criar uma corrida nova usando a requisição -> "Post Race -> ms-races". Esta requisição irá criar uma nova corrida selecionando aleatoriamente de 3 a 10 carros de ms-cars. E sua ordem será a ordem de largada na corrida. Note que a corrida assim que criada terá seu status "started", ou seja, terá sido iniciada.
- POST localhost:8090/api/race
- INPUT:
- ```json
  {
    "name" : "Lung-fuoh",
    "country" : "China",
    "date" : "21/06/2021"
  }
  ```
- OUTPUT:
- ```json
  {
    "id": "65009a194b87dc504ac15a02",
    "name": "Lung-fuoh",
    "country": "China",
    "date": "21/06/2021",
    "status": "started",
    "cars": [
        {
            "id": "64fb4f8b20d0de63d8a93db7",
            "brand": "Car 7",
            "model": "Model 7",
            "year": "2006",
            "pilot": {
                "name": "Pilot 7",
                "age": 36
            }
        },
        {
            "id": "64fb4f4220d0de63d8a93db2",
            "brand": "Car 2",
            "model": "Model 2",
            "year": "2001",
            "pilot": {
                "name": "Pilot 2",
                "age": 31
            }
        },
        {
            "id": "64fb4fa720d0de63d8a93db9",
            "brand": "Car 9",
            "model": "Model 9",
            "year": "2008",
            "pilot": {
                "name": "Pilot 9",
                "age": 38
            }
        }
    ]
  }
  ```
10. Visualizar as corridas, seja por id ou todas. Para visualizar todas usar a requisição -> "Get all races -> ms-races". Isso mostrará todas as corridas já criadas sem mostrar muitos detalhes. Para mais detalhes usar a requisição -> "Get race by id -> ms-races". Ela mostrará a corrida e seus respectivos carros. (É necessario ter acesso a esses carros oara utilizar a próxima requisição).
- GET localhost:8090/api/race
- OUTPUT:
- ```json
  [
    {
        "id": "650222507986b84483e26fd3",
        "name": "Race 1",
        "country": "EUA",
        "date": "20/02/2013",
        "status": "finished"
    },
    {
        "id": "65009a194b87dc504ac15a02",
        "name": "Lung-fuoh",
        "country": "China",
        "date": "21/06/2021",
        "status": "started"
    }
  ]
  ```
- GET localhost:8090/api/race/65009a194b87dc504ac15a02
- OUTPUT:
- ```json
  {
    "id": "65009a194b87dc504ac15a02",
    "name": "Lung-fuoh",
    "country": "China",
    "date": "21/06/2021",
    "status": "started",
    "cars": [
        {
            "id": "64fb4f8b20d0de63d8a93db7",
            "brand": "Car 7",
            "model": "Model 7",
            "year": "2006",
            "pilot": {
                "name": "Pilot 7",
                "age": 36
            }
        },
        {
            "id": "64fb4f4220d0de63d8a93db2",
            "brand": "Car 2",
            "model": "Model 2",
            "year": "2001",
            "pilot": {
                "name": "Pilot 2",
                "age": 31
            }
        },
        {
            "id": "64fb4fa720d0de63d8a93db9",
            "brand": "Car 9",
            "model": "Model 9",
            "year": "2008",
            "pilot": {
                "name": "Pilot 9",
                "age": 38
            }
        }
    ]
  }
  ```
11. Realizar ultrapassagens usando -> "Car overtake -> ms-races". Tendo o id da corrida e o id de um veículo, que não seja o primeiro, é possível realizar uma ultrapassagem. Utilize esse método quantas vezes quiser com ids de carros diferentes que estejam participando da corrida.
- PUT localhost:8090/api/race/65009a194b87dc504ac15a02/overtake/64fb4f4220d0de63d8a93db2
- OUTPUT:
- ```json
  {
    "id": "65009a194b87dc504ac15a02",
    "name": "Lung-fuoh",
    "country": "China",
    "date": "21/06/2021",
    "status": "started",
    "cars": [
        {
            "id": "64fb4f4220d0de63d8a93db2",
            "brand": "Car 2",
            "model": "Model 2",
            "year": "2001",
            "pilot": {
                "name": "Pilot 2",
                "age": 31
            }
        },
        {
            "id": "64fb4f8b20d0de63d8a93db7",
            "brand": "Car 7",
            "model": "Model 7",
            "year": "2006",
            "pilot": {
                "name": "Pilot 7",
                "age": 36
            }
        },
        {
            "id": "64fb4fa720d0de63d8a93db9",
            "brand": "Car 9",
            "model": "Model 9",
            "year": "2008",
            "pilot": {
                "name": "Pilot 9",
                "age": 38
            }
        }
    ]
  }
  ```
12. Assim que fizer todas as ultrapassagens é possível finalizar a corrida e enviá-la para uma fila do RabbitMQ usando a seguinte requisição -> "Finish race -> ms-races". Lembre de passar o id da corrida para finalizá-la. 
- PUT localhost:8090/api/race/65009a194b87dc504ac15a02/finish
- OUTPUT:
- ```json
  {
    "id": "65009a194b87dc504ac15a02",
    "name": "Lung-fuoh",
    "country": "China",
    "date": "21/06/2021",
    "status": "finished",
    "cars": [
        {
            "id": "64fb4f4220d0de63d8a93db2",
            "brand": "Car 2",
            "model": "Model 2",
            "year": "2001",
            "pilot": {
                "name": "Pilot 2",
                "age": 31
            }
        },
        {
            "id": "64fb4f8b20d0de63d8a93db7",
            "brand": "Car 7",
            "model": "Model 7",
            "year": "2006",
            "pilot": {
                "name": "Pilot 7",
                "age": 36
            }
        },
        {
            "id": "64fb4fa720d0de63d8a93db9",
            "brand": "Car 9",
            "model": "Model 9",
            "year": "2008",
            "pilot": {
                "name": "Pilot 9",
                "age": 38
            }
        }
    ]
  }
  ```
13. Visualizar as corridas finializadas, seja por id ou todas. Para visualizar todas usar -> "Get all races -> ms-history". E para visualizar por id usar -> "Get race by id -> ms-history".
- GET localhost:8091/api/history
- OUTPUT:
- ```json
  [
    {
        "id": "65009a194b87dc504ac15a02",
        "name": "Lung-fuoh",
        "country": "China",
        "date": "21/01/2021",
        "status": "finished",
        "dateOfInsertion": "12/09/2023"
    }
  ]
  ```
- GET localhost:8091/api/history/65009a194b87dc504ac15a02
- OUTPUT:
- ```json
  [
    {
        "id": "65009a194b87dc504ac15a02",
        "name": "Lung-fuoh",
        "country": "China",
        "date": "21/01/2021",
        "status": "finished",
        "cars": [
            {
                "id": "64fb4f4220d0de63d8a93db2",
                "brand": "Car 2",
                "model": "Model 2",
                "year": "2001",
                "pilot": {
                    "name": "Pilot 2",
                    "age": 31
                }
            },
            {
                "id": "64fb4f8b20d0de63d8a93db7",
                "brand": "Car 7",
                "model": "Model 7",
                "year": "2006",
                "pilot": {
                    "name": "Pilot 7",
                    "age": 36
                }
            },
            {
                "id": "64fb4fa720d0de63d8a93db9",
                "brand": "Car 9",
                "model": "Model 9",
                "year": "2008",
                "pilot": {
                    "name": "Pilot 9",
                    "age": 38
                }
            }
        ],
        "dateOfInsertion": "12/09/2023"
    }
  ]
  ```
## Testes

Para a parte de testes do projeto foi utilizado o Junit 5 em conjunto do Mockito.

## Considerações finais

O projeto não conta com validações extras que não foram expressamente requisitadas, ou seja, espere alguns métodos não retornando nada quando ocorre algum erro de utilização. 
É possível também criar objeto vazios, uma vez que validações para evitar isso não foram implementadas. 
Por falta de tempo e conhecimento o Docker não foi implementado no projeto. 
