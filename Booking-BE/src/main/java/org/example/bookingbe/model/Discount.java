package org.example.bookingbe.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "discount")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "discount_percent", columnDefinition = "LONG")
    private Long discountPercent;
    @Column(name = "date_start", columnDefinition = "DATETIME")
    private LocalDateTime dateStart;
    @Column(name = "date_end", columnDefinition = "DATETIME")
    private LocalDateTime dateEnd;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    private BigDecimal discountValue;
    private Boolean isActive;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

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
    @PrePersist
    protected void onCreate() {
        createAt = LocalDateTime.now();
        updateAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateAt = LocalDateTime.now();
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

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
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
}