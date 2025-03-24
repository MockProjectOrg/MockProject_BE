package org.example.bookingbe.service.BillService;

import jakarta.mail.MessagingException;
import org.example.bookingbe.model.Bill;

public interface IBillService {
    Bill save(Bill bill, String checkIn, String checkOut, String firstName, String lastName,String address, String email, String phone, Double price, String datePay) throws MessagingException;
}
