package org.example.bookingbe.controller;

import jakarta.validation.Valid;
import org.example.bookingbe.model.User;
import org.example.bookingbe.model.dto.HotelDTO;
import org.example.bookingbe.model.dto.UserDTO;
import org.example.bookingbe.service.HotelService.IHotelService;
import org.example.bookingbe.service.UserService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IHotelService hotelService;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<UserDTO> userDTOList = userService.findAllUsers();
        model.addAttribute("users", userDTOList);
        return "admin/users";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        UserDTO userDTO = userService.findUserById(id);
        model.addAttribute("user", userDTO);
        return "admin/users-edit";
    }

    @PostMapping("/users/edit/{id}")
    public String editUser(@PathVariable Long id, @Valid @ModelAttribute("user") UserDTO userDTO,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/users-edit";
        }
        try {
            userService.updateUser(userDTO);
        } catch (Exception e) {
            result.rejectValue("username", "user.exists", "Username is already registered!");
            return "admin/users-edit";
        }

        redirectAttributes.addFlashAttribute("updatedUserId", userDTO.getId());
        return "redirect:/admin/users?success";
    }

    @GetMapping("/hotels")
    public String listHotels(Model model) {
        List<HotelDTO> hotelDTOList = hotelService.findAllHotels();
        model.addAttribute("hotels", hotelDTOList);
        return "admin/hotels";
    }

    @GetMapping("/bookings")
    public String listBookings() {
        return "admin/bookings";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }

}
