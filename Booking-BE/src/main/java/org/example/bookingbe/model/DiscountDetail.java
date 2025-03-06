package org.example.bookingbe.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "discount_detail")
public class DiscountDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code", columnDefinition = "Varchar(50)")
    private String code;
    @Column(name = "status_discount", columnDefinition = "BIT")
    @ColumnDefault("0")
    private boolean statusDiscount;
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    public DiscountDetail() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isStatusDiscount() {
        return statusDiscount;
    }

    public void setStatusDiscount(boolean statusDiscount) {
        this.statusDiscount = statusDiscount;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
