package org.example.bookingbe.repository.ImageRepo;

import org.example.bookingbe.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IImageRepo extends JpaRepository<Image, Long> {
    List<Image> findByRoomId(Long roomId);
}
