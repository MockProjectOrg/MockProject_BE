package org.example.bookingbe.repository.RoomRepo;

import org.example.bookingbe.model.Room;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoomRepo extends JpaRepository<Room, Long> {
    @EntityGraph(attributePaths = {"images"})
    Optional<Room> findById(Long id);
}
