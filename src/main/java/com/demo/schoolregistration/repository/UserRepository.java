package com.demo.schoolregistration.repository;

import com.demo.schoolregistration.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findUserByUsername(String username);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);
}
