package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.PostBookingResponse;
import config.TestConfig;
import tests.BookingApiTests;

import static constants.Constants.*;
import static io.restassured.RestAssured.given;
import static testdata.BookingTestData.*;


public class BookingSteps {

    String baseURI = TestConfig.get("baseURI");

    @Step
    public Response createBooking(Object body) {
        return given(Specifications.requestSpecifications(baseURI, BookingApiTests.token))
                .body(body)
                .when()
                .post(bookingEndpoint)
                .andReturn();
    }

    @Step
    public Response createBooking() {
        return createBooking(defaultBooking());
    }

    @Step
    public Response getBookingById(int id) {
        return given(Specifications.requestSpecifications(baseURI, BookingApiTests.token))
                .when()
                .get(bookingEndpoint + "/" + id)
                .andReturn();
    }

    @Step
    public Response updateBooking(String token, int id, Object body) {
        return given(Specifications.requestSpecifications(baseURI, token))
                .cookie("token", token)
                .body(body)
                .when()
                .put(bookingEndpoint + "/" + id)
                .andReturn();
    }

    @Step
    public Response updateBooking(String token, int id) {
        return updateBooking(token, id, updatedUser);
    }

    @Step
    public Response removeBooking(int id, String token) {
        return given(Specifications.requestSpecifications(baseURI, token))
                .baseUri(baseURI)
                .when()
                .delete(bookingEndpoint + "/" + id)
                .andReturn();
    }

    @Step
    public Response partialUpdate(String token, int id) {
        return partialUpdate(token, id, partialBookingUpdate());
    }

    @Step
    public Response partialUpdate(String token, int id, Object body) {
        return given(Specifications.requestSpecifications(baseURI, token))
                .body(body)
                .when()
                .patch(bookingEndpoint + "/" + id)
                .andReturn();
    }

    @Step
    public Integer retrieveIdFromResponse(PostBookingResponse response) {
        return response.getBookingid();
    }
}
