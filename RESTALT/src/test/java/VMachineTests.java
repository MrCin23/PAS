import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;
import pl.lodz.p.DataInitializer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

@QuarkusTest
public class VMachineTests {
    @Inject
    DataInitializer dataInitializer;

    @BeforeEach
//    public static void init() {
    public void initCollection() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8081;
        RestAssured.basePath = "/REST/api/vmachine";
        dataInitializer.dropAndCreateVMachine();
//        dataInitializer.initVM();
    }

//    @AfterEach
//    public void dropCollection() {
//        dataInitializer.dropAndCreateVMachine();
//        dataInitializer.initVM();
//    }
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
                .statusCode(404);
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
                    "entityId": {
                        "uuid": "22222222-434f-4b54-a81f-4b1d190e654d"
                    },
                    "ramSize": "4GB",
                    "isRented": 1,
                    "actualRentalPrice": 400.0,
                    "cpunumber": 0,
                    "_clazz":"applearch"
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
