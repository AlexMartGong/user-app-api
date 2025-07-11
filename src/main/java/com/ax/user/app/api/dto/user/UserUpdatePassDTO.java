package com.ax.user.app.api.dto.user;

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
public class UserUpdatePassDTO {
    @NotBlank(message = "Old password cannot be blank")
    @Size(min = 6, max = 50, message = "Old password must be between 6 and 50 characters")
    private String oldPassword;

    @NotBlank(message = "New password cannot be blank")
    @Size(min = 6, max = 50, message = "New password must be between 6 and 50 characters")
    private String newPassword;
}
