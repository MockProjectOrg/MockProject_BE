package org.example.bookingbe.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.example.bookingbe.model.User;
import org.example.bookingbe.respone.MessageRespone;
import org.example.bookingbe.service.UserService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
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

    @GetMapping("/")
    public String login(HttpServletRequest request){
        if(request.getUserPrincipal() !=null){
            return "redirect:/";
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model, HttpServletRequest request){
        if(request.getUserPrincipal() !=null){
            return "redirect:/api/user/home";
        }
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/Doregister")
    public String doRegister(@ModelAttribute("user") @Valid User user, BindingResult result, Model model) throws MessagingException {
        if(userService.existsUser(user.getUserName()) || userService.exstsEmail(user.getEmail())){
            model.addAttribute("message", new MessageRespone("User or Email already exists"));
            return "auth/register";
        }
        if(result.hasErrors()){
            return "auth/register";
        }
        userService.registerUser(user);
        return "redirect:/api/";
    }

    @GetMapping("/user/userProfile")
    public String getUser(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/api/login";
        }

        model.addAttribute("userId", userId);
        return "auth/profile";
    }







}
