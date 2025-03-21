package org.example.bookingbe.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "check_in", columnDefinition = "DATETIME")
    private LocalDateTime checkIn;
    @Column(name = "check_out", columnDefinition = "DATETIME")
    private LocalDateTime checkOut;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "status_book", columnDefinition = "BIT")
    @ColumnDefault("0")
    private Boolean status;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    public Booking() {}

    public Booking(Long id, LocalDateTime checkIn, LocalDateTime checkOut, String description, User user, Boolean status, Room room) {
        this.id = id;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.description = description;
        this.user = user;
        this.status = status;
        this.room = room;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
