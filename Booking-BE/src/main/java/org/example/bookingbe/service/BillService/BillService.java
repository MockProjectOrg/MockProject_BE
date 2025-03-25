package org.example.bookingbe.service.BillService;

import jakarta.mail.MessagingException;
import org.example.bookingbe.model.Bill;
import org.example.bookingbe.repository.BillRepo.IBillRepo;
import org.example.bookingbe.repository.BookingRepo.IBookingRepo;
import org.example.bookingbe.service.MailSender.MailRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BillService implements IBillService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IBookingRepo bookingRepo;
    @Autowired
    private IBillRepo billRepo;
    @Autowired
    private MailRegister mailRegister;


    @Override
    public Bill save(Bill bill, String checkIn, String checkOut, String firstName, String lastName, String address, String email, String phone , Double price, String datePay) throws MessagingException {
        mailRegister.payMail(checkIn,checkOut,firstName,lastName,address,email,phone,price,datePay);
        return billRepo.save(bill);
    }
}
