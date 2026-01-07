package rs.siriusxi.hbca.ui;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.RequiredArgsConstructor;
import rs.siriusxi.hbca.ui.dto.HotelBookingDetails;
import rs.siriusxi.hbca.service.HotelBookingService;

import java.util.List;

/**
 * Service class that provides functionalities for managing hotel bookings
 * through the UI layer. It acts as a bridge between the UI (frontend)
 * and the underlying business logic by delegating operations to
 * {@link HotelBookingService}.
 *
 * This class is intended to be accessed by browser clients and supports
 * anonymous access. It is marked as callable using the {@code @BrowserCallable}
 * annotation.
 *
 * Responsibilities:
 * - Fetches a list of hotel bookings using the {@link HotelBookingService}.
 * - Provides a read-only view of booking details to the UI layer.
 *
 * Annotations:
 * - {@code @BrowserCallable}: Marks methods as callable from the browser.
 * - {@code @AnonymousAllowed}: Permits access by unauthenticated users.
 * - {@code @RequiredArgsConstructor}: Generates a constructor to inject dependencies.
 */
@BrowserCallable
@AnonymousAllowed
@RequiredArgsConstructor
public class HotelBookingUIService {

    private final HotelBookingService hotelBookingService;

    public List<HotelBookingDetails> getBookings() {
        return hotelBookingService.getBookings();
    }
}
