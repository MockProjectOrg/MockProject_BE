package org.example.bookingbe.service.NotifyService;

import org.example.bookingbe.model.NotifyDetail;

import java.util.List;

public interface INotifyService {
    void createCheckoutNotification(String roomNumber, String checkoutTime, Long userId);
    List<NotifyDetail> getNotificationsByUserId(Long userId);
    int getNotificationCount(Long userId);

    void markAsRead(Long notifyId);
    void markAllAsRead(Long userId);

    void deleteNotification(Long notifyId);
}
