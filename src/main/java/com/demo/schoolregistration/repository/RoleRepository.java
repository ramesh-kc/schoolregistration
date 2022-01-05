package com.demo.schoolregistration.repository;

import com.demo.schoolregistration.model.EnumRole;
import com.demo.schoolregistration.model.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findRoleByName(EnumRole name);
}
