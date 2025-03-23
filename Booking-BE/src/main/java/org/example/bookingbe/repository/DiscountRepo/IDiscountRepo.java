package org.example.bookingbe.repository.DiscountRepo;

import org.example.bookingbe.dto.DiscountDto;
import org.example.bookingbe.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDiscountRepo extends JpaRepository<Discount, Long> {
    @Query(value = "select d.id, d.description, d.is_active, d.name, d.discount_value, d.create_at  ,du.expired_at, du.is_used, du.discount_code, d.hotel_id from discount as d join discount_user as du on d.id = du.discount_id join user as u on u.id = du.user_id join hotel as h on h.id = d.hotel_id" +
            " where h.id =:hotelId and d.is_active = 1 and du.is_used = 0 and u.user_name =:name", nativeQuery = true)
    List<DiscountDto> getAllDiscount(@Param("name") String userName, @Param("hotelId") Long hotelId);
}
