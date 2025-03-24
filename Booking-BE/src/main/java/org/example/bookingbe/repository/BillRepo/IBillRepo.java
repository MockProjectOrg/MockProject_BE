package org.example.bookingbe.repository.BillRepo;

import org.example.bookingbe.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBillRepo extends JpaRepository<Bill, Long> {

    @Query("SELECT COALESCE(SUM(b.totalPrice), 0) FROM Bill b WHERE MONTH(b.datePayment) = :month AND YEAR(b.datePayment) = :year")
    Double getTotalRevenue(@Param("month") int month, @Param("year") int year);

    @Query("SELECT SUM(b.totalPrice) FROM Bill b WHERE YEAR(b.datePayment) = :year")
    Double getTotalRevenueThisYear(@Param("year") int year);

    @Query("SELECT SUM(b.totalPrice) FROM Bill b WHERE YEAR(b.datePayment) = :year AND MONTH(b.datePayment) = :month")
    Double getPreviousMonthlyRevenue(@Param("year") int year, @Param("month") int month);

    @Query("SELECT COALESCE(SUM(b.totalPrice), 0) FROM Bill b WHERE YEAR(b.datePayment) = :year")
    Double getTotalRevenueLastYear(@Param("year") int year);

    @Query("SELECT MONTH(b.datePayment), COALESCE(SUM(b.totalPrice), 0) FROM Bill b " +
            "WHERE YEAR(b.datePayment) = YEAR(CURRENT_DATE) " +
            "GROUP BY MONTH(b.datePayment) ORDER BY MONTH(b.datePayment)")
    List<Object[]> getMonthlyRevenue();

    @Query("SELECT MONTH(b.datePayment), COALESCE(COUNT(b), 0) " +
            "FROM Bill b WHERE YEAR(b.datePayment) = YEAR(CURRENT_DATE) " +
            "GROUP BY MONTH(b.datePayment) ORDER BY MONTH(b.datePayment)")
    List<Object[]> countBookingsByMonth();
}
