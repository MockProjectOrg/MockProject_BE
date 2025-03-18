package org.example.bookingbe.service.StatusService;

import jakarta.transaction.Transactional;
import org.example.bookingbe.model.Room;
import org.example.bookingbe.model.Status;
import org.example.bookingbe.repository.StatusRepo.IStatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StatusService implements IStatusService {
    private final IStatusRepo statusRepo;

    @Autowired
    public StatusService(IStatusRepo statusRepo) {
        this.statusRepo = statusRepo;
    }

    @Override
    public List<Status> findAll() {
        return statusRepo.findAll();
    }

    @Override
    public Optional<Status> findById(Long id) {
        return statusRepo.findById(id);
    }

    @Override
    public Status save(Status status) {
        return statusRepo.save(status);
    }

    @Override
    public void deleteById(Long id) {
        statusRepo.deleteById(id);
    }
}
