package org.example.bookingbe.custom.datetime;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Component
public class DateTimeFormat {
    public String formatDate(String dateStr) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Chuyển chuỗi thành LocalDateTime
        LocalDateTime dateTime = LocalDateTime.parse(dateStr, inputFormatter);

        // Định dạng lại
        return dateTime.format(outputFormatter);
    }

    public String formatTime(String dateStr) {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm");

            // Chuyển chuỗi thành LocalDateTime
            LocalDateTime dateTime = LocalDateTime.parse(dateStr, inputFormatter);

            // Định dạng chỉ lấy giờ:phút
            return dateTime.format(outputFormatter);
        } catch (Exception e) {
            return "Invalid time";
        }
    }

    public static long calculateDaysBetween(String checkInStr, String checkOutStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            // Chuyển đổi chuỗi thành LocalDateTime
            LocalDateTime checkIn = LocalDateTime.parse(checkInStr, formatter);
            LocalDateTime checkOut = LocalDateTime.parse(checkOutStr, formatter);

            // Tính số ngày giữa hai thời điểm (làm tròn lên nếu có giờ lẻ)
            long days = ChronoUnit.DAYS.between(checkIn.toLocalDate(), checkOut.toLocalDate());

            return days;
        } catch (Exception e) {
            System.out.println("Lỗi khi chuyển đổi ngày: " + e.getMessage());
            return 0;
        }
    }
}
