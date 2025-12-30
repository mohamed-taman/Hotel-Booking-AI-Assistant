package rs.siriusxi.hbca.ui;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.RequiredArgsConstructor;
import rs.siriusxi.hbca.service.HotelBookingDetails;
import rs.siriusxi.hbca.service.HotelBookingService;

import java.util.List;

@BrowserCallable
@AnonymousAllowed
@RequiredArgsConstructor
public class HotelBookingUIService {

    private final HotelBookingService hotelBookingService;

    public List<HotelBookingDetails> getBookings() {
        return hotelBookingService.getBookings();
    }
}
