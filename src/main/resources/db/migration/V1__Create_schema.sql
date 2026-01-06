CREATE TABLE customers (
    id BIGINT AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    CONSTRAINT CUSTOMER_ID_PK PRIMARY KEY (id)
);

CREATE TABLE bookings (
    id BIGINT AUTO_INCREMENT,
    hotel_name VARCHAR(255) NOT NULL,
    booking_number VARCHAR(255) NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    customer_id BIGINT NOT NULL,
    room_type ENUM('SINGLE', 'DOUBLE', 'TRIPLE', 'SUITE') NOT NULL,
    number_of_guests INT NOT NULL,
    booking_status ENUM('CONFIRMED', 'COMPLETED', 'CANCELLED') NOT NULL,
    CONSTRAINT BOOKING_ID_PK PRIMARY KEY (id),
    CONSTRAINT BOOKING_NUMBER_UQ UNIQUE (booking_number),
    CONSTRAINT BOOKING_CUSTOMER_ID_FK FOREIGN KEY (customer_id) REFERENCES customers(id)
);
