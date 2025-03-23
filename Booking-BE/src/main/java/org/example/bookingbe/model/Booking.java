package org.example.bookingbe.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "booking_date", columnDefinition = "DATETIME")
    private LocalDateTime bookingDate;
    @Column(name = "booking_cancel", columnDefinition = "DATETIME")
    private LocalDateTime bookingCancel;
    @Column(name = "check_in", columnDefinition = "DATETIME")
    private LocalDateTime checkIn;
    @Column(name = "check_out", columnDefinition = "DATETIME")
    private LocalDateTime checkOut;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "package_id")
    private Package bookingPackage;

    public Booking() {
    }

    public Booking(LocalDateTime bookingDate, LocalDateTime canceledAt, LocalDateTime checkIn, LocalDateTime checkOut,
                   String description, User user, Status status, Room room, Package bookingPackage) {
        this.bookingDate = bookingDate;
        this.bookingCancel = bookingCancel;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.description = description;
        this.user = user;
        this.status = status;
        this.room = room;
        this.bookingPackage = bookingPackage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDateTime getCanceledAt() {
        return bookingCancel;
    }

    public void setCanceledAt(LocalDateTime canceledAt) {
        this.bookingCancel = canceledAt;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Package getBookingPackage() {
        return bookingPackage;
    }

    public void setBookingPackage(Package bookingPackage) {
        this.bookingPackage = bookingPackage;
    }
}