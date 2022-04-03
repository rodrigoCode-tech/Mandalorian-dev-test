package org.acme.redis;

import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class TarefaResoruceTest {

    @Test
    public void tarefaResoruceTests() {
        //delete uma tarefa se existir
        given()
                .accept(ContentType.JSON)
                .when()
                .delete("/api/tarefas/teste tarefa 1")
                .then()
                .statusCode(204);

        // teste criando uma tarefa sem informar o título
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"anotacao\":\"teste tarefa 1\"}")
                .when()
                .post("/api/tarefas")
                .then()
                .statusCode(400);

        // teste criando uma tarefa
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"titulo\":\"teste tarefa 1\",\"anotacao\":\"teste tarefa 1\"}")
                .when()
                .post("/api/tarefas")
                .then()
                .statusCode(201)
                .body("titulo", is("teste tarefa 1"))
                .body("anotacao", is("teste tarefa 1"));

        // teste criando uma tarefa que já existe
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"titulo\":\"teste tarefa 1\",\"anotacao\":\"teste tarefa 1\"}")
                .when()
                .post("/api/tarefas")
                .then()
                .statusCode(400);

        // teste atualizando uma tarefa sem informar o título
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"anotacao\":\"teste tarefa 1\"}")
                .when()
                .put("/api/tarefas")
                .then()
                .statusCode(400);

        // teste atualizando uma tarefa que não existe
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"titulo\":\"teste tarefa 2\",\"anotacao\":\"teste tarefa 2\"}")
                .when()
                .put("/api/tarefas")
                .then()
                .statusCode(404);

        // teste atualizando uma tarefa
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"titulo\":\"teste tarefa 1\",\"anotacao\":\"teste tarefa 1 update\"}")
                .when()
                .put("/api/tarefas")
                .then()
                .statusCode(204);

        //teste consultando uma tarefa
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/tarefas/teste tarefa 1")
                .then()
                .statusCode(200)
                .body("titulo", is("teste tarefa 1"))
                .body("anotacao", is("teste tarefa 1 update"));

        //teste consultando uma tarefa inexistente
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/tarefas/tarefa_kndkasjndasj34343")
                .then()
                .statusCode(404);

        //delete o teste tarefa 1
        given()
                .accept(ContentType.JSON)
                .when()
                .delete("/api/tarefas/teste tarefa 1")
                .then()
                .statusCode(204);
    }
}