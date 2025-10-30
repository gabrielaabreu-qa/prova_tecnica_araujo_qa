Resumo do projeto
Stack e setup
Projeto de testes Maven com Java 17, JUnit 5 e RestAssured.
pom.xml com dependências (rest-assured, json-path, json-schema-validator, junit-jupiter) e Surefire configurado.
BaseApiTest centraliza a base URL (https://petstore.swagger.io/v2).
Organização do código
io.petstore.BaseApiTest: configuração comum dos testes.
io.petstore.StoreOrderTests: casos de /store/order.
io.petstore.PetTests: casos de /pet (GET inexistente, PUT update, findByStatus).
Casos implementados
POST /store/order: cria pedido com id, petId, quantity, status="placed", complete=true, shipDate, conferindo eco dos campos.
GET /pet/{petId} (inexistente): usa ID grande e valida 404 e mensagem de “not found”.
PUT /pet: cria pet (POST), atualiza (PUT) e valida com GET que name e status persistem. Inclui photoUrls para garantir persistência no serviço público.
GET /pet/findByStatus?status=pending: valida 200, JSON e que itens (se existirem) têm status="pending" e id não nulo.
Asserções e regras de negócio
Verificação de statusCode, contentType e campos essenciais.
Tolerância à API pública: criação aceita 200 ou 201.
Confirmação de persistência após update via GET.
Massa de dados
orderId/petId aleatórios/grandes nos pedidos para evitar colisão.
Pets com name claros (ex.: “filho_do_gabriel”, “thor”) e transição de status (“available” → “pending”).
photoUrls adicionados no POST/PUT para compatibilidade com o modelo da Petstore.
Como rodar
Dentro de petstore-tests: mvn test (ou por método: mvn -Dtest="Classe#metodo" test).
Se usar PowerShell, use aspas no -Dtest por causa do #.
