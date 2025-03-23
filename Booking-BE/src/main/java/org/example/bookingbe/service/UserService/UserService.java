package org.example.bookingbe.service.UserService;

import jakarta.mail.MessagingException;
import org.example.bookingbe.model.Role;
import org.example.bookingbe.model.User;
import org.example.bookingbe.repository.RoleRepo.IRoleRepo;
import org.example.bookingbe.repository.UserRepo.IUserRepo;
import org.example.bookingbe.service.MailSender.MailRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    private final IUserRepo userRepo;
    private final IRoleRepo roleRepo;
    private final MailRegister mailRegister;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserRepo userRepo, IRoleRepo roleRepo, MailRegister mailRegister, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.mailRegister = mailRegister;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User user) throws MessagingException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = roleRepo.findByRoleName("USER");
        if (role == null) {
            throw new IllegalStateException("Role 'USER' not found in database.");
        }

        user.setRole(role);
        userRepo.save(user);

        mailRegister.sendEmailRegister(user.getEmail(), user.getFirstName(), user.getLastName());
    }

    public Boolean existsUser(String username) {
        return userRepo.existsByUserName(username);
    }

    public Boolean existsEmail(String email) {
        return userRepo.existsByEmail(email);
    }
}
