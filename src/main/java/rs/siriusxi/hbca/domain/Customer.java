package rs.siriusxi.hbca.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Customer {
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;

    private List<Booking> bookings = new ArrayList<>();
}


