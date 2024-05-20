package br.com.fiap.mslogistica.controller;

import br.com.fiap.mslogistica.util.EntregaHelper;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class EntregaControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Nested
    class RegistrarEntrega {

        @Test
        void devePermitirRegistrarEntrega (){

            var entrega = EntregaHelper.gerarEntrega();

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(entrega)
//                    .log().all()
                    .when()
                    .post("/entregas")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(
                            "schemas/entrega.schema.json"));
        }

    }

    @Nested
    class BuscarEntrega {

        @Test
        void devePermitirBuscarEntrega() throws Exception {

            var id = 101L;
            when()
                    .get("/entregas/{id}", id)
                    .then()
                    .statusCode(HttpStatus.OK.value());
        }

        @Test
        void deveGerarExcecao_QuandoBuscarEntrega_IdNaoExiste() throws Exception {

            var id = 11L;
            when()
                    .get("/entregas/{id}", id)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    class AlterarEntrega {

        @Test
        void devePermitirAlterarEntrega() throws Exception {

            var entrega = EntregaHelper.gerarEntrega();
            entrega.setPedidoId(15L);
            entrega.setId(101L);

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(entrega)
                    .when()
                    .put("/entregas")
                    .then()
                    .statusCode(HttpStatus.ACCEPTED.value())
                    .body(matchesJsonSchemaInClasspath(
                            "schemas/entrega_update.schema.json"));
        }

        @Test
        void deveGerarExcecao_QuandoAlterarEntrega_IdNaoExiste() throws Exception {

            var entrega = EntregaHelper.gerarEntrega();
            entrega.setId(2001L);

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(entrega)
                    .when()
                    .put("/entregas")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(equalTo("Entidade Entrega não encontrada."));
        }
    }

    @Nested
    class RemoverEntrega {

        @Test
        void devePermitirRemoverEntrega() throws Exception {

            var id = 102L;

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .delete("/entregas/{id}", id)
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(equalTo("Entrega Removida"));
        }

        @Test
        void deveGerarExcecao_QuandoRemoverEntregador_IdNaoExiste() throws Exception {
            var id = 2001L;

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .delete("/entregas/{id}", id)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(equalTo("Entidade Entrega não encontrada."));
        }

    }

    @Nested
    class ListarEntrega {

        @Test
        void devePermitirListarEntrega() throws Exception {

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .queryParams("page", "0")
                    .queryParams("size", "10")
                    .when()
                    .get("/entregas")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("schemas/lista_entrega.schema.json"));
        }
    }
}
