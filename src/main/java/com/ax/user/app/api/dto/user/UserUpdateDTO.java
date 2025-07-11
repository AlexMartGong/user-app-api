package com.ax.user.app.api.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @NotBlank
    private String username;

    @Email(message = "Email should be valid")
    @NotBlank
    private String email;

}
