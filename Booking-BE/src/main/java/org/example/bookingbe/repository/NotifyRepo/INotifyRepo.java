package org.example.bookingbe.repository.NotifyRepo;

import org.example.bookingbe.model.Notify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INotifyRepo extends JpaRepository<Notify, Long> {
}
