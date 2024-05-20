package br.com.fiap.mslogistica.controller;

import br.com.fiap.mslogistica.service.EntregadorService;
import br.com.fiap.mslogistica.util.EntregadorHelper;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
public class EntregadorControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Nested
    class RegistrarEntregador {

        @Test
        void devePermitirRegistrarEntregador (){

            var entregador = EntregadorHelper.gerarEntregador();

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(entregador)
//                    .log().all()
                    .when()
                    .post("/entregadores")
                    .then()
//                    .log().all()
                    .statusCode(HttpStatus.CREATED.value())
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(
                            "schemas/entregador.schema.json"));
        }

    }

    @Nested
    class BuscarEntregador {

        @Test
        void devePermitirBuscarEntregador() throws Exception {

            var id = 101L;
            when()
                    .get("/entregadores/{id}", id)
                    .then()
                    .statusCode(HttpStatus.OK.value());
        }

        @Test
        void deveGerarExcecao_QuandoBuscarEntregador_IdNaoExiste() throws Exception {

            var id = 11L;
            when()
                    .get("/entregadores/{id}", id)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    class AlterarEntregador {

        @Test
        void devePermitirAlterarEntregador() throws Exception {

            var entregador = EntregadorHelper.gerarEntregador();
            entregador.setId(101L);

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(entregador)
                    .when()
                    .put("/entregadores")
                    .then()
                    .statusCode(HttpStatus.ACCEPTED.value())
                    .body(matchesJsonSchemaInClasspath(
                            "schemas/entregador.schema.json"));
        }

        @Test
        void deveGerarExcecao_QuandoAlterarEntregador_IdNaoExiste() throws Exception {

            var entregador = EntregadorHelper.gerarEntregador();
            entregador.setId(2001L);

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(entregador)
                    .when()
                    .put("/entregadores")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(equalTo("Entidade Entregador não encontrada."));
        }
    }

    @Nested
    class RemoverEntregador {

        @Test
        void devePermitirRemoverEntregador() throws Exception {

            var id = 102L;

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .delete("/entregadores/{id}", id)
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(equalTo("Entregador Removido"));
        }

        @Test
        void deveGerarExcecao_QuandoRemoverEntregador_IdNaoExiste() throws Exception {
            var id = 2001L;

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .delete("/entregadores/{id}", id)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(equalTo("Entidade Entregador não encontrada."));
        }

    }

    @Nested
    class ListarEntregador {

        @Test
        void devePermitirListarEntregador() throws Exception {

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .queryParams("page", "0")
                    .queryParams("size", "10")
                    .when()
                    .get("/entregadores")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("schemas/lista_entregador.schema.json"));
        }
    }
}
