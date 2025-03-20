package org.example.bookingbe.repository.RoleRepo;

import org.example.bookingbe.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepo extends JpaRepository<Role, Long> {

    Role findByRoleName(String roleName);

}
