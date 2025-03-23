package org.example.bookingbe.controller;

import org.example.bookingbe.model.Discount;
import org.example.bookingbe.model.DiscountUser;
import org.example.bookingbe.model.Hotel;
import org.example.bookingbe.model.User;
import org.example.bookingbe.repository.HotelRepo.IHotelRepo;
import org.example.bookingbe.repository.UserRepo.IUserRepo;
import org.example.bookingbe.service.DiscountService.IDiscountService;
import org.example.bookingbe.service.HotelService.IHotelService;
import org.example.bookingbe.service.UserDetail.UserPriciple;
import org.example.bookingbe.service.UserService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/discounts")
public class DiscountController {
    @Autowired
    private IDiscountService discountService;

    @Autowired
    private IHotelRepo hotelRepo;

    @Autowired
    private IUserRepo userRepo;

    // Hotel Manager endpoints

    @GetMapping("/manager/dashboard")
    @PreAuthorize("hasRole('HOTEL MANAGER')")
    public String getDiscountDashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPriciple userPrinciple = (UserPriciple) authentication.getPrincipal();

        Long userId = userPrinciple.getId();
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Hotel hotel = hotelRepo.findByUser(user).stream().findFirst()
                .orElseThrow(() -> new RuntimeException("No hotel found for this user"));

        Long hotelId = hotel.getId();

        List<Discount> discounts = discountService.getAllDiscountsByHotel(hotelId);
        model.addAttribute("discounts", discounts);
        model.addAttribute("newDiscount", new Discount());

        return "managerHotel/managerDiscount";
    }

    @PostMapping("/manager/create")
    @PreAuthorize("hasRole('HOTEL MANAGER')")
    public String createDiscount(@AuthenticationPrincipal UserPriciple userPrinciple,
                                 @ModelAttribute Discount discount) {
        Long userId = userPrinciple.getId();
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Hotel hotel = hotelRepo.findByUser(user).stream().findFirst()
                .orElseThrow(() -> new RuntimeException("No hotel found for this user"));

        Long hotelId = hotel.getId();

        discountService.createDiscount(discount, hotelId);
        return "redirect:/discounts/manager/dashboard";
    }

    @PostMapping("/manager/update/{id}")
    @PreAuthorize("hasRole('HOTEL MANAGER')")
    public String updateDiscount(@PathVariable Integer id,
                                 @ModelAttribute Discount discount) {
        discount.setId(id);
        discountService.updateDiscount(discount);
        return "redirect:/discounts/manager/dashboard";
    }

    @PostMapping("/manager/delete/{id}")
    @PreAuthorize("hasRole('HOTEL MANAGER')")
    public String deleteDiscount(@PathVariable Integer id) {
        discountService.deleteDiscount(id);
        return "redirect:/discounts/manager/dashboard";
    }

    @PostMapping("/manager/generate/{id}")
    @PreAuthorize("hasRole('HOTEL MANAGER')")
    public String generateDiscountCodes(@PathVariable Integer id) {
        discountService.generateDiscountCodesForEligibleUsers(id);
        return "redirect:/discounts/manager/dashboard";
    }

    // User endpoints

    @GetMapping("/user/my-discounts")
    public String getUserDiscounts(@AuthenticationPrincipal UserPriciple userPrinciple, Model model) {
        Long userId = userPrinciple.getId();
        List<DiscountUser> userDiscounts = discountService.getUserDiscounts(userId);

        model.addAttribute("discounts", userDiscounts);
        return "profile";
    }

    // REST API endpoints for potential AJAX operations

    @PostMapping("/api/validate")
    @ResponseBody
    public ResponseEntity<?> validateDiscountCode(@AuthenticationPrincipal UserPriciple userPrinciple,
                                                  @RequestParam String discountCode) {
        Long userId = userPrinciple.getId();
        boolean isValid = discountService.validateAndUseDiscountCode(discountCode, userId);

        if (isValid) {
            return ResponseEntity.ok().body("Discount code applied successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired discount code");
        }
    }

}