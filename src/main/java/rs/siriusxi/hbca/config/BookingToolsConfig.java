package rs.siriusxi.hbca.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import rs.siriusxi.hbca.service.HotelBookingService;
import rs.siriusxi.hbca.ui.dto.HotelBookingDetail;

import java.util.function.Function;

/**
 * Configuration class defining function beans for hotel booking operations.
 * <p>
 * This class is annotated with {@code @Configuration} to indicate
 * that it declares beans for the application context. The functions
 * defined in this class allow for finding booking details, cancelling
 * bookings, and changing room types for existing bookings. It makes use of
 * {@link HotelBookingService} to perform these operations.
 * <p>
 * The class provides three AI tool functions:
 * - {@link #findBooking()}: Retrieves booking details by booking number
 * - {@link #cancelBooking()}: Cancels an existing booking for a customer
 * - {@link #changeBookingRoomType()}: Changes the room type for an existing booking
 * <p>
 * Annotations:
 * - {@code @Configuration}: Marks the class as a configuration class.
 * - {@code @RequiredArgsConstructor}: Automatically generates a
 * constructor for final fields, injecting the required dependencies.
 * - {@code @Slf4j}: Provides logging capabilities through Lombok.
 * <p>
 * Nested Records:
 * - {@link FindBookingRequest}: Encapsulates data required to find
 * booking details by booking number.
 * - {@link CancelBookingRequest}: Encapsulates data required to process
 * a booking cancellation request.
 * - {@link ChangeRoomTypeRequest}: Encapsulates data required to request
 * a change in the room type for an existing booking.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class BookingToolsConfig {

    private static final String FIND_BOOKING_TOOL = "findBooking";
    private static final String CANCEL_BOOKING_TOOL = "cancelBooking";
    private static final String CHANGE_BOOKING_ROOM_TYPE_TOOL = "changeBookingRoomType";

    public static String[] aiToolsNames() {
        return new String[]{FIND_BOOKING_TOOL, CANCEL_BOOKING_TOOL, CHANGE_BOOKING_ROOM_TYPE_TOOL};
    }

    private final HotelBookingService hotelBookingService;

    public record CancelBookingRequest(String bookingNumber, String firstName, String lastName) {
    }

    public record ChangeRoomTypeRequest(String bookingNumber, String firstName,
                                        String lastName, String roomType) {
    }

    public record FindBookingRequest(@ToolParam(description = "The booking number") String bookingNumber) {
    }

    @Bean(FIND_BOOKING_TOOL)
    @Description("Request to find or get booking details for a customer by booking number")
    Function<FindBookingRequest, HotelBookingDetail> findBooking() {
        return request ->
                hotelBookingService
                        .findBooking(request.bookingNumber().trim());
    }

    @Bean(CANCEL_BOOKING_TOOL)
    @Description("Request to cancel booking for a customer")
    Function<CancelBookingRequest, String> cancelBooking() {
        return request -> {
            hotelBookingService.cancelBooking(request.bookingNumber(),
                    request.firstName(), request.lastName());
            return "";
        };
    }

    @Bean(CHANGE_BOOKING_ROOM_TYPE_TOOL)
    @Description("Request to change room type for a customer booking")
    Function<ChangeRoomTypeRequest, String> changeBookingRoomType() {
        return request -> {
            hotelBookingService.changeBookingRoomType(request.bookingNumber(),
                    request.firstName(), request.lastName(), request.roomType());
            return "";
        };
    }
}
