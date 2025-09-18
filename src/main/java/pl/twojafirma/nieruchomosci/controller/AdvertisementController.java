package pl.twojafirma.nieruchomosci.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.twojafirma.nieruchomosci.dto.AdvertisementDto;
import pl.twojafirma.nieruchomosci.model.Advertisement;
import pl.twojafirma.nieruchomosci.model.User;
import pl.twojafirma.nieruchomosci.repository.AdvertisementRepository;
import pl.twojafirma.nieruchomosci.service.UserService;

@Controller
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdvertisementRepository advertisementRepository;
    private final UserService userService;

    @GetMapping("/")
    public String home() {
        return "redirect:/ogloszenia";
    }

    @GetMapping("/ogloszenia")
    public String listAdvertisements(Model model) {
        model.addAttribute("advertisements", advertisementRepository.findAll());
        return "ogloszenia";
    }

    @GetMapping("/ogloszenia/{id}")
    public String advertisementDetails(@PathVariable Long id, Model model) {
        Advertisement ad = advertisementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nieprawidłowe ID ogłoszenia:" + id));
        model.addAttribute("advertisement", ad);
        return "szczegoly";
    }

    @GetMapping("/dodaj-ogloszenie")
    public String showAddForm(Model model) {
        model.addAttribute("advertisementDto", new AdvertisementDto());
        return "formularz-ogloszenia";
    }

    @PostMapping("/dodaj-ogloszenie")
    public String addAdvertisement(@Valid @ModelAttribute("advertisementDto") AdvertisementDto advertisementDto,
                                   BindingResult result,
                                   Authentication authentication) {
        if (result.hasErrors()) {
            return "formularz-ogloszenia";
        }

        String username = authentication.getName();
        User user = userService.findByUsername(username);

        Advertisement advertisement = new Advertisement();
        advertisement.setTitle(advertisementDto.getTitle());
        advertisement.setDescription(advertisementDto.getDescription());
        advertisement.setPrice(advertisementDto.getPrice());
        advertisement.setCity(advertisementDto.getCity());
        advertisement.setArea(advertisementDto.getArea());
        advertisement.setPhoneNumber(advertisementDto.getPhoneNumber());
        advertisement.setImageUrl(advertisementDto.getImageUrl());
        advertisement.setAuthor(user);

        advertisementRepository.save(advertisement);
        return "redirect:/ogloszenia";
    }
}