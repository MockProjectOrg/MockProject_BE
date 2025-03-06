package org.example.bookingbe.repository.StatusRepo;

import org.example.bookingbe.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStatusRepo extends JpaRepository<Status, Long> {
}
