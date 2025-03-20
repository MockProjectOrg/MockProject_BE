package org.example.bookingbe.controller;

import jakarta.validation.Valid;
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

    @GetMapping("/users/add")
    public String showFormAddUser(Model model) {
        model.addAttribute("user", new UserDTO());
        return "admin/add-users";
    }

    @PostMapping("/users/add")
    public String addUser(@ModelAttribute @Valid  UserDTO user,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes,
                          Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error ->
                    System.out.println("Validation Error: " + error.getDefaultMessage()));
            return "admin/add-users";
        }

        user.setRole("Manager");

        userService.createUser(user);

        redirectAttributes.addFlashAttribute("successMessage", "User added successfully!");

        return "redirect:/admin/users";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }

}
