package org.example.bookingbe;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = BookingBeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class BookingBeApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("Spring Boot Test is running!");
    }

}
