package rs.siriusxi.hbca.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rs.siriusxi.hbca.domain.Booking;
import rs.siriusxi.hbca.ui.dto.HotelBookingDetail;

import static org.mapstruct.MappingConstants.ComponentModel.*;

/**
 * Maps {@link Booking} entities to {@link HotelBookingDetail} DTOs
 * and handles the transformation of relevant fields.
 * <p>
 * This interface is a MapStruct mapper, which automates the conversion
 * of objects during the application's runtime. The mapping configuration
 * is defined using annotation-based mappings.
 * </p>
 *
 * Responsibilities:
 * - Extracts nested properties from {@link Booking} (e.g., customer details)
 *   and maps them to flat structures in {@link HotelBookingDetail}.
 * - Provides a single method for converting a {@link Booking} entity
 *   into a {@link HotelBookingDetail} DTO.
 *
 * Configuration Notes:
 * - MapStruct manages the implementation of this interface at compile-time.
 * - The {@code @Mapper} annotation with the {@code componentModel} set
 *   to SPRING ensures that the generated implementation is managed as a
 *   Spring Component.
 */
@Mapper(componentModel = SPRING)
public interface BookingDetailsMapper {

    @Mapping(target = "lastName", source = "customer.lastName")
    @Mapping(target = "firstName", source = "customer.firstName")
    HotelBookingDetail bookingToHotelBookingDetail(Booking booking);
}
