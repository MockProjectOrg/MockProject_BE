package org.example.bookingbe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/pay")
public class PaymentController {
    @GetMapping("/")
    public String pay() {
        return "client/payment";
    }
}
