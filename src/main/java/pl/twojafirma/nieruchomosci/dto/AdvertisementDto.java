package pl.twojafirma.nieruchomosci.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class AdvertisementDto {
    private Long id;

    @NotEmpty(message = "Tytuł nie może być pusty.")
    @Size(min = 5, message = "Tytuł musi mieć co najmniej 5 znaków.")
    private String title;

    @NotEmpty(message = "Opis nie może być pusty.")
    private String description;

    @NotNull(message = "Cena jest wymagana.")
    @DecimalMin(value = "1.0", message = "Cena musi być większa niż 0.")
    private BigDecimal price;

    @NotEmpty(message = "Miasto nie może być puste.")
    private String city;

    @NotNull(message = "Powierzchnia jest wymagana.")
    private double area;

    @NotEmpty(message = "Numer telefonu nie może być pusty.")
    private String phoneNumber;

    @NotEmpty(message = "URL do zdjęcia nie może być pusty.")
    private String imageUrl;
}
