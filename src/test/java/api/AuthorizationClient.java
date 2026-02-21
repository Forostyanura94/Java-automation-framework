package api;

import constants.Constants;
import config.TestConfig;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import testdata.BookingTestData;

public class AuthorizationClient {
    static String baseURI = TestConfig.get("baseURI");

    public static String getAuthToken() {
        return RestAssured.given()
                .baseUri(baseURI)
                .contentType(ContentType.JSON)
                .body(BookingTestData.adminUser())
                .when()
                .post(Constants.authEndpoint).jsonPath().getString("token");
    }
}
