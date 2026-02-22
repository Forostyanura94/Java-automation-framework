package api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;


public class Specifications {
    public static RequestSpecification requestSpecifications(String url, String token) {
        return new RequestSpecBuilder()
                .setBaseUri(url)
                .addHeader("Cookie", "token=" + token)
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .build();
    }
}
