package org.example.bookingbe.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    // Thêm các thuộc tính để định nghĩa điều kiện của mã giảm giá

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_condition") // Hoặc tên thực tế của cột trong database
    private DiscountCondition condition;

    public void setIsActive(boolean b) {
    }

    // Điều kiện áp dụng mã giảm giá
    public enum DiscountCondition {
        NEW_USER,    // Khách hàng mới
        VIP_USER,    // Khách VIP
        FREQUENT_USER // Khách đặt nhiều
    }

    @PrePersist
    protected void onCreate() {
        createAt = LocalDate.now();
        updateAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateAt = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = Long.valueOf(id);
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


    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        this.isActive = active;
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

    public DiscountCondition getCondition() {
        return condition;
    }

    public void setCondition(DiscountCondition condition) {
        this.condition = condition;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Integer discountValue) {
        this.discountValue = discountValue;
    }
}