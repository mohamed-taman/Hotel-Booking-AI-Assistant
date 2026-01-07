package rs.siriusxi.hbca.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import rs.siriusxi.hbca.service.HotelBookingService;

import java.util.function.Function;

/**
 * Configuration class defining function beans for hotel booking operations.
 *
 * This class is annotated with {@code @Configuration} to indicate
 * that it declares beans for the application context. The functions
 * defined in this class allow for the cancellation of bookings and
 * changing room types for existing bookings. It makes use of
 * {@link HotelBookingService} to perform these operations.
 *
 * Annotations:
 * - {@code @Configuration}: Marks the class as a configuration class.
 * - {@code @RequiredArgsConstructor}: Automatically generates a
 *   constructor for final fields, injecting the required dependencies.
 *
 * Nested Records:
 * - {@link CancelBookingRequest}: Encapsulates data required to process
 *   a booking cancellation request.
 * - {@link RoomTypeChangeRequest}: Encapsulates data required to request
 *   a change in the room type for an existing booking.
 */
@Configuration
@RequiredArgsConstructor
public class FunctionConfig {

    private final HotelBookingService hotelBookingService;

    public record CancelBookingRequest(String bookingNumber, String firstName, String lastName) {
    }

    public record RoomTypeChangeRequest(String bookingNumber, String firstName,
                                        String lastName, String roomType) {
    }

    @Bean
    @Description("Cancel booking")
    public Function<CancelBookingRequest, String> cancelBooking() {
        return request -> {
            hotelBookingService.cancelBooking(request.bookingNumber(),
                    request.firstName(), request.lastName());
            return "";
        };
    }

    @Bean
    @Description("Room type change request")
    public Function<RoomTypeChangeRequest, String> roomTypeChangeRequest() {
        return request -> {
            hotelBookingService.roomTypeChangeRequest(request.bookingNumber(),
                    request.firstName(), request.lastName(), request.roomType());
            return "";
        };
    }
}
