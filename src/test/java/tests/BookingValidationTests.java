package tests;

import io.qameta.allure.Issue;
import io.restassured.response.Response;
import models.Booking;
import models.PostBookingResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import testdata.BookingTestData;

public class BookingValidationTests extends BookingApiBaseTest {

    @Issue("DEFECT-001")
    @Disabled(" Known defect: missing validation for createBooking()")
    @Test
    public void createBookingWithMissingFields() {
        Response response = bookingSteps.createBooking(BookingTestData.bookingWithMissingFields());
        Assertions.assertAll("verify booking is not created",
                () -> Assertions.assertEquals(400, response.getStatusCode()),
                () -> Assertions.assertEquals("Bad Request", response.getBody().asString()));
    }

    @ParameterizedTest(name = "update booking with invalid tokens should return 403")
    @MethodSource("testdata.BookingTestData#invalidTokens")
    public void updateBookingWithInvalidToken(String token) {
        bookingId = bookingSteps.retrieveIdFromResponse(bookingSteps.createBooking().as(PostBookingResponse.class));
        Response response = bookingSteps.updateBooking(token, bookingId);
        Assertions.assertAll("verify booking has been updated",
                () -> Assertions.assertEquals(403, response.getStatusCode()),
                () -> Assertions.assertEquals("Forbidden", response.getBody().asString()));
    }

    @Test
    public void updateBookingWithoutToken() {
        bookingId = bookingSteps.retrieveIdFromResponse(bookingSteps.createBooking().as(PostBookingResponse.class));
        Response response = bookingSteps.updateBooking(null, bookingId);
        Assertions.assertAll("verify booking has not been updated",
                () -> Assertions.assertEquals(403, response.getStatusCode()),
                () -> Assertions.assertEquals("Forbidden", response.getBody().asString()));
    }

    @Test
    public void removeBookingWithoutToken() {
        bookingId = bookingSteps.retrieveIdFromResponse(bookingSteps.createBooking().as(PostBookingResponse.class));
        Response removeResponse = bookingSteps.removeBooking(bookingId, null);
        Assertions.assertEquals(403, removeResponse.getStatusCode());
        Response response = bookingSteps.getBookingById(bookingId);
        Assertions.assertAll("verify booking has not been removed",
                () -> Assertions.assertEquals(200, response.getStatusCode()),
                () -> Assertions.assertEquals(BookingTestData.defaultBooking(), response.getBody().as(Booking.class)));
    }

    @Test
    public void dataHasNotBeenChangedAfterUpdateBookingWithInvalidToken() {
        bookingId = bookingSteps.retrieveIdFromResponse(bookingSteps.createBooking().as(PostBookingResponse.class));
        bookingSteps.updateBooking("invalid", bookingId);
        Booking response = bookingSteps.getBookingById(bookingId).as(Booking.class);
        Assertions.assertEquals(BookingTestData.defaultBooking(), response);
    }

    @Test
    public void updateBookingWithNullMandatoryFields() {
        bookingId = bookingSteps.retrieveIdFromResponse(bookingSteps.createBooking().as(PostBookingResponse.class));
        Response response = bookingSteps.updateBooking(token, bookingId, BookingTestData.bookingWithMissingFields());
        Assertions.assertAll("verify booking has not been updated",
                () -> Assertions.assertEquals(400, response.getStatusCode()),
                () -> Assertions.assertEquals("Bad Request", response.getBody().asString()));
    }

    @Test
    public void updateBookingWithMissingMandatoryFields() {
        bookingId = bookingSteps.retrieveIdFromResponse(bookingSteps.createBooking().as(PostBookingResponse.class));
        Response response = bookingSteps.updateBooking(token, bookingId, BookingTestData.bookingUpdateMissingMandatoryFields());
        Assertions.assertAll("verify booking has not been updated",
                () -> Assertions.assertEquals(400, response.getStatusCode()),
                () -> Assertions.assertEquals("Bad Request", response.getBody().asString()));
    }

    @Test
    public void updateBookingNonExistingId() {
        Response response = bookingSteps.updateBooking(token, 99999);
        Assertions.assertAll("verify booking has not been updated",
                () -> Assertions.assertEquals(405, response.getStatusCode()),
                () -> Assertions.assertEquals("Method Not Allowed", response.getBody().asString()));
    }
}
