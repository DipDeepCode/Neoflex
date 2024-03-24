package ru.ddc.vacationpaycalculator;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.junit.jupiter.api.BeforeEach;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpStatus.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VacationPayCalculatorApplicationTests {

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    void contextLoads() {
    }

    @Test
    void givenRequestWithoutVacationStartDate_whenSend_thenStatusOkAndResultEquals() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("averageSalary", "51666.66667")
                .queryParam("vacationDuration", "12")
                .when()
                .get("/calculate")
                .then()
                .statusCode(OK.value())
                .body("result", equalTo(18409.55F));
    }

    @Test
    void givenRequestWithVacationStartDate_whenSend_thenStatusOkAndResultEquals() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("averageSalary", "71428.57142")
                .queryParam("vacationDuration", "10")
                .queryParam("vacationStartDate", "2024-05-02")
                .when()
                .get("/calculate")
                .then()
                .statusCode(OK.value())
                .body("result", equalTo(19088.25F));
    }

    @Test
    void givenRequestWithoutRequiredParams_whenSend_thenStatus() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/calculate")
                .then()
                .statusCode(BAD_REQUEST.value());
    }
}
