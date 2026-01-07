INSERT INTO customers (first_name, last_name) VALUES ('Jack', 'Bauer');
INSERT INTO customers (first_name, last_name) VALUES ('Chloe', 'O''Brian');
INSERT INTO customers (first_name, last_name) VALUES ('Kim', 'Bauer');
INSERT INTO customers (first_name, last_name) VALUES ('David', 'Palmer');
INSERT INTO customers (first_name, last_name) VALUES ('Michelle', 'Dessler');

INSERT INTO bookings (hotel_name, booking_number, check_in_date, check_out_date, customer_id, room_type, number_of_guests, booking_status)
VALUES ('Marriot', '101', CURRENT_DATE, DATEADD('DAY', 2, CURRENT_DATE), 1, 'SINGLE', 2, 'CONFIRMED');

INSERT INTO bookings (hotel_name, booking_number, check_in_date, check_out_date, customer_id, room_type, number_of_guests, booking_status)
VALUES ('Hilton', '102', DATEADD('DAY', 2, CURRENT_DATE), DATEADD('DAY', 4, CURRENT_DATE), 2, 'DOUBLE', 2, 'CONFIRMED');

INSERT INTO bookings (hotel_name, booking_number, check_in_date, check_out_date, customer_id, room_type, number_of_guests, booking_status)
VALUES ('Sheraton', '103', DATEADD('DAY', 4, CURRENT_DATE), DATEADD('DAY', 6, CURRENT_DATE), 3, 'TRIPLE', 2, 'CONFIRMED');

INSERT INTO bookings (hotel_name, booking_number, check_in_date, check_out_date, customer_id, room_type, number_of_guests, booking_status)
VALUES ('Westin', '104', DATEADD('DAY', 6, CURRENT_DATE), DATEADD('DAY', 8, CURRENT_DATE), 4, 'SUITE', 2, 'CONFIRMED');

INSERT INTO bookings (hotel_name, booking_number, check_in_date, check_out_date, customer_id, room_type, number_of_guests, booking_status)
VALUES ('Four Seasons', '105', DATEADD('DAY', 8, CURRENT_DATE), DATEADD('DAY', 10, CURRENT_DATE), 5, 'SINGLE', 2, 'CONFIRMED');
