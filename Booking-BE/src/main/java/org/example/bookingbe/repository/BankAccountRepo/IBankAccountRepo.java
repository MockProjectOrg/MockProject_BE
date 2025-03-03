package org.example.bookingbe.repository.BankAccountRepo;

import org.example.bookingbe.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBankAccountRepo extends JpaRepository<BankAccount, Long> {
}
