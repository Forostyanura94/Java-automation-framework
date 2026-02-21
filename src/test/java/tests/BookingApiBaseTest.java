package tests;

import api.AuthorizationClient;
import api.BookingSteps;
import models.PostBookingResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

public class BookingApiBaseTest {
    BookingSteps bookingSteps = new BookingSteps();
    protected int bookingId;
    public static String token;
    PostBookingResponse response;

    @BeforeAll
    public static void setUpToken() {
        token = AuthorizationClient.getAuthToken();
    }

    @AfterEach
    public void cleanUp() {
        if (bookingId != 0) {
            bookingSteps.removeBooking(bookingId, token);
        }
    }
}
