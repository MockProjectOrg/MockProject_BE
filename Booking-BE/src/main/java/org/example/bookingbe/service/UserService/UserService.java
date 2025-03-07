package org.example.bookingbe.service.UserService;

import org.example.bookingbe.model.Role;
import org.example.bookingbe.model.User;
import org.example.bookingbe.repository.RoleRepo.IRoleRepo;
import org.example.bookingbe.repository.UserRepo.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepo userRepo;
    @Autowired
    private IRoleRepo roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepo.findByRoleName("USER");
        user.setRole(role);
        return userRepo.save(user);
    }

    @Override
    public Boolean existsUser(String username) {
        return userRepo.existsByUserName(username);
    }

    @Override
    public Boolean exstsEmail(String email) {
        return userRepo.existsByEmail(email);
    }
}
