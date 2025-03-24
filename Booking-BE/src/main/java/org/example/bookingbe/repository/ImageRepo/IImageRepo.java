package org.example.bookingbe.repository.ImageRepo;

import org.example.bookingbe.model.Image;
import org.example.bookingbe.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IImageRepo extends JpaRepository<Image, Long> {
    void deleteByRoomId(Long roomId);

    List<Image> findByRoomId(Long roomId);

    @Query("SELECT i.imageName FROM Image i WHERE i.room = :room")
    List<String> findImagesByRoom(Room room);
}

