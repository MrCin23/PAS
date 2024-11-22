import com.mongodb.client.MongoIterable;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.lodz.p.DataInitializer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class RentTests {
    public static DataInitializer dataInitializer = new DataInitializer();

    @BeforeAll
    public static void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/REST";
//        dataInitializer.dropAndCreateRent();
//        dataInitializer.init();
    }

    @AfterEach
    public void dropCollection() {
        dataInitializer.dropAndCreateRent();
        dataInitializer.init();
    }

    @Test
    public void deleteLater() {
        Assertions.assertTrue(true);
    }

    @Test
    public void testCreateRent() {
        //create client
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
                .post("/api/client")
                .then()
                .statusCode(201)
                .body("firstName", equalTo("John"))
                .body("surname", equalTo("Doe"))
                .body("username", equalTo("johndoe"))
                .body("emailAddress", equalTo("john.doe@example.com"));
        //create vm
        String payloadJson2 = """
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
                .body(payloadJson2)
                .when()
                .post("/api/vmachine")
                .then()
                .statusCode(201)
                .body("_clazz", equalTo("applearch"))
                .body("ramSize", equalTo("4GB"))
                .body("cpunumber", equalTo(4));
        //create rent
        String payloadJson3 = """
                {
                    "clientId": "2abc9e5d-3d2f-42e7-b90b-e7c61f662da3",
                    "vmId": "7ab44a0b-8347-41cb-a64a-452666d0494a",
                    "startTime": "2024-11-11T11:11"
                }
                """;

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payloadJson3)
                .when()
                .post("/api/rent");
        response.then().statusCode(201);

        String responseBody = response.getBody().asString();
        assertThat(responseBody, containsString("2abc9e5d-3d2f-42e7-b90b-e7c61f662da3"));
        assertThat(responseBody, containsString("7ab44a0b-8347-41cb-a64a-452666d0494a"));
        assertThat(responseBody, containsString("[2024,11,11,11,11]"));
    }

    @Test
    public void testRentRented(){
        //create client
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
                .post("/api/client")
                .then()
                .statusCode(201)
                .body("firstName", equalTo("John"))
                .body("surname", equalTo("Doe"))
                .body("username", equalTo("johndoe"))
                .body("emailAddress", equalTo("john.doe@example.com"));
        //create vm
        String payloadJson2 = """
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
                .body(payloadJson2)
                .when()
                .post("/api/vmachine")
                .then()
                .statusCode(201)
                .body("_clazz", equalTo("applearch"))
                .body("ramSize", equalTo("4GB"))
                .body("cpunumber", equalTo(4));
        //create rent
        String payloadJson3 = """
                {
                    "clientId": "2abc9e5d-3d2f-42e7-b90b-e7c61f662da3",
                    "vmId": "7ab44a0b-8347-41cb-a64a-452666d0494a",
                    "startTime": "2024-11-11T11:11"
                }
                """;

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payloadJson3)
                .when()
                .post("/api/rent");
        response.then().statusCode(201);

        String payloadJson4 = """
                {
                    "clientId": "2abc9e5d-3d2f-42e7-b90b-e7c61f662da3",
                    "vmId": "7ab44a0b-8347-41cb-a64a-452666d0494a",
                    "startTime": "2024-12-12T12:12"
                }
                """;
        String responseBody = response.getBody().asString();
        assertThat(responseBody, containsString("2abc9e5d-3d2f-42e7-b90b-e7c61f662da3"));
        assertThat(responseBody, containsString("7ab44a0b-8347-41cb-a64a-452666d0494a"));
        assertThat(responseBody, containsString("[2024,11,11,11,11]"));
        //create rent of rented machine
        Response response2 = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payloadJson4)
                .when()
                .post("/api/rent");
        response2.then().statusCode(500);

        String responseBody2 = response2.getBody().asString();
        assertThat(responseBody2, containsString("Request processing failed: java. lang. RuntimeException: VMachine already rented"));
    }
}
