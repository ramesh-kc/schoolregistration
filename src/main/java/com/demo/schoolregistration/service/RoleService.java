package com.demo.schoolregistration.service;

import com.demo.schoolregistration.model.Role;
import com.demo.schoolregistration.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
  private final RoleRepository roleRepository;

  public RoleService(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  public Role findRoleByName(String name) {
    return roleRepository.findRoleByName(name);
  }
}
