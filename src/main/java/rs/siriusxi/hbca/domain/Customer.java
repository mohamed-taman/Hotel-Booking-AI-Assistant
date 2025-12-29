package rs.siriusxi.hbca.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
public class Customer {
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;

    private List<Booking> bookings = new ArrayList<>();
}


