package org.example.bookingbe.dto;

import java.time.LocalDate;
import java.sql.Date;

public class DiscountDto {
    private Long id;
    private String description;
    private Boolean isActive;
    private String name;
    private Long hotelId;
    private Boolean isUsed;
    private String discountCode;
    private Integer discountValue;
    private LocalDate createAt;
    private LocalDate expiredAt;

    public DiscountDto(Long id, String description, Boolean isActive, String name, Integer discountValue, Date createAt, Date expiredAt, Boolean isUsed, String discountCode, Long hotelId) {
        this.id = id;
        this.description = description;
        this.isActive = isActive;
        this.name = name;
        this.discountValue = discountValue;
        this.createAt = (createAt != null) ? createAt.toLocalDate() : null;
        this.expiredAt = (expiredAt != null) ? expiredAt.toLocalDate() : null;
        this.isUsed = isUsed;
        this.discountCode = discountCode;
        this.hotelId = hotelId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public Integer getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Integer discountValue) {
        this.discountValue = discountValue;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public LocalDate getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(LocalDate expiredAt) {
        this.expiredAt = expiredAt;
    }



}
