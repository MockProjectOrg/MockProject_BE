package org.example.bookingbe.service.UserService;

import org.example.bookingbe.model.User;

import java.util.Optional;

public interface IUserService {
    User registerUser(User user);
    Boolean existsUser(String username);
    Boolean exstsEmail(String email);

    Optional<User> findByUsername(String username);
    User getUserById(Long id);
}
