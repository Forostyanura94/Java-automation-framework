package api;

import constants.Constants;
import config.TestConfig;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import testdata.BookingTestData;

public class AuthorizationClient {

    public static String getAuthToken() {
        return RestAssured.given()
                .baseUri(TestConfig.get(Constants.BASE_URI))
                .contentType(ContentType.JSON)
                .body(BookingTestData.adminUser())
                .when()
                .post(Constants.authEndpoint).jsonPath().getString("token");
    }
}
