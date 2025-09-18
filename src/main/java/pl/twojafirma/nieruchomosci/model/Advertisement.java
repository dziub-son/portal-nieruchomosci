package pl.twojafirma.nieruchomosci.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Lob
    private String description;

    private BigDecimal price;
    private String city;
    private double area;
    private String phoneNumber;

    @Lob
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
}