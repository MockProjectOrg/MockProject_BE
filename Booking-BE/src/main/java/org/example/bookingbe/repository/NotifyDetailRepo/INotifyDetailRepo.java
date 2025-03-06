package org.example.bookingbe.repository.NotifyDetailRepo;

import org.example.bookingbe.model.NotifyDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INotifyDetailRepo extends JpaRepository<NotifyDetail, Long> {
}
