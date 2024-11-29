import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.lodz.p.DataInitializer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class VMachineTests {
    public static DataInitializer dataInitializer = new DataInitializer();

    @BeforeAll
    public static void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/REST/api/vmachine";
//        dataInitializer.dropAndCreateVMachine();
//        dataInitializer.initVM();
    }

    @AfterEach
    public void dropCollection() {
        dataInitializer.dropAndCreateVMachine();
        dataInitializer.initVM();
    }
    @Test
    public void testCreateVMachine() throws JsonProcessingException {
        String payloadJson = """
                {
                    "_clazz": "applearch",
                    "entityId": {
                        "uuid": "7ab44a0b-8347-41cb-a64a-452666d0494a"
                    },
                    "ramSize": "4GB",
                    "cpunumber": 4
                }""";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payloadJson)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("_clazz", equalTo("applearch"))
                .body("ramSize", equalTo("4GB"))
                .body("cpunumber", equalTo(4));
    }

    @Test
    public void testGetAllVMachines() {
        RestAssured.given()
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0)); // Ensure there are VMachine entries
    }

    @Test
    public void testGetVMachineByUUID() {
        String payloadJson = """
                {
                    "_clazz": "applearch",
                    "entityId": {
                        "uuid": "123e4567-e89b-12d3-a456-426614174000"
                    },
                    "ramSize": "8GB",
                    "cpunumber": 2
                }""";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payloadJson)
                .when()
                .post()
                .then()
                .statusCode(201);

        UUID uuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        RestAssured.given()
                .when()
                .get("/{uuid}", uuid)
                .then()
                .statusCode(200)
                .body("entityId.uuid", equalTo(uuid.toString()));
    }

    @Test
    public void testUpdateVMachine() {
        String payloadJson = """
                {
                    "_clazz": "applearch",
                    "entityId": {
                        "uuid": "123e4567-e89b-12d3-a456-426614174000"
                    },
                    "ramSize": "8GB",
                    "cpunumber": 2
                }""";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payloadJson)
                .when()
                .post()
                .then()
                .statusCode(201);

        UUID uuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        Map<String, Object> fieldsToUpdate = new HashMap<>();
        fieldsToUpdate.put("ramSize", "16GB");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(fieldsToUpdate)
                .when()
                .put("/{uuid}", uuid)
                .then()
                .statusCode(204);
    }

    @Test
    public void testDeleteVMachine() {
        String payloadJson = """
                {
                    "_clazz": "applearch",
                    "entityId": {
                        "uuid": "123e4567-e89b-12d3-a456-426614174000"
                    },
                    "ramSize": "8GB",
                    "cpunumber": 2
                }""";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payloadJson)
                .when()
                .post()
                .then()
                .statusCode(201);

        UUID uuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        RestAssured.given()
                .when()
                .delete("/{uuid}", "123e4567-e89b-12d3-a456-426614174000")
                .then()
                .statusCode(204);
    }

    @Test
    public void testIncorrectCreateVM(){
        String payloadJson = """
                {
                    "_clazz": "applearch",
                    "entityId": {
                        "uuid": "7ab44a0b-8347-41cb-a64a-452666d0494a"
                    }
                }""";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payloadJson)
                .when()
                .post()
                .then()
                .statusCode(400);
    }
}
