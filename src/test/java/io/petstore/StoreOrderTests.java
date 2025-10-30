package io.petstore;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class StoreOrderTests extends BaseApiTest {

    @Test
    @DisplayName("Cadastrar novo pedido de pet com sucesso (POST /store/order)")
    void shouldCreateNewOrderSuccessfully() {
        long orderId = ThreadLocalRandom.current().nextLong(1_000_000, 9_999_999);
        long petId = ThreadLocalRandom.current().nextLong(1_000_000, 9_999_999);

        Map<String, Object> body = new HashMap<>();
        body.put("id", orderId);
        body.put("petId", petId);
        body.put("quantity", 2);
        body.put("shipDate", OffsetDateTime.now().toString());
        body.put("status", "placed");
        body.put("complete", true);

        given()
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post("/store/order")
        .then()
            .statusCode(200)
            .contentType(containsString("application/json"))
            .body("id", equalTo((int) orderId))
            .body("petId", equalTo((int) petId))
            .body("quantity", equalTo(2))
            .body("status", equalTo("placed"))
            .body("complete", equalTo(true))
            .body("shipDate", notNullValue());
    }
}


