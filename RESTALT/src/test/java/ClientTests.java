import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import pl.lodz.p.DataInitializer;
import pl.lodz.p.repository.ClientRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ClientTests {
    public static DataInitializer dataInitializer = new DataInitializer();

    @BeforeEach
//    public static void init() {
    public void initCollection() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8081;
        RestAssured.basePath = "/REST/api/client";
        dataInitializer.dropAndCreateClient();
        dataInitializer.initClient();
    }

//    @AfterEach
//    public void dropCollection() {
//        dataInitializer.dropAndCreateClient();
//        dataInitializer.initClient();
//    }

    @Test
    public void testCreateClient() throws JsonProcessingException {
        String payloadJson = """
                {
                  "firstName": "John",
                  "surname": "Doe",
                  "username": "johndoe",
                  "emailAddress": "john.doe@example.com",
                  "clientType": {
                    "maxRentedMachines": 5,
                    "name": "Standard",
                    "_clazz": "standard"
                  },
                  "currentRents": 0,
                  "active": true
                }""";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payloadJson)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("firstName", equalTo("John"))
                .body("surname", equalTo("Doe"))
                .body("username", equalTo("johndoe"))
                .body("emailAddress", equalTo("john.doe@example.com"));
    }

    @Test
    public void testGetAllClients() {
        RestAssured.given()
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0)); // Sprawdza, czy lista klientów nie jest pusta
    }

    @Test
    public void testGetClientByUUID() {
        String payloadJson = """
                {
                    "entityId": {
                        "uuid": "123e4567-e89b-12d3-a456-426614174000"
                    },
                    "firstName": "John",
                    "surname": "Doe",
                    "username": "JDoe",
                    "emailAddress": "john.doe@example.com",
                    "clientType": {
                        "_clazz": "standard",
                        "entityId": {
                            "uuid": "5bd23f3d-0be9-41d7-9cd8-0ae77e6f463d"
                        },
                        "maxRentedMachines": 5,
                        "name": "Standard"
                    },
                    "currentRents": 0,
                    "active": true
                }""";
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payloadJson)
                .when()
                .post()
                .then()
                .statusCode(201);

        UUID uuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174000"); // Przykładowy UUID

        RestAssured.given()
                .when()
                .get("/{uuid}", uuid)
                .then()
                .statusCode(200)
                .body("entityId.uuid", equalTo(uuid.toString()));
    }

    @Test
    public void testUpdateClient() {
        String payloadJson = """
                {
                    "entityId": {
                        "uuid": "123e4567-e89b-12d3-a456-426614174000"
                    },
                    "firstName": "John",
                    "surname": "Doe",
                    "username": "JDoe",
                    "emailAddress": "john.doe@example.com",
                    "clientType": {
                        "_clazz": "standard",
                        "entityId": {
                            "uuid": "5bd23f3d-0be9-41d7-9cd8-0ae77e6f463d"
                        },
                        "maxRentedMachines": 5,
                        "name": "Standard"
                    },
                    "currentRents": 0,
                    "active": true
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
        fieldsToUpdate.put("emailAddress", "new.email@example.com");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(fieldsToUpdate)
                .when()
                .put("/{uuid}", uuid)
                .then()
                .statusCode(204);
    }

    @Test
    public void testDeactivateClient() {
        String payloadJson = """
                {
                    "entityId": {
                        "uuid": "123e4567-e89b-12d3-a456-426614174000"
                    },
                    "firstName": "John",
                    "surname": "Doe",
                    "username": "JDoe",
                    "emailAddress": "john.doe@example.com",
                    "clientType": {
                        "_clazz": "standard",
                        "entityId": {
                            "uuid": "5bd23f3d-0be9-41d7-9cd8-0ae77e6f463d"
                        },
                        "maxRentedMachines": 5,
                        "name": "Standard"
                    },
                    "currentRents": 0,
                    "active": true
                }""";
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payloadJson)
                .when()
                .post()
                .then()
                .statusCode(201);
        UUID uuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174000"); // Przykładowy UUID

        RestAssured.given()
                .when()
                .put("/deactivate/{uuid}", uuid)
                .then()
                .statusCode(204);
    }

    @Test
    public void testActivateClient() {
        String payloadJson = """
                {
                    "entityId": {
                        "uuid": "123e4567-e89b-12d3-a456-426614174000"
                    },
                    "firstName": "John",
                    "surname": "Doe",
                    "username": "JDoe",
                    "emailAddress": "john.doe@example.com",
                    "clientType": {
                        "_clazz": "standard",
                        "entityId": {
                            "uuid": "5bd23f3d-0be9-41d7-9cd8-0ae77e6f463d"
                        },
                        "maxRentedMachines": 5,
                        "name": "Standard"
                    },
                    "currentRents": 0,
                    "active": true
                }""";
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payloadJson)
                .when()
                .post()
                .then()
                .statusCode(201);
        UUID uuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174000"); // Przykładowy UUID

        RestAssured.given()
                .when()
                .put("/activate/{uuid}", uuid)
                .then()
                .statusCode(204);
    }

    @Test
    public void testFindClientByUsername() {
        String payloadJson = """
                {
                    "entityId": {
                        "uuid": "123e4567-e89b-12d3-a456-426614174000"
                    },
                    "firstName": "John",
                    "surname": "Doe",
                    "username": "JDoe",
                    "emailAddress": "john.doe@example.com",
                    "clientType": {
                        "_clazz": "standard",
                        "entityId": {
                            "uuid": "5bd23f3d-0be9-41d7-9cd8-0ae77e6f463d"
                        },
                        "maxRentedMachines": 5,
                        "name": "Standard"
                    },
                    "currentRents": 0,
                    "active": true
                }""";
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payloadJson)
                .when()
                .post()
                .then()
                .statusCode(201);
        String username = "JDoe";

        RestAssured.given()
                .when()
                .get("/findClient/{username}", username)
                .then()
                .statusCode(200)
                .body("username", equalTo(username));
    }

    @Test
    public void testFindClientsByUsername() {
        String payloadJson = """
                {
                    "entityId": {
                        "uuid": "123e4567-e89b-12d3-a456-426614174000"
                    },
                    "firstName": "John",
                    "surname": "Doe",
                    "username": "JDoe",
                    "emailAddress": "john.doe@example.com",
                    "clientType": {
                        "_clazz": "standard",
                        "entityId": {
                            "uuid": "5bd23f3d-0be9-41d7-9cd8-0ae77e6f463d"
                        },
                        "maxRentedMachines": 5,
                        "name": "Standard"
                    },
                    "currentRents": 0,
                    "active": true
                }""";
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payloadJson)
                .when()
                .post()
                .then()
                .statusCode(201);
        String username = "JDoe";

        RestAssured.given()
                .when()
                .get("/findClients/{username}", username)
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("username", everyItem(equalTo(username)));
    }

    @Test
    public void testIncorrectCreateClient(){
        String payloadJson = """
                {
                  "firstName": "John",
                  "surname": "Doe",
                  "username": "johndoe",
                  "emailAddress": "john.doe@example.com",
                }""";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payloadJson)
                .when()
                .post()
                .then()
                .statusCode(400);
    }

    @Test
    public void testDuplicateUUIDRejection() {
        String payloadJson = """
                {
                    "entityId": {
                        "uuid": "2abc9e5d-3d2f-42e7-b90b-e7c61f662da3"
                    },
                    "firstName": "John",
                    "surname": "Doe",
                    "username": "johndoe",
                    "emailAddress": "john.doe@example.com",
                    "clientType": {
                        "_clazz": "standard",
                        "entityId": {
                            "uuid": "f8a34079-809e-459b-b76f-f25a02c064c6"
                        },
                        "maxRentedMachines": 5,
                        "name": "Standard"
                    },
                    "currentRents": 0,
                    "active": true
                }""";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payloadJson)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("firstName", equalTo("John"))
                .body("surname", equalTo("Doe"))
                .body("username", equalTo("johndoe"))
                .body("emailAddress", equalTo("john.doe@example.com"));

        payloadJson = """
                {
                    "entityId": {
                        "uuid": "2abc9e5d-3d2f-42e7-b90b-e7c61f662da3"
                    },
                    "firstName": "John",
                    "surname": "Doe",
                    "username": "johndoe",
                    "emailAddress": "john.doe@example.com",
                    "clientType": {
                        "_clazz": "standard",
                        "entityId": {
                            "uuid": "f8a34079-809e-459b-b76f-f25a02c064c6"
                        },
                        "maxRentedMachines": 5,
                        "name": "Standard"
                    },
                    "currentRents": 0,
                    "active": true
                }""";
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payloadJson)
                .when()
                .post();
        response.then().statusCode(409);

        String responseBody = response.getBody().asString();
        assertThat(responseBody, containsString("Client with id 2abc9e5d-3d2f-42e7-b90b-e7c61f662da3 already exists"));
    }

    @Order(1)
    @Test
    public void testDuplicateUsernameRejection() {
        String payloadJson = """
                {
                    "firstName": "John",
                    "surname": "Doe",
                    "username": "johndoe",
                    "emailAddress": "john.doe@example.com",
                    "clientType": {
                        "_clazz": "standard",
                        "maxRentedMachines": 5,
                        "name": "Standard"
                    },
                    "currentRents": 0,
                    "active": true
                }""";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payloadJson)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("firstName", equalTo("John"))
                .body("surname", equalTo("Doe"))
                .body("username", equalTo("johndoe"))
                .body("emailAddress", equalTo("john.doe@example.com"));


        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payloadJson)
                .when()
                .post();
        response.then().statusCode(409);

        String responseBody = response.getBody().asString();
        assertThat(responseBody, containsString("Client with username johndoe already exists! Error code: com.mongodb.MongoWriteException: Write operation error on server mongodb1:27017. Write error: WriteError{code=11000, message='E11000 duplicate key error collection: vmrental.clients index: username_1 dup key: { username: \"johndoe\" }', details={}}."));
    }
}