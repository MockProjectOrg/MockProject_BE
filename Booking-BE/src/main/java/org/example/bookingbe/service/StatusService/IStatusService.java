package org.example.bookingbe.service.StatusService;

import org.example.bookingbe.model.Room;
import org.example.bookingbe.model.Status;

import java.util.List;
import java.util.Optional;

public interface IStatusService {

    List<Status> findAll();
    Optional<Status> findById(Long id);
    Status save(Status status);
    void deleteById(Long id);
}
