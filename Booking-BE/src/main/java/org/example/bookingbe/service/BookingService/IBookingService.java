package org.example.bookingbe.service.BookingService;

import java.util.List;

public interface IBookingService {

    Double getTotalRevenue();

    List<Double> getMonthlyRevenue();

    Double getTotalOrder();

    List<Double> getQuarterlyRevenue();

    List<Double> getYearlyRevenue();

}
