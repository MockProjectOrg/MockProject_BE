package org.example.bookingbe.service.DiscountService;

import org.example.bookingbe.dto.DiscountDto;
import org.example.bookingbe.model.*;
import org.example.bookingbe.repository.DiscountRepo.IDiscountRepo;
import org.example.bookingbe.repository.DiscountUserRepo.IDiscountUserRepo;
import org.example.bookingbe.repository.HotelRepo.IHotelRepo;
import org.example.bookingbe.repository.RoomRepo.IRoomRepo;
import org.example.bookingbe.repository.UserRepo.IUserRepo;
import org.example.bookingbe.service.HotelService.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DiscountService implements IDiscountService {
    @Autowired
    private IDiscountRepo discountRepo;
    @Autowired
    private IHotelService hotelService;
    @Autowired
    private IRoomRepo roomRepo;

    @Autowired
    private IDiscountUserRepo discountUserRepo;

    @Autowired
    private IHotelRepo hotelRepo;

    @Autowired
    private IUserRepo userRepo;
    @Override
    public List<DiscountDto> getDiscounts(String userName, Long roomId) {
        Optional<Room> room = roomRepo.findById(roomId);
        return discountRepo.getAllDiscount(userName,room.get().getHotel().getId());
    }



    @Override
    public Discount createDiscount(Discount discount, Long hotelId) {
        Hotel hotel = hotelRepo.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        discount.setHotel(hotel);
        discount.setActive(true);

        return discountRepo.save(discount);
    }

    @Override
    public Discount updateDiscount(Discount discount) {
        Discount existingDiscount = discountRepo.findById(discount.getId())
                .orElseThrow(() -> new RuntimeException("Discount not found"));

        existingDiscount.setName(discount.getName());
        existingDiscount.setDescription(discount.getDescription());
        existingDiscount.setDiscountValue(discount.getDiscountValue());
        existingDiscount.setActive(discount.getActive());
        existingDiscount.setCondition(discount.getCondition());

        return discountRepo.save(existingDiscount);
    }

    @Override
    public void deleteDiscount(Integer discountId) {
        Discount discount = discountRepo.findById(Long.valueOf(discountId))
                .orElseThrow(() -> new RuntimeException("Discount not found"));

        // Delete all related discount_user records first
        List<DiscountUser> discountUsers = discountUserRepo.findByDiscount(discount);
        discountUserRepo.deleteAll(discountUsers);

        // Then delete the discount
        discountRepo.delete(discount);
    }

    @Override
    public List<Discount> getAllDiscountsByHotel(Long hotelId) {
        Hotel hotel = hotelRepo.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        return discountRepo.findByHotel(hotel);
    }

    @Override
    public void generateDiscountCodesForEligibleUsers(Integer discountId) {
        Discount discount = discountRepo.findById(Long.valueOf(discountId))
                .orElseThrow(() -> new RuntimeException("Discount not found"));

        if (!discount.getActive()) {
            throw new RuntimeException("Cannot generate codes for inactive discount");
        }

        // Get eligible users based on discount condition
        List<User> eligibleUsers = findEligibleUsers(discount.getCondition(), discount.getHotel().getId());

        // Generate unique discount codes for each eligible user
        for (User user : eligibleUsers) {
            DiscountUser discountUser = new DiscountUser();
            discountUser.setUser(user);
            discountUser.setDiscount(discount);
            discountUser.setDiscountCode(generateUniqueCode(discount.getName()));
            discountUser.setUsed(false);
            discountUser.setExpiredAt(LocalDate.now().plusMonths(1)); // Set expiry to 1 month from now

            discountUserRepo.save(discountUser);
        }
    }

    private List<User> findEligibleUsers(Discount.DiscountCondition condition, Long hotelId) {
        // This is a simplified implementation. In a real system, you would have more complex logic
        // to determine which users are eligible for each discount type

        switch (condition) {
            case NEW_USER:
                // Example: Get users who registered within the last week
                return userRepo.findByCreatedAtAfter(LocalDate.now().minusWeeks(1));
            case VIP_USER:
                // Example: In a real system, this would use some VIP status indicator
                return userRepo.findByVipStatusTrue();
            case FREQUENT_USER:

                return userRepo.findByFrequentBookerTrue();
            default:
                return new ArrayList<>();
        }
    }

    private String generateUniqueCode(String discountName) {
        // Generate a unique code based on discount name and random UUID
        return discountName + "-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    @Override
    public List<DiscountUser> getUserDiscounts(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return discountUserRepo.findByUser(user);
    }

    @Override
    public boolean validateAndUseDiscountCode(String discountCode, Long userId) {
        Optional<DiscountUser> discountUserOpt = discountUserRepo.findByDiscountCodeAndIsUsedFalse(discountCode);

        if (discountUserOpt.isPresent()) {
            DiscountUser discountUser = discountUserOpt.get();

            // Check if the discount belongs to the user
            if (!discountUser.getUser().getId().equals(userId)) {
                return false; // Discount code belongs to another user
            }

            // Check if the discount is expired
            if (discountUser.getExpiredAt().isBefore(LocalDate.now())) {
                return false; // Discount is expired
            }

            // Mark the discount as used
            discountUser.setUsed(true);
            discountUserRepo.save(discountUser);

            return true;
        }

        return false; // Discount code not found or already used
    }
}
