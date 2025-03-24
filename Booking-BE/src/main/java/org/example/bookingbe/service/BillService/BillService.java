package org.example.bookingbe.service.BillService;

import org.example.bookingbe.model.Bill;
import org.example.bookingbe.repository.BillRepo.IBillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillService implements IBillService {
    @Autowired
    private IBillRepo billRepo;
    @Override
    public Bill save(Bill bill) {
        return billRepo.save(bill);
    }
}
