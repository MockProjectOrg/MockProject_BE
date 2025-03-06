package org.example.bookingbe.repository.PaymentRepo;

import org.example.bookingbe.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaymentRepo extends JpaRepository<Payment, Long> {
}
