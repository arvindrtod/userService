package com.Scaler.userService.services;

import com.Scaler.userService.models.Role;
import com.Scaler.userService.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    private RoleRepository roleRepository;
    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(String role) {
        Optional<Role> optionalRole = roleRepository.findByRole(role);
        if (optionalRole.isPresent()){
            throw new RuntimeException("Role is already present");
        }
        Role role1= new Role();
        role1.setRole(role);
        Role  savedRole = roleRepository.save(role1);
        return savedRole;
    }
}
