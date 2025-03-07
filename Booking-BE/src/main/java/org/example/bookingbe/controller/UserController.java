package org.example.bookingbe.controller;

import org.example.bookingbe.model.User;
import org.example.bookingbe.respone.MessageRespone;
import org.example.bookingbe.service.UserDetail.UserPriciple;
import org.example.bookingbe.service.UserService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/Doregister")
    public String doRegister(@ModelAttribute("user") User user, Model model){
        System.out.println("User after add: " + user);
        if(userService.existsUser(user.getUserName()) || userService.exstsEmail(user.getEmail())){
            model.addAttribute("message", new MessageRespone("User or Email already exists"));
            return "register";
        }
        userService.registerUser(user);
        return "redirect:/api/login";
    }

    @GetMapping("/user/home")
    public String getUser(){
        return "profile";
    }





}
