package org.example.bookingbe.dto;

import java.time.LocalDateTime;

public interface BookingInterface {
    Long getId();
    LocalDateTime getCheckIn();
    LocalDateTime getCheckOut();
    String getDescription();
    Long getRoomId();
    Long getUserId();
    Long getHotelId();

}

