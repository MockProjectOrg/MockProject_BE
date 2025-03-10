package org.example.bookingbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })

public class BookingBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingBeApplication.class, args);
    }

}
