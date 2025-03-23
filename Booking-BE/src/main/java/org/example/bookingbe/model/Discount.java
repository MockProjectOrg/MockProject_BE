package org.example.bookingbe.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity
@Table(name = "discount")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "discount_value")
    private Integer discountValue;
    @Column(name = "is_active", columnDefinition = "BIT")
    @ColumnDefault("0")
    private Boolean isActive;
    @Column(name = "create_at", columnDefinition = "DATE")
    private LocalDate createAt;
    @Column(name = "update_at", columnDefinition = "DATE")
    private LocalDate updateAt;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    public Discount() {
    }

    public Discount(Long id, String name, String description, Integer discountValue, Boolean isActive, LocalDate createAt, LocalDate updateAt, Hotel hotel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.discountValue = discountValue;
        this.isActive = isActive;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.hotel = hotel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Integer discountValue) {
        this.discountValue = discountValue;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public LocalDate getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
