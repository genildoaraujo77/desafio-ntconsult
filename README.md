## DOC DESAFIIO_NTCONSULT
# Apresentação da API criada com a linguagem Java e Spring Boot para efetuar criação de sessões de votação para pautas de uma Assembleia

# Requisitos para execução da API
- Fazer dowload ou clone da aplicação e importar o projeto para sua IDE de preferencia
- Ter no mínimo o java 8 instalado
- Ter mysql 8 instalado (configurar no application.properties as configurações de acesso ao banco) 

# Pautas
Esta sessão representa todas as requisições disponíveis para atuar nas pautas.

# Criar pauta
## POST http://localhost:8080/pautas
Nesta requisição podemos criar uma pauta de cada vez.
# Body raw (json)
## JSON
{
  "nome": "Pauta 5",
  "descricao": "teste pauta 5"
}

# Listar pautas
## GET http://localhost:8080/pautas
Nesta requisição podemos listar todas as pautas.
# Request Headers
### Accept                          application/json

# Buscar pauta
## GET http://localhost:8080/pautas/3
Nesta requisição podemos buscar uma pauta de cada vez incluindo o seu id como parametro na URL.

# Atualizar pauta
## PUT http://localhost:8080/pautas/1
Nesta requisição podemos atualizar os dados de uma pauta de cada vez.
# Body raw (json)
## JSON
{
  "nome": "Pauta 11",
  "descricao": "teste pauta 11"
}

# Excluir pauta
## DEL http://localhost:8080/pautas/4
Nesta requisição podemos excluir uma pauta de cada vez.
# Associados
Esta sessão representa todas as requisições disponíveis para atuar com os associados.

# Criar associado
## POST http://localhost:8080/associados
Nesta requisição podemos criar os associados que vão votar nas sessões conforme permissão.
# Body raw (json)
## JSON
{
  "nome": "Paulo Almeida",
  "cpf": "55547572004"
}

# Listar associados
## GET http://localhost:8080/associados
Nesta requisição podemos listar todos os associados.
# Request Headers
Accept                                     application/json

#Buscar associado
## GET http://localhost:8080/associados/4
Nesta requisição podemos listar um dos associados a partir do seu id.

# Atualizar associado
## PUT http://localhost:8080/associados/2
Nesta requisição podemos atualizar os dados de um associado.
# Body raw (json)
## JSON
{
  "nome": "Genildo Gonçalves de Lima Araujo",
  "cpf": "12345678911"
}

# Excluir associado
## DEL http://localhost:8080/associados/1
Nesta requisição podemos excluir um associado especifico passando o seu id.

# Sessao de Votação
Chegamos nas requisições onde se é possível abrir uma sessão de votação.

# Abrir sessao para uma pauta
## POST http://localhost:8080/sessoes-votacao
Nesta requisição podemos abrir uma sessão para uma determinada pauta.
# Body raw (json)
## JSON
{
  "pauta": {
    "id": "5"
  },
  "tempoSessaoVotacao": 20
}

# Listar sessões
## GET http://localhost:8080/sessoes-votacao
Nesta requisição podemos listar todas as sessões de votação, estando elas abertas ou finalizadas, sendo que o resultado da votação só aparecerá quando a sessão estiver com status de FINALIZADA.
# Request Headers
Accept                                application/json

# Buscar sessao
## GET http://localhost:8080/sessoes-votacao/4
Nesta requisição podemos buscar uma sessão de votação especifica passando o seu id.

# Votar na pauta
## POST http://localhost:8080/sessoes-votacao/5/votos
Nesta requisição podemos votar na pauta que estiver em um sessão aberta de acordo com as validações de cpf do associado e permissão para votar ou não votar.
# Body raw (json)
## JSON
{
  "simounao": false,
  "associado": {
    "cpf": "55547572004"
  },
  "pauta": {
    "id": "5"
  }
}

# Listar votos na pauta
## GET http://localhost:8080/sessoes-votacao/5/votos
Nesta requisição podemos listar todos os votos em uma pauta.

# Conclusão
Está é uma documentação de teste que demonstra todos os EndPoints existentes na API de Votação de Pautas por meio de sessões abertas para somente uma pauta por sessão.
Até o seguinte momento só foi possível implementar o desafio principal solicitado e a Tarefa Bônus 1 - Integração com sistemas externos.

# Foram criadas as classes java abaixo para realização de testes seguindo os padrões TDD do Junit
## AssociadosControllerTest.java, PautasControllerTest.java, SessaoVotacaoControllerTest 
Essas classes representam o possíveis testes de funcionamento nas respectivas função de cadas classe no projeto real.

# Tarefas Bônus - Não Concluídas
● Tarefa Bônus 2 - Mensageria e filas
● Tarefa Bônus 3 - Performance
● Tarefa Bônus 4 - Versionamento da API