package org.example.bookingbe.repository.DiscountDetailRepo;

import org.example.bookingbe.model.DiscountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDiscountDetailRepo extends JpaRepository<DiscountDetail, Long> {
}
