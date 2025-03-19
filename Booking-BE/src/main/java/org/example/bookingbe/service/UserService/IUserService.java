package org.example.bookingbe.service.UserService;

import jakarta.mail.MessagingException;
import org.example.bookingbe.model.User;

import java.util.Optional;

public interface IUserService {
    void registerUser(User user) throws MessagingException;
    Boolean existsUser(String username);
    Boolean exstsEmail(String email);

    Optional<User> findByUsername(String username);
}
