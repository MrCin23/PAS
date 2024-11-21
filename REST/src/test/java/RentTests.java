import com.mongodb.client.MongoIterable;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.lodz.p.DataInitializer;

public class RentTests {
    public static DataInitializer dataInitializer = new DataInitializer();

    @BeforeAll
    public static void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/REST/api/rent";
//        dataInitializer.dropAndCreateRent();
//        dataInitializer.init();
    }

    @AfterEach
    public void dropCollection() {
        dataInitializer.dropAndCreateRent();
        dataInitializer.init();
    }

    @Test
    public void test(){
        Assertions.assertTrue(true);
    }
}
