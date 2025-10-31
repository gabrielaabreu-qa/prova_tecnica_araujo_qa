*Resumo do projeto*

Projeto de testes automatizados da API pública Petstore Swagger utilizando Java 17 JUnit 5 e RestAssured com o gerenciador de dependências Maven

*Stack e setup*

-Projeto Maven com Java 17 JUnit 5 e RestAssured

-pom.xml inclui as dependências rest-assured json-path json-schema-validator junit-jupiter

-Plugin Surefire configurado para execução dos testes

-Classe base BaseApiTest centraliza a configuração da base URL https://petstore.swagger.io/v2

 *Organização do código*
 
-io.petstore.BaseApiTest configuração comum dos testes

-io.petstore.StoreOrderTests casos de /store/order

-io.petstore.PetTests casos de /pet GET inexistente PUT update findByStatus

 *Casos implementados*
 
-POST /store/order cria pedido com id petId quantity status placed complete true shipDate conferindo eco dos campos

-GET /pet/{petId} inexistente usa ID grande e valida 404 e mensagem de not found

-PUT /pet cria pet POST atualiza PUT e valida com GET que name e status persistem Inclui photoUrls para garantir persistência no serviço público

-GET /pet/findByStatus?status=pending valida 200 JSON e que itens se existirem têm status pending e id não nulo

 *Asserções e regras de negócio*
 
-Verificação de statusCode contentType e campos essenciais

-Tolerância à API pública criação aceita 200 ou 201

-Confirmação de persistência após update via GET

 *Massa de dados*
 
-orderId e petId aleatórios grandes para evitar colisão

-Pets com nomes claros ex filho_do_gabriel thor e transição de status available para pending

-photoUrls adicionados no POST PUT para compatibilidade com o modelo da Petstore

 *Como rodar*
 
-Para rodar todos os testes

mvn test

-Para rodar um método específico

mvn -Dtest="Classe#metodo" test

-Se estiver usando PowerShell use aspas no -Dtest por causa do #
