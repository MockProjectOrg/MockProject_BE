package org.example.bookingbe.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity
@Table(name = "discount_user")
public class DiscountUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "discount_code", columnDefinition = "varchar(150)")
    private String discountCode;
    @Column(name = "is_used", columnDefinition = "BIT")
    @ColumnDefault("0")
    private Boolean isUsed;
    @Column(name = "expired_at", columnDefinition = "DATE")
    private LocalDate expiredAt;
    @Column(name = "create_at", columnDefinition = "DATE")
    private LocalDate createAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

    public DiscountUser() {
    }

    public DiscountUser(Long id, String discountCode, Boolean isUsed, LocalDate expiredAt, LocalDate createAt, User user, Discount discount) {
        this.id = id;
        this.discountCode = discountCode;
        this.isUsed = isUsed;
        this.expiredAt = expiredAt;
        this.createAt = createAt;
        this.user = user;
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public LocalDate getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(LocalDate expiredAt) {
        this.expiredAt = expiredAt;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
}
