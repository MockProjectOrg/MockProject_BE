package org.example.bookingbe.service.DiscountService;

import org.example.bookingbe.dto.DiscountDto;

import java.util.List;

public interface IDiscountService {
    List<DiscountDto> getDiscounts(String userName, Long roomId);
}
