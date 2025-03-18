package org.example.bookingbe.controller;

import jakarta.servlet.http.HttpSession;
import org.example.bookingbe.model.Room;
import org.example.bookingbe.model.Status;
import org.example.bookingbe.service.RoomService.IRoomService;
import org.example.bookingbe.service.StatusService.IStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("hotelManager/roomStatuses")
public class StatusController {
    private final IStatusService statusService;

    @Autowired
    public StatusController(IStatusService statusService) {
        this.statusService = statusService;
    }

    // 1. Hiển thị danh sách Status
    @GetMapping
    public String showAllStatuses(Model model) {
        model.addAttribute("statuses", statusService.findAll());
        return "managerHotel/managerStatus";
    }

    // 2. Hiển thị form thêm mới Status
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("status", new Status());
        return "managerHotel/managerStatus";
    }

    // 3. Xử lý thêm mới Status
    @PostMapping("/create")
    public String createStatus(@ModelAttribute Status status, RedirectAttributes redirectAttributes) {
        statusService.save(status);
        redirectAttributes.addFlashAttribute("success", "Thêm trạng thái mới thành công!");
        return "redirect:/hotelManager/roomStatuses";
    }

    // 4. Hiển thị form chỉnh sửa Status
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Status> statusOptional = statusService.findById(id);
        if (statusOptional.isPresent()) {
            model.addAttribute("status", statusOptional.get());
            return "status/edit";
        } else {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy trạng thái với ID: " + id);
            return "redirect:/hotelManager/roomStatuses";
        }
    }

    // 5. Xử lý cập nhật Status
    @PostMapping("/edit")
    public String updateStatus(@ModelAttribute Status status, RedirectAttributes redirectAttributes) {
        statusService.save(status);
        redirectAttributes.addFlashAttribute("success", "Cập nhật trạng thái thành công!");
        return "redirect:/hotelManager/roomStatuses";
    }

    // 6. Xóa Status
    @GetMapping("/delete/{id}")
    public String deleteStatus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            statusService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Xóa trạng thái thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa trạng thái này!");
        }
        return "redirect:/hotelManager/roomStatuses";
    }
}