package org.example.bookingbe.service.NotifyService;

import jakarta.transaction.Transactional;
import org.example.bookingbe.model.Notify;
import org.example.bookingbe.model.NotifyDetail;
import org.example.bookingbe.model.User;
import org.example.bookingbe.repository.NotifyDetailRepo.INotifyDetailRepo;
import org.example.bookingbe.repository.NotifyRepo.INotifyRepo;
import org.example.bookingbe.repository.UserRepo.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotifyService implements INotifyService {
    @Autowired
    private INotifyRepo notifyRepo;

    @Autowired
    private INotifyDetailRepo notifyDetailRepo;

    @Autowired
    private IUserRepo userRepo;

    @Override
    @Transactional
    public void createCheckoutNotification(String roomNumber, String checkoutTime, Long userId) {
        // Tạo thông báo mới
        Notify notify = new Notify();
        notify.setDateNotify(LocalDateTime.now());
        notifyRepo.save(notify);

        // Lấy thông tin user
        Optional<User> userOpt = userRepo.findById(userId);
        if (!userOpt.isPresent()) {
            return;
        }

        // Tạo chi tiết thông báo
        NotifyDetail notifyDetail = new NotifyDetail();
        notifyDetail.setDescription("Phòng " + roomNumber + " sắp check-out lúc " + checkoutTime);
        notifyDetail.setNotify(notify);
        notifyDetail.setUser(userOpt.get());

        notifyDetailRepo.save(notifyDetail);
    }

    @Override
    public List<NotifyDetail> getNotificationsByUserId(Long userId) {
        return notifyDetailRepo.findByUserIdOrderByDateNotifyDesc(userId);
    }

    @Override
    public int getNotificationCount(Long userId) {
        return notifyDetailRepo.countByUserId(userId);
    }

    @Override
    public void markAsRead(Long notifyId) {
        // Tìm thông báo theo ID
        Optional<Notify> notifyOpt = notifyRepo.findById(notifyId);
        if (notifyOpt.isPresent()) {
            Notify notify = notifyOpt.get();
            notify.setIsRead(true);
            notifyRepo.save(notify);
        }
    }

    @Override
    public void markAllAsRead(Long userId) {
        // Lấy tất cả thông báo chưa đọc của user
        List<NotifyDetail> unreadNotifications = notifyDetailRepo.findByUserIdAndNotifyIsReadFalse(userId);

        // Đánh dấu tất cả là đã đọc
        for (NotifyDetail notifyDetail : unreadNotifications) {
            Notify notify = notifyDetail.getNotify();
            notify.setIsRead(true);
            notifyRepo.save(notify);
        }
    }

    @Override
    @Transactional
    public void deleteNotification(Long notifyId) {
        notifyDetailRepo.deleteById(notifyId);
    }

}