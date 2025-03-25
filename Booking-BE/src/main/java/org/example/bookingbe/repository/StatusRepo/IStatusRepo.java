package org.example.bookingbe.repository.StatusRepo;

import org.example.bookingbe.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IStatusRepo extends JpaRepository<Status, Long> {
    @Query("SELECT s FROM Status s WHERE s.statusName = :name")
    Status findByName(@Param("name") String name);


}
