package org.example.bookingbe.repository.UserRepo;

import org.example.bookingbe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepo extends JpaRepository<User, Long> {
    User findByUserName(String username);

}
