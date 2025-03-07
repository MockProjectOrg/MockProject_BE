package org.example.bookingbe.service.UserService;

import org.example.bookingbe.model.User;

public interface IUserService {
    User registerUser(User user);
    Boolean existsUser(String username);
    Boolean exstsEmail(String email);
}
