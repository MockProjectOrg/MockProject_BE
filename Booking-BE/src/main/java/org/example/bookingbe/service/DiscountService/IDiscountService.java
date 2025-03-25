package org.example.bookingbe.service.DiscountService;

import org.example.bookingbe.dto.DiscountDto;
import org.example.bookingbe.model.Discount;
import org.example.bookingbe.model.DiscountUser;

import java.util.List;

public interface IDiscountService {
    List<DiscountDto> getDiscounts(String userName, Long roomId);

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
