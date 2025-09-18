package pl.twojafirma.nieruchomosci.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Controller
public class CalculatorController {

    @GetMapping("/kalkulator")
    public String showCalculator() {
        return "kalkulator";
    }

    @PostMapping("/kalkulator")
    public String calculate(
            @RequestParam("kwota") BigDecimal kwota,
            @RequestParam("okres") int okres,
            @RequestParam("oprocentowanie") double oprocentowanie,
            Model model) {

        double miesieczneOprocentowanie = oprocentowanie / 100 / 12;
        int liczbaRat = okres * 12;

        double rataMiesieczna = kwota.doubleValue() *
                (miesieczneOprocentowanie * Math.pow(1 + miesieczneOprocentowanie, liczbaRat)) /
                (Math.pow(1 + miesieczneOprocentowanie, liczbaRat) - 1);

        BigDecimal rata = BigDecimal.valueOf(rataMiesieczna).setScale(2, RoundingMode.HALF_UP);

        model.addAttribute("rata", rata);
        model.addAttribute("showResult", true);
        return "kalkulator";
    }
}
