package org.example.bookingbe.repository.BillDetailRepo;

import org.example.bookingbe.model.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBillDetailRepo extends JpaRepository<BillDetail, Long> {
}
