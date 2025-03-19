package org.example.bookingbe.service.UserDetail;

import org.example.bookingbe.model.Hotel;
import org.example.bookingbe.model.User;
import org.example.bookingbe.repository.HotelRepo.IHotelRepo;
import org.example.bookingbe.repository.UserRepo.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private IUserRepo userRepository;

    @Autowired
    private IHotelRepo hotelRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Long hotelId = null;

        // Kiểm tra nếu user là Manager thì mới lấy hotelId
        if ("HOTEL_MANAGER".equalsIgnoreCase(user.getRole().getRoleName())) {
            hotelId = hotelRepo.findByUser(user).stream()
                    .map(Hotel::getId)
                    .findFirst()
                    .orElse(null);
        }

        System.out.println("User Role: " + user.getRole().getRoleName());
        return UserPriciple.create(user, hotelId);
    }
}


