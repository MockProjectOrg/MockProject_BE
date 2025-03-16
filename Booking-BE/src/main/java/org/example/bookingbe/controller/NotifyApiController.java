package org.example.bookingbe.controller;

import org.example.bookingbe.service.NotifyService.INotifyService;
import org.example.bookingbe.service.UserDetail.UserPriciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotifyApiController {

    @Autowired
    private INotifyService notifyService;

    @PutMapping("/{id}/read")
    public ResponseEntity<?> markAsRead(@PathVariable("id") Long notifyId) {
        try {
            notifyService.markAsRead(notifyId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/mark-all-read")
    public ResponseEntity<?> markAllAsRead() {
        try {
            Long userId = getCurrentUserId();
            notifyService.markAllAsRead(userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable("id") Long notifyId) {
        try {
            notifyService.deleteNotification(notifyId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
}
