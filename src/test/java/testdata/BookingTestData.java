package testdata;

import config.TestConfig;
import models.Booking;
import models.BookingDates;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class BookingTestData {

    public static Stream<String> invalidTokens() {
        return Stream.of(
                "invalid",
                "bad",
                "123",
                "Bearer abc"
        );
    }

    public static Booking defaultBooking() {
        return Booking.builder()
                .firstname("John")
                .lastname("Doe")
                .totalprice(123)
                .depositpaid(true)
                .bookingdates(BookingDates.builder()
                        .checkin("2018-01-01")
                        .checkout("2019-01-01")
                        .build())
                .additionalneeds("Breakfast")
                .build();
    }

    public static Booking updatedUser = Booking.builder()
            .firstname("Some")
            .lastname("User")
            .totalprice(1059)
            .depositpaid(false)
            .bookingdates(BookingDates.builder()
                    .checkin("2026-10-12")
                    .checkout("2027-08-15")
                    .build())
            .additionalneeds("Dinner")
            .build();

    public static Map<String, Object> bookingUpdateMissingMandatoryFields() {
        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", "2019-01-01");
        bookingDates.put("checkout", "2019-02-01");

        Map<String, Object> bookingUpdate = new HashMap<>();
        bookingUpdate.put("firstname", "John");
        bookingUpdate.put("totalprice", 456);
        bookingUpdate.put("bookingdates", bookingDates);
        bookingUpdate.put("additionalneeds", "Breakfast");
        return bookingUpdate;
    }

    public static Booking bookingWithMissingFields() {
        return defaultBooking().toBuilder()
                .lastname(null)
                .totalprice(null)
                .build();
    }

    public static Map<String, Object> adminUser() {
        Map<String, Object> adminUser = new HashMap<>();
        adminUser.put("username", TestConfig.get("admin_username"));
        adminUser.put("password", TestConfig.get("admin_password"));
        return adminUser;
    }

    public static Map<String, Object> partialBookingUpdate() {
        Map<String, Object> bookingUpdate = new HashMap<>();
        bookingUpdate.put("firstname", "Josh");
        bookingUpdate.put("totalprice", "756");
        return bookingUpdate;
    }
}
