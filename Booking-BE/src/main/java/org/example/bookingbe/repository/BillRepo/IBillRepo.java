package org.example.bookingbe.repository.BillRepo;

import org.example.bookingbe.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBillRepo extends JpaRepository<Bill, Long> {
    
}
