package tests;

import io.restassured.response.Response;
import models.Booking;
import models.PostBookingResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testdata.BookingTestData;

public class BookingApiTests extends BookingApiBaseTest {

    @BeforeEach
    public void postBookingAndGetId() {
        response = bookingSteps.createBooking().as(PostBookingResponse.class);
        bookingId = bookingSteps.retrieveIdFromResponse(response);
    }

    @Test
    public void verifyBookingCreated() {
        Assertions.assertEquals(BookingTestData.defaultBooking().getLastname(), response.getBooking().getLastname());
        Assertions.assertEquals(BookingTestData.defaultBooking().getFirstname(), response.getBooking().getFirstname());
        Assertions.assertTrue(bookingId > 0);
    }

    @Test
    public void findRecordByBookingId() {
        Booking getBooking = bookingSteps.getBookingById(bookingId).as(Booking.class);
        Assertions.assertAll("verify correct booking record returned",
                () -> Assertions.assertEquals(response.getBooking(), getBooking),
                () -> Assertions.assertTrue(bookingId > 0));
    }

    @Test
    public void bookingSuccessfullyRemoved() {
        bookingSteps.removeBooking(bookingId, token);
        Response response = bookingSteps.getBookingById(bookingId);
        Assertions.assertAll("verify user has been removed",
                () -> Assertions.assertEquals(404, response.getStatusCode()),
                () -> Assertions.assertEquals("Not Found", response.getBody().asString()));
    }

    @Test
    public void updateBooking() {
        bookingSteps.updateBooking(token, bookingId);
        Booking getBooking = bookingSteps.getBookingById(bookingId).as(Booking.class);
        Assertions.assertEquals(BookingTestData.updatedUser, getBooking);
    }

    @Test
    public void partiallyUpdateBooking() {
        bookingSteps.partialUpdate(token, bookingId);
        Booking getBooking = bookingSteps.getBookingById(bookingId).as(Booking.class);
        Assertions.assertAll("verify user has been partially updated",
                () -> Assertions.assertEquals(BookingTestData.partialBookingUpdate().get("firstname"), getBooking.getFirstname()),
                () -> Assertions.assertEquals(BookingTestData.partialBookingUpdate().get("totalprice"), getBooking.getTotalprice().toString()));
    }
}
