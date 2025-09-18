package pl.twojafirma.nieruchomosci.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserRegistrationDto {
    @NotEmpty(message = "Nazwa użytkownika nie może być pusta.")
    private String username;

    @NotEmpty(message = "Email nie może być pusty.")
    @Email(message = "Proszę podać poprawny adres email.")
    private String email;

    @NotEmpty(message = "Hasło nie może być puste.")
    private String password;
}
