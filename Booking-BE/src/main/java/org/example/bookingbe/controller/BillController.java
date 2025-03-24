package org.example.bookingbe.controller;

import org.example.bookingbe.service.BillService.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/bill")
public class BillController {

    @Autowired
    private IBillService billService;

    // Tính phần trăm thay đổi doanh thu giữa tháng hiện tại và tháng trước
    public Double getPercentageChangeInRevenue() {
        // Lấy danh sách doanh thu theo tháng
        List<Map<String, Object>> monthlyRevenueList = billService.getMonthlyRevenue();
        // Lấy doanh thu tháng hiện tại
        int currentMonth = LocalDate.now().getMonthValue();
        Double currentRevenue = monthlyRevenueList.stream()
                .filter(map -> (int) map.get("month") == currentMonth)
                .map(map -> (Double) map.get("revenue"))
                .findFirst()
                .orElse(0.0);

        // Lấy doanh thu tháng trước
        int previousMonth = currentMonth - 1;
        int currentYear = LocalDate.now().getYear();
        if (previousMonth == 0) { // Nếu tháng trước là tháng 12 của năm trước
            previousMonth = 12;
            currentYear -= 1;
        }
        Double previousRevenue = billService.getPreviousMonthlyRevenue(previousMonth, currentYear);

        // Tránh lỗi chia cho 0
        if (previousRevenue == 0.0) {
            return 0.0;
        }

        return ((currentRevenue - previousRevenue) / previousRevenue) * 100;
    }

    @GetMapping("/admin/Revenuechart")
    public String getRevenueChart(Model model) {
        List<Map<String, Object>> revenueData = billService.getMonthlyRevenue();

        // Tạo danh sách nhãn (tháng) và dữ liệu doanh thu
        List<String> labels = revenueData.stream()
                .map(row -> "Tháng " + row.get("month"))
                .toList();

        List<Double> revenues = revenueData.stream()
                .map(row -> (Double) row.get("revenue"))
                .toList();

        // Đưa dữ liệu vào model để Thymeleaf sử dụng
        model.addAttribute("labels", labels);
        model.addAttribute("revenues", revenues);

        return "adminHotel/revenueChart"; // Trả về trang Thymeleaf
    }

}
