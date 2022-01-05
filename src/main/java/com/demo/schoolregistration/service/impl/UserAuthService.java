package com.demo.schoolregistration.service.impl;

import com.demo.schoolregistration.config.security.JwtUtilsService;
import com.demo.schoolregistration.model.EnumRole;
import com.demo.schoolregistration.model.Role;
import com.demo.schoolregistration.model.User;
import com.demo.schoolregistration.payload.request.LoginUserRequest;
import com.demo.schoolregistration.payload.request.SignUpRequest;
import com.demo.schoolregistration.payload.response.JwtResponse;
import com.demo.schoolregistration.payload.response.MessageResponse;
import com.demo.schoolregistration.repository.RoleRepository;
import com.demo.schoolregistration.repository.UserRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {

  public static final String ERROR_ROLE_IS_NOT_FOUND = "Error: Role is not found.";

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  private final AuthenticationManager authenticationManager;
  private final JwtUtilsService jwtUtilsService;

  public UserAuthService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtilsService jwtUtilsService) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.jwtUtilsService = jwtUtilsService;
  }

  public ResponseEntity<MessageResponse> enrichUser(SignUpRequest signUpRequest) {

    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    }

    User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), passwordEncoder.encode(signUpRequest.getPassword()));
    Set<String> inputRequestRoles = signUpRequest.getRole();
    Set<Role> roles = enrichRoles(inputRequestRoles);

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  public ResponseEntity<JwtResponse> authenticateUser( LoginUserRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtilsService.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
  }

  public Set<Role> enrichRoles(Set<String> inputRequestRoles) {
    Set<Role> roles = new HashSet<>();

    if (CollectionUtils.isEmpty(inputRequestRoles)) {
      Role userRole = roleRepository.findRoleByName(EnumRole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException(ERROR_ROLE_IS_NOT_FOUND));
      roles.add(userRole);

    } else {
      inputRequestRoles.forEach(
          role -> {
            switch (role) {
              case "admin":
                Role adminRole = roleRepository.findRoleByName(EnumRole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException(ERROR_ROLE_IS_NOT_FOUND));
                roles.add(adminRole);

                break;
              case "mod":
                Role modRole = roleRepository.findRoleByName(EnumRole.ROLE_MODERATOR)
                        .orElseThrow(() -> new RuntimeException(ERROR_ROLE_IS_NOT_FOUND));
                roles.add(modRole);

                break;
              default:
                Role userRole = roleRepository.findRoleByName(EnumRole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException(ERROR_ROLE_IS_NOT_FOUND));
                roles.add(userRole);
            }
          });
    }
    return roles;
  }
}
