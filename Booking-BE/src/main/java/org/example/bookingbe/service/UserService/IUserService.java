package org.example.bookingbe.service.UserService;

import org.example.bookingbe.model.User;

public interface IUserService {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void registerUser(User user);

    Boolean existsUser(String username);
}
