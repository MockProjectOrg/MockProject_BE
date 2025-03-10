package org.example.bookingbe.model.dto;/*
 * @project Booking-BE
 * @author Huy
 */

public class HotelDTO {

    private Long id;
    private String userName;
    private String userId;

    public HotelDTO(Long id, String userName, String userId) {
        this.id = id;
        this.userName = userName;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }
}
