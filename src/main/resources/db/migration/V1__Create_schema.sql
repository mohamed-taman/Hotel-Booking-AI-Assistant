CREATE TABLE customers
(
    id         BIGINT AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    CONSTRAINT CUSTOMER_ID_PK PRIMARY KEY (id)
);

CREATE TABLE bookings
(
    id               BIGINT AUTO_INCREMENT,
    hotel_name       VARCHAR(255) NOT NULL,
    booking_number   VARCHAR(255) NOT NULL,
    check_in_date    DATE         NOT NULL,
    check_out_date   DATE         NOT NULL,
    customer_id      BIGINT       NOT NULL,
    room_type        ENUM('SINGLE', 'DOUBLE', 'TRIPLE', 'SUITE') NOT NULL,
    number_of_guests INT          NOT NULL,
    booking_status   ENUM('CONFIRMED', 'COMPLETED', 'CANCELLED') NOT NULL,
    CONSTRAINT BOOKING_ID_PK PRIMARY KEY (id),
    CONSTRAINT BOOKING_NUMBER_UQ UNIQUE (booking_number),
    CONSTRAINT BOOKING_CUSTOMER_ID_FK FOREIGN KEY (customer_id) REFERENCES customers (id)
);

-- AI Chat Memory schema
CREATE TABLE spring_ai_chat_memory
(
    id BIGINT AUTO_INCREMENT,
    conversation_id VARCHAR (36) NOT NULL,
    content CLOB NOT NULL,
    type VARCHAR (20) CHECK (type IN ('USER', 'ASSISTANT', 'SYSTEM', 'TOOL')) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    CONSTRAINT CHAT_MEMORY_PK PRIMARY KEY (id)
);

CREATE INDEX CONVERSATION_ID_TIMESTAMP_IDX
    ON SPRING_AI_CHAT_MEMORY (conversation_id, timestamp);
