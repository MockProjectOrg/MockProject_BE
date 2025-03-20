package org.example.bookingbe.service.UserService;

import org.example.bookingbe.model.dto.UserDTO;

import java.util.List;

public interface IUserService {

    UserDTO createUser(UserDTO userDTO);

    List<UserDTO> findAllUsers();

    UserDTO findUserById(Long id);

    void deleteUserById(Long id);

}
