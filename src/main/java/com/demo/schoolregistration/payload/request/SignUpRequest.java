package com.demo.schoolregistration.payload.request;

import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {

  @NotBlank
  @Size(min = 3, max = 20)
  private String username;

  @NotBlank
  @Email
  @Size(max = 50)
  private String email;

  @NotBlank
  @Size(min = 6, max = 40)
  private String password;

  private Set<String> role;
}
