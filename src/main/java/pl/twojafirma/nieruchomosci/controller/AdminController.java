package pl.twojafirma.nieruchomosci.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.twojafirma.nieruchomosci.model.Advertisement;
import pl.twojafirma.nieruchomosci.model.User;
import pl.twojafirma.nieruchomosci.repository.AdvertisementRepository;
import pl.twojafirma.nieruchomosci.service.UserService;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final AdvertisementRepository advertisementRepository;

    @GetMapping
    public String adminPanel(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("advertisements", advertisementRepository.findAll());
        return "admin/dashboard";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "admin/edit-user";
    }

    @PostMapping("/users/edit/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("user") User user) {
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }


    @GetMapping("/ads/edit/{id}")
    public String showEditAdForm(@PathVariable Long id, Model model) {
        Advertisement ad = advertisementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nieprawidłowe ID ogłoszenia:" + id));
        model.addAttribute("advertisement", ad);
        return "admin/edit-ad";
    }

    @PostMapping("/ads/edit/{id}")
    public String updateAd(@PathVariable Long id, @ModelAttribute("advertisement") Advertisement adDetails) {
        Advertisement ad = advertisementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nieprawidłowe ID ogłoszenia:" + id));

        ad.setTitle(adDetails.getTitle());
        ad.setDescription(adDetails.getDescription());
        ad.setPrice(adDetails.getPrice());
        ad.setCity(adDetails.getCity());
        ad.setArea(adDetails.getArea());
        ad.setPhoneNumber(adDetails.getPhoneNumber());
        ad.setImageUrl(adDetails.getImageUrl());

        advertisementRepository.save(ad);
        return "redirect:/admin";
    }

    @PostMapping("/ads/delete/{id}")
    public String deleteAd(@PathVariable Long id) {
        advertisementRepository.deleteById(id);
        return "redirect:/admin";
    }
}