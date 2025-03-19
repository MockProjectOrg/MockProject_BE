package org.example.bookingbe.controller;

import org.example.bookingbe.model.NotifyDetail;
import org.example.bookingbe.service.NotifyService.INotifyService;
import org.example.bookingbe.service.UserDetail.UserPriciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/managerHotel/notify")
public class NotifyController {

    @Autowired
    private INotifyService notifyService;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Hiển thị trang thông báo
    @GetMapping
    public String showNotifications(Model model) {
        Long userId = getCurrentUserId();
        List<NotifyDetail> notifications = notifyService.getNotificationsByUserId(userId);
        model.addAttribute("notifications", notifications);
        model.addAttribute("dateFormatter", DATE_FORMATTER);
        return "managerHotel/notifications";
    }

    // API để lấy số lượng thông báo
    @GetMapping("/count")
    @ResponseBody
    public Map<String, Object> getNotificationCount() {
        Long userId = getCurrentUserId();
        int count = notifyService.getNotificationCount(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("count", count);
        return response;
    }

    // API để lấy danh sách thông báo mới nhất (dùng cho icon chuông)
    @GetMapping("/latest")
    @ResponseBody
    public List<NotifyDetail> getLatestNotifications() {
        Long userId = getCurrentUserId();
        return notifyService.getNotificationsByUserId(userId);
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        if (authentication.getPrincipal() instanceof UserPriciple) {
            return ((UserPriciple) authentication.getPrincipal()).getId();
        }
        return null;
    }


    @PutMapping("/{id}/read")
    @ResponseBody
    public ResponseEntity<?> markAsRead(@PathVariable("id") Long notifyId) {
        try {
            notifyService.markAsRead(notifyId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Hoặc nếu bạn muốn thêm endpoint mark all as read
    @PutMapping("/mark-all-read")
    @ResponseBody
    public ResponseEntity<?> markAllAsRead() {
        try {
            Long userId = getCurrentUserId();
            notifyService.markAllAsRead(userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

