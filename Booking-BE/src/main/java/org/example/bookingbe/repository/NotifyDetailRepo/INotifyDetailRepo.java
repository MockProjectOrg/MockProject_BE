package org.example.bookingbe.repository.NotifyDetailRepo;

import org.example.bookingbe.model.NotifyDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.management.Notification;
import java.util.List;

@Repository
public interface INotifyDetailRepo extends JpaRepository<NotifyDetail, Long> {

    @Query("SELECT nd FROM NotifyDetail nd WHERE nd.user.id = :userId ORDER BY nd.notify.dateNotify DESC")
    List<NotifyDetail> findByUserIdOrderByDateNotifyDesc(@Param("userId") Long userId);

    @Query("SELECT COUNT(nd) FROM NotifyDetail nd WHERE nd.user.id = :userId")
    int countByUserId(@Param("userId") Long userId);

    List<NotifyDetail> findByUserIdAndNotifyIsReadFalse(Long userId);
}
