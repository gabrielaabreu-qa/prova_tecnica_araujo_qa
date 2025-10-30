package io.petstore;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PetTests extends BaseApiTest {

    @Test
    @DisplayName("Pesquisar por um pet inexistente (GET /pet/{petId})")
    void shouldReturnNotFoundForNonExistentPet() {
        long nonExistingId = 9_999_999_999L;

        given()
        .when()
            .get("/pet/{petId}", nonExistingId)
        .then()
            .statusCode(404)
            .body("message", anyOf(containsStringIgnoringCase("not found"), notNullValue()));
    }

    @Test
    @DisplayName("Atualizar dados de um pet existente (PUT /pet)")
    void shouldUpdateExistingPet() {
        long petId = 1234567890;

        Map<String, Object> createBody = new HashMap<>();
        createBody.put("id", petId);
        createBody.put("name", "filho_do_gabriel");
        createBody.put("status", "available");
        createBody.put("photoUrls", List.of("https://example.com/filho.jpg"));

        given()
            .contentType(ContentType.JSON)
            .body(createBody)
        .when()
            .post("/pet")
        .then()
            .statusCode(anyOf(is(200), is(201)))
            .body("id", equalTo((int) petId))
            .body("name", equalTo("filho_do_gabriel"))
            .body("status", equalTo("available"));

        Map<String, Object> updateBody = new HashMap<>();
        updateBody.put("id", petId);
        updateBody.put("name", "thor");
        updateBody.put("status", "pending");
        updateBody.put("photoUrls", List.of("https://example.com/thor.jpg"));

        given()
            .contentType(ContentType.JSON)
            .body(updateBody)
        .when()
            .put("/pet")
        .then()
            .statusCode(200)
            .body("id", equalTo((int) petId))
            .body("name", equalTo("thor"))
            .body("status", equalTo("pending"));

        given()
        .when()
            .get("/pet/{petId}", petId)
        .then()
            .statusCode(200)
            .body("id", equalTo((int) petId))
            .body("name", equalTo("thor"))
            .body("status", equalTo("pending"));
    }

    @Test
    @DisplayName("Pesquisar por pets com status pending (GET /pet/findByStatus)")
    void shouldFindPetsByPendingStatus() {
        given()
            .queryParam("status", "pending")
        .when()
            .get("/pet/findByStatus")
        .then()
            .statusCode(200)
            .contentType(containsString("application/json"))
            .body("size()", greaterThanOrEqualTo(0))
            .body("id", everyItem(notNullValue()))
            .body("status", everyItem(equalTo("pending")));
    }
}


