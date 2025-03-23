package org.example.bookingbe.service.AdminService;

import org.example.bookingbe.model.Hotel;
import org.example.bookingbe.model.User;
import java.util.List;

public interface IAdminService {
    List<User> getAllUsers();
    void deleteUser(Long id);
    void updateUserRole(Long userId, String newRoleName);
    void updateUser(User user);

    List<Hotel> getAllHotels();
    Hotel getHotelById(Long id);
    void saveHotel(Hotel hotel);
    void updateHotel(Hotel hotel);
    void deleteHotel(Long id);
}
