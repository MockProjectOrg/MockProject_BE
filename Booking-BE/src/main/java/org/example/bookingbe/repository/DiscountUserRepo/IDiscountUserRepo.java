package org.example.bookingbe.repository.DiscountUserRepo;

import org.example.bookingbe.model.Discount;
import org.example.bookingbe.model.DiscountUser;
import org.example.bookingbe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IDiscountUserRepo extends JpaRepository<DiscountUser, Long> {
    List<DiscountUser> findByUser(User user);
    List<DiscountUser> findByUserAndIsUsedFalse(User user);
    List<DiscountUser> findByDiscount(Discount discount);
    Optional<DiscountUser> findByDiscountCodeAndIsUsedFalse(String discountCode);
}