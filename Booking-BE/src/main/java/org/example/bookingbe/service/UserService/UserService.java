package org.example.bookingbe.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.bookingbe.model.User;
import org.example.bookingbe.model.dto.UserDTO;
import org.example.bookingbe.repository.UserRepo.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepo userRepo;

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> userList = userRepo.findAll();

        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : userList) {
            UserDTO userDTO = mapUserToUserDto(user);
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
        return mapUserToUserDto(user);
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        User user =userRepo.findById(userDTO.getId())
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found")
                );

        if (usernameExistsAndNotSameUser(userDTO.getUserName(), user.getId())) {
            throw new RuntimeException("This username is already registered!");
        }

        setFormattedDataToUser(user, userDTO);
        userRepo.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepo.deleteById(id);
    }

    private void setFormattedDataToUser(User user, UserDTO userDTO) {
        user.setUserName(userDTO.getUserName());
        user.setAddress((userDTO.getAddress()));
        user.setPhone(userDTO.getPhone());
    }

    private boolean usernameExistsAndNotSameUser(String username, Long userId) {
        Optional<User> existingUserWithSameUsername = Optional.ofNullable(userRepo.findByUserName(username));
        return existingUserWithSameUsername.isPresent() && !existingUserWithSameUsername.get().getId().equals(userId);
    }

    private UserDTO mapUserToUserDto(User user) {
        return new UserDTO(
               user.getId(),
               user.getUserName(),
               user.getEmail(),
               user.getRole().getRoleName()
        );
    }
}
