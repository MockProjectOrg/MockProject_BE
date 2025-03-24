package org.example.bookingbe.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date_payment", columnDefinition = "DATETIME")
    private LocalDateTime datePayment;
    @Column(name = "total_price", columnDefinition = "DOUBLE")
    private Double totalPrice;
    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
    public Bill() {}

    public Bill(LocalDateTime datePayment, Double totalPrice, Booking booking) {
        this.datePayment = datePayment;
        this.totalPrice = totalPrice;
        this.booking = booking;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDatePayment() {
        return datePayment;
    }

    public void setDatePayment(LocalDateTime datePayment) {
        this.datePayment = datePayment;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
