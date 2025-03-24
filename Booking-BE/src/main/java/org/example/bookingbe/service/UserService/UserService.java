package org.example.bookingbe.service.UserService;

import jakarta.mail.MessagingException;
import org.example.bookingbe.model.Role;
import org.example.bookingbe.model.User;
import org.example.bookingbe.repository.RoleRepo.IRoleRepo;
import org.example.bookingbe.repository.UserRepo.IUserRepo;
import org.example.bookingbe.service.CloudinaryService.CloudinaryService;
import org.example.bookingbe.service.MailSender.MailRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private final IUserRepo userRepo;
    @Autowired
    private final IRoleRepo roleRepo;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final MailRegister mailRegister;
    @Autowired
    private final CloudinaryService cloudinaryService;

    public UserService(IUserRepo userRepo, IRoleRepo roleRepo, MailRegister mailRegister, PasswordEncoder passwordEncoder, CloudinaryService cloudinaryService) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.mailRegister = mailRegister;
        this.passwordEncoder = passwordEncoder;
        this.cloudinaryService = cloudinaryService;
    }

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

    @Override
    public User getUserById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public void saveUser(User user) {
        userRepo.save(user);  // Lưu thông tin user đã chỉnh sửa
    }

    @Override
    public String uploadAvatar(MultipartFile file, Long userId) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty, please upload a valid image.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Invalid file type. Only images are allowed.");
        }

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        try {
            // Tạo publicId duy nhất cho avatar (mỗi user có một avatar riêng)
            String publicId = "avatar_" + userId;
            String avatarUrl = cloudinaryService.uploadFile(file, "user_avatars", publicId);

            // Cập nhật avatar mới vào database
            user.setAvatar(avatarUrl);
            userRepo.save(user);

            return avatarUrl;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload avatar to Cloudinary", e);
        }
    }
}
