package org.example.bookingbe.service.UserService;

import org.example.bookingbe.model.User;
import org.example.bookingbe.model.dto.UserDTO;

import java.util.List;

public interface IUserService {

    List<UserDTO> findAllUsers();

    UserDTO findUserById(Long id);

    void updateUser(UserDTO userDTO);

    void deleteUserById(Long id);

}
