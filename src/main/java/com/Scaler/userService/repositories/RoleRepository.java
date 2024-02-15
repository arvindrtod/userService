package com.Scaler.userService.repositories;

import com.Scaler.userService.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    public Optional<Role> findByRole(String role);
    public List<Role> findAllByIdIn(List<Long> roleIds);
}
