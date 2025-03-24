package org.example.bookingbe.service.AdminService;

import org.example.bookingbe.model.Hotel;
import org.example.bookingbe.model.Role;
import org.example.bookingbe.model.User;
import org.example.bookingbe.repository.AdminRepo.IAdminRepo;
import org.example.bookingbe.repository.HotelRepo.IHotelRepo;
import org.example.bookingbe.repository.UserRepo.IUserRepo;
import org.example.bookingbe.service.MailSender.MailRegister;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService implements IAdminService {
    private final IUserRepo userRepo;
    private final IAdminRepo adminRepo;
    private final IHotelRepo hotelRepo;
    private final MailRegister emailService;

    public AdminService(IUserRepo userRepo, IAdminRepo adminRepo, IHotelRepo hotelRepo, MailRegister emailService) {
        this.userRepo = userRepo;
        this.adminRepo = adminRepo;
        this.hotelRepo = hotelRepo;
        this.emailService = emailService;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    @Transactional
    public void updateUserRole(Long userId, String newRoleName) {
        Optional<User> userOptional = userRepo.findById(userId);
        Role newRole = adminRepo.findByRoleName(newRoleName);

        if (userOptional.isPresent() && newRole != null) {
            User user = userOptional.get();
            user.setRole(newRole);
            userRepo.save(user);

            // Gửi email thông báo
            String subject = "Cập nhật quyền truy cập";
            String message = "Chào " + user.getUserName() + ",\n\n"
                    + "Quyền truy cập của bạn đã được cập nhật lên: " + newRole.getRoleName() + ".\n"
                    + "Vui lòng đăng nhập lại để tiếp tục sử dụng hệ thống.\n\n"
                    + "Trân trọng,\nĐội ngũ hỗ trợ.";

            emailService.sendEmail(user.getEmail(), subject, message);

            // Logout user sau khi đổi role
            SecurityContextHolder.getContext().setAuthentication(null);
        }
    }

    @Override
    public void updateUser(User updatedUser) {
        User existingUser = userRepo.findById(updatedUser.getId()).orElse(null);
        if (existingUser != null) {
            existingUser.setUserName(updatedUser.getUserName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhone(updatedUser.getPhone());
            existingUser.setAddress(updatedUser.getAddress());
            existingUser.setGender(updatedUser.getGender());
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setBirthday(updatedUser.getBirthday());

            userRepo.save(existingUser); // Lưu lại User vào database
        }
    }

    @Override
    public List<Hotel> getAllHotels() {
        List<Hotel> hotels = hotelRepo.findAll();
        return hotels != null ? hotels : new ArrayList<>();
    }


    @Override
    public Hotel getHotelById(Long id) {
        return hotelRepo.findById(id).orElse(null);
    }

    @Override
    public void saveHotel(Hotel hotel) {
        hotelRepo.save(hotel);
    }

    @Override
    public void updateHotel(Hotel hotel) {
        Hotel existingHotel = hotelRepo.findById(hotel.getId()).orElse(null);
        if (existingHotel != null) {
            existingHotel.setHotelName(hotel.getHotelName());
            existingHotel.setAddress(hotel.getAddress());
            existingHotel.setDescription(hotel.getDescription());
            existingHotel.setAvatar(hotel.getAvatar());
            existingHotel.setUser(hotel.getUser());
            hotelRepo.save(existingHotel);
        }
    }

    @Override
    public void deleteHotel(Long id) {
        hotelRepo.deleteById(id);
    }

}
