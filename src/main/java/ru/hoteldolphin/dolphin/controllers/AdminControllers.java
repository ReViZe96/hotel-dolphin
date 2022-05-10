package ru.hoteldolphin.dolphin.controllers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.hoteldolphin.dolphin.entities.Guests;
import ru.hoteldolphin.dolphin.services.GuestsService;

import java.util.List;

@Controller
@Getter
@Setter
public class AdminControllers {
    @Autowired
    private GuestsService guestsService;

    @GetMapping("/moderate")
    public String getModeratingPage(Model model) {
        List<Guests> notModedGuests = guestsService.findNotModedGuests();
        if (!(notModedGuests.isEmpty())) {
            model.addAttribute("notModedGuests", notModedGuests);
        }
        return "moderate";
    }

    @GetMapping("/showModedGuests")
    public String getModedGuestsPage(Model model) {
        List<Guests> allModedGuests = guestsService.findModedGuests();
        if (!(allModedGuests.isEmpty())) {
            model.addAttribute("allModedGuests", allModedGuests);
        }
        return "allModedGuests";
    }

    @PostMapping("/editGuestsName/{id}")
    public String editName(@PathVariable("id") Long id, @RequestParam("name") String name) {
        guestsService.setGuestsName(id, name);
        return "redirect:/moderate";
    }

    @PostMapping("/editGuestsPhone/{id}")
    public String editPhone(@PathVariable("id") Long id, @RequestParam("phone") String phone) {
        guestsService.setGuestsPhone(id, phone);
        return "redirect:/moderate";
    }

    @PostMapping("/editGuestsAmount/{id}")
    public String editAmount(@PathVariable("id") Long id, @RequestParam("amount") Integer amount) {
        guestsService.setGuestsAmount(id, amount);
        return "redirect:/moderate";
    }

    @PostMapping("/editGuestsCheckIn/{id}")
    public String editCheckIn(@PathVariable("id") Long id, @RequestParam("checkIn") String checkIn) {
        guestsService.setGuestsCheckIn(id, checkIn);
        return "redirect:/moderate";
    }

    @PostMapping("/editGuestsCheckOut/{id}")
    public String editCheckOut(@PathVariable("id") Long id, @RequestParam("checkOut") String checkOut) {
        guestsService.setGuestsCheckOut(id, checkOut);
        return "redirect:/moderate";
    }

    @PostMapping("/deleteGuest/{id}")
    public String deleteGuest(@PathVariable("id") Long id) {
        guestsService.deleteGuest(id);
        return "redirect:/moderate";
    }

    @PostMapping("/confirmGuest/{id}")
    public String confirmGuest(@PathVariable("id") Long id, @RequestParam("watched") String watched) {
        guestsService.setGuestsWatched(id, watched);
        return "redirect:/moderate";
    }

    @PostMapping("/findByName")
    public String findGuestsByName(@RequestParam("name") String name, Model model) {
        List<Guests> searchedGuests = guestsService.findGuestsByName(name);
        if (!(searchedGuests.isEmpty())) {
            Integer amountOfGuests = searchedGuests.size();
            model.addAttribute("searchedGuests", searchedGuests);
            model.addAttribute("amountG", amountOfGuests);
        }
        return "resultOfSearch";
    }

    @PostMapping("/findByPhone")
    public String findGuestsByPhone(@RequestParam("phone") String phone, Model model) {
        List<Guests> searchedGuests = guestsService.findGuestsByPhone(phone);
        if (!(searchedGuests.isEmpty())) {
            Integer amountOfGuests = searchedGuests.size();
            model.addAttribute("searchedGuests", searchedGuests);
            model.addAttribute("amountG", amountOfGuests);
        }
        return "resultOfSearch";
    }

    @PostMapping("/findByAmount")
    public String findGuestsByAmount(@RequestParam("amount") Integer amount, Model model) {
        List<Guests> searchedGuests = guestsService.findGuestsByAmount(amount);
        if (!(searchedGuests.isEmpty())) {
            Integer amountOfGuests = searchedGuests.size();
            model.addAttribute("searchedGuests", searchedGuests);
            model.addAttribute("amountG", amountOfGuests);
        }
        return "resultOfSearch";
    }

    @PostMapping("/findByCheckIn")
    public String findGuestsByCheckIn(@RequestParam("checkIn") String checkIn, Model model) {
        List<Guests> searchedGuests = guestsService.findGuestsByCheckIn(checkIn);
        if (!(searchedGuests.isEmpty())) {
            Integer amountOfGuests = searchedGuests.size();
            model.addAttribute("searchedGuests", searchedGuests);
            model.addAttribute("amountG", amountOfGuests);
        }
        return "resultOfSearch";
    }

    @PostMapping("/findByCheckOut")
    public String findGuestsByCheckOut(@RequestParam("checkOut") String checkOut, Model model) {
        List<Guests> searchedGuests = guestsService.findGuestsByCheckOut(checkOut);
        if (!(searchedGuests.isEmpty())) {
            Integer amountOfGuests = searchedGuests.size();
            model.addAttribute("searchedGuests", searchedGuests);
            model.addAttribute("amountG", amountOfGuests);
        }
        return "resultOfSearch";
    }
}