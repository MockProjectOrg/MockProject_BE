package org.example.bookingbe.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "hotel_name", columnDefinition = "varchar(100)")
    private String hotelName;
    @Column(name = "address", columnDefinition = "varchar(150)")
    private String address;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "avatar", columnDefinition = "TEXT")
    private String avatar;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    public Hotel() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


}
