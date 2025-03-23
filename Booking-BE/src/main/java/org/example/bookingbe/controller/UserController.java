package org.example.bookingbe.controller;

import jakarta.servlet.http.HttpSession;
import org.example.bookingbe.model.DiscountUser;
import org.example.bookingbe.model.User;
import org.example.bookingbe.repository.DiscountUserRepo.IDiscountUserRepo;
import org.example.bookingbe.respone.MessageRespone;
import org.example.bookingbe.service.UserDetail.UserPriciple;
import org.example.bookingbe.service.UserService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/api")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IDiscountUserRepo discountUserRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String login(){
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/Doregister")
    public String doRegister(@ModelAttribute("user") User user, Model model){
        System.out.println("User after add: " + user);
        if(userService.existsUser(user.getUserName()) || userService.exstsEmail(user.getEmail())){
            model.addAttribute("message", new MessageRespone("User or Email already exists"));
            return "auth/register";
        }
        userService.registerUser(user);
        return "redirect:auth/login";
    }


    @GetMapping("/user/userProfile")
    public String getUser(Model model) {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPriciple userPrinciple = (UserPriciple) authentication.getPrincipal();
        Long userId = userPrinciple.getId();

        // Get user details
        User user = userService.getUserById(userId);
        if (user == null) {
            return "redirect:/api/login";
        }

        // Get user's discounts
        List<DiscountUser> userDiscounts = discountUserRepo.findByUser(user);

        // Add attributes to the model
        model.addAttribute("user", user);
        model.addAttribute("discounts", userDiscounts);

        return "profile";
    }





}
