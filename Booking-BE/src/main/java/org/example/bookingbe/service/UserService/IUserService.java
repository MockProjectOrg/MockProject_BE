package org.example.bookingbe.service.UserService;

import jakarta.mail.MessagingException;
import org.example.bookingbe.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface IUserService {
    void registerUser(User user) throws MessagingException;

    Boolean existsUser(String username);
    Boolean exstsEmail(String email);

    Optional<User> findByUsername(String username);
    User getUserById(Long id);

    void saveUser(User user);


    String uploadAvatar(MultipartFile file, Long userId);
}
