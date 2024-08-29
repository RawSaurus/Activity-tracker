package com.miroslav.acitivity_tracker.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationRequest {

    @NotEmpty(message = "Field is mandatory")
    @NotBlank(message = "Field is mandatory")
    private String firstname;
    @NotEmpty(message = "Field is mandatory")
    @NotBlank(message = "Field is mandatory")
    private String lastname;
    @NotEmpty(message = "Field is mandatory")
    @NotBlank(message = "Field is mandatory")
    @Email(message = "This isn't correct format for email")
    private String email;
    @NotEmpty(message = "Field is mandatory")
    @NotBlank(message = "Field is mandatory")
    @Size(min = 8)
    private String password;

}
