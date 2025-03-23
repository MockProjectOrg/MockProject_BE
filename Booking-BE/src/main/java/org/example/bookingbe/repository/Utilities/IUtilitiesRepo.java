package org.example.bookingbe.repository.Utilities;

import org.example.bookingbe.model.Utilities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUtilitiesRepo extends JpaRepository<Utilities, Long> {
}
