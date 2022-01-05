package com.demo.schoolregistration.controller;

import com.demo.schoolregistration.config.security.JwtUtilsService;
import com.demo.schoolregistration.payload.request.LoginUserRequest;
import com.demo.schoolregistration.payload.request.SignUpRequest;
import com.demo.schoolregistration.payload.response.JwtResponse;
import com.demo.schoolregistration.payload.response.MessageResponse;
import com.demo.schoolregistration.service.impl.UserAuthService;
import com.demo.schoolregistration.service.impl.UserDetailsImpl;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtUtilsService jwtUtilsService;
  private final UserAuthService userAuthService;

  public AuthController(AuthenticationManager authenticationManager, JwtUtilsService jwtUtilsService, UserAuthService userAuthService) {
    this.authenticationManager = authenticationManager;
    this.jwtUtilsService = jwtUtilsService;
    this.userAuthService = userAuthService;
  }

  @PostMapping("/signIn")
  public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginUserRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtilsService.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    return userAuthService.enrichUser(signUpRequest);
  }
}
