package com.demo.schoolregistration.controller;

import com.demo.schoolregistration.payload.request.LoginUserRequest;
import com.demo.schoolregistration.payload.request.SignUpRequest;
import com.demo.schoolregistration.payload.response.JwtResponse;
import com.demo.schoolregistration.payload.response.MessageResponse;
import com.demo.schoolregistration.service.impl.UserAuthService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class AuthController {

  private final UserAuthService userAuthService;

  public AuthController(UserAuthService userAuthService) {
    this.userAuthService = userAuthService;
  }

  @PostMapping("/authenticate")
  public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginUserRequest loginRequest) {
    return userAuthService.authenticateUser(loginRequest);
  }

  @PostMapping("/register")
  public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    return userAuthService.enrichUser(signUpRequest);
  }
}
