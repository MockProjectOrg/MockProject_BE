package org.example.bookingbe.repository.DiscountRepo;

import org.example.bookingbe.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDiscountRepo extends JpaRepository<Discount, Long> {
}
