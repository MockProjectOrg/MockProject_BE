package org.example.bookingbe.service.DiscountService;

import jakarta.transaction.Transactional;
import org.example.bookingbe.model.Discount;
import org.example.bookingbe.model.DiscountUser;
import org.example.bookingbe.model.Hotel;
import org.example.bookingbe.model.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IDiscountService {

    // Discount management for Hotel Manager
    Discount createDiscount(Discount discount, Long hotelId);
    Discount updateDiscount(Discount discount);
    void deleteDiscount(Integer discountId);
    List<Discount> getAllDiscountsByHotel(Long hotelId);

    // Generate discount codes for eligible users
    void generateDiscountCodesForEligibleUsers(Integer discountId);

    // User functionality
    List<DiscountUser> getUserDiscounts(Long userId);
    boolean validateAndUseDiscountCode(String discountCode, Long userId);
}
