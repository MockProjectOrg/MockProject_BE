package org.example.bookingbe.controller;

import org.example.bookingbe.repository.BookingRepo.IBookingRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ChartController {

    private final IBookingRepo bookingRepo;

    public ChartController(IBookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    @GetMapping("/BookingChart")
    @ResponseBody
    public Map<String, Object> getBookingChartData() {
        List<Object[]> rawData = bookingRepo.countBookingsByMonth();

        String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        int currentMonth = LocalDate.now().getMonthValue();

        List<String> labels = new ArrayList<>();
        List<Integer> bookingData = new ArrayList<>();
        int[] bookingsByMonth = new int[12];

        for (Object[] row : rawData) {
            if (row[0] != null && row[1] != null) {
                int month = ((Number) row[0]).intValue();
                int count = ((Number) row[1]).intValue();
                bookingsByMonth[month - 1] = count;
            }
        }

        for (int i = 0; i < currentMonth; i++) {
            labels.add(monthNames[i]);
            bookingData.add(bookingsByMonth[i]);
        }

        // Debug dữ liệu
        System.out.println("📊 API Labels: " + labels);
        System.out.println("📈 API Booking Data: " + bookingData);

        Map<String, Object> response = new HashMap<>();
        response.put("labels", labels);
        response.put("bookingData", bookingData);

        return response;
    }

}
