package org.example.bookingbe.repository.DiscountUserRepo;

import org.example.bookingbe.model.DiscountUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDiscountUserRepo extends JpaRepository<DiscountUser, Long> {
}
