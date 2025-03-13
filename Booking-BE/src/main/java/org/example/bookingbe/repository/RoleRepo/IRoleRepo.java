package org.example.bookingbe.repository.RoleRepo;

import org.example.bookingbe.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleName);
}
