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

import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepo userRepo;
    @Autowired
    private IRoleRepo roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailRegister mailRegister;
    public void registerUser(User user) throws MessagingException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepo.findByRoleName("USER");
        user.setRole(role);
        userRepo.save(user);
        mailRegister.sendEmailRegister(user.getEmail(), user.getFirstName(), user.getLastName());
    }

    @Override
    public Boolean existsUser(String username) {
        return userRepo.existsByUserName(username);
    }

    @Override
    public Boolean exstsEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepo.findByUserName(username);
    }
}
