package com.example.app.patient;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

  @LocalServerPort
  private int randomServerPort;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @BeforeEach
  void setup() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = randomServerPort;
  }

  @Test
  @Order(1)
  @DisplayName("Test reading and writing data to the database")
  void testReadingWithNoValues() {
    var result = RestAssured.given()
        .expect().statusCode(200)
        .when()
        .get("/")
        .then()
        .extract().as(PatientJson[].class);

    assertThat(result).isEmpty();
  }

  @Test
  @Order(2)
  @DisplayName("Test writing and reading an object with encrypted values")
  void testCreatingPatientWithPhiData( @Autowired PatientRepository patientRepository) {
    var patient = new PatientJson();
    patient.setSin("123-123-123");
    patient.setName("Adib");

    var result = RestAssured.given()
        .contentType("application/json; charset=utf-8")
        .body(patient)
        .expect().statusCode(200)
        .when()
        .post("/patients")
        .then()
        .extract().as(PatientJson.class);

    assertThat(result).isEqualToIgnoringGivenFields( result,"id");

    var sql = String.format("SELECT sin FROM patients WHERE id::text = '%s' ", result.getId());
    System.out.println(sql);
    var sin = jdbcTemplate.queryForObject(sql, String.class);
    System.out.println("sin in the jvm: "  + result.getSin());
    System.out.println("sin in the db: " + sin);

    assertThat(sin).isNotEqualTo(result.getId());
  }

}
