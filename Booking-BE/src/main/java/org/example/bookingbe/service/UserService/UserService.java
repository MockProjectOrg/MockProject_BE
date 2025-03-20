package org.example.bookingbe.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import org.example.bookingbe.model.Role;
import org.example.bookingbe.model.User;
import org.example.bookingbe.model.dto.UserDTO;
import org.example.bookingbe.repository.RoleRepo.IRoleRepo;
import org.example.bookingbe.repository.UserRepo.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Service
public class UserService implements IUserService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private IRoleRepo roleRepo;

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(user.getUserName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setAddress(user.getAddress());
        userDTO.setRole(user.getRole().getRoleName());
        return userDTO;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Role role  = roleRepo.findByRoleName("Manager");
        user.setRole(role);

        User createdUser = userRepo.save(user);
        return convertToDTO(createdUser);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> userList = userRepo.findAll();

        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : userList) {
            UserDTO userDTO = convertToDTO(user);
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    @Override
    public UserDTO findUserById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("User not found")
                );
        return convertToDTO(user);
    }


    @Override
    public void deleteUserById(Long id) {
        userRepo.deleteById(id);
    }

}
