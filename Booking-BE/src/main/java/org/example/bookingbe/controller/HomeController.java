package org.example.bookingbe.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

//    @GetMapping("/home")
//    public String home(Model model) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
//            model.addAttribute("username", auth.getName()); // Lấy tên người dùng
//        }
//
//        return "client/homePage";
//    }
}

