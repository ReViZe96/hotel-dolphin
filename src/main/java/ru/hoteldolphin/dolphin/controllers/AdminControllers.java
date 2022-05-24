package ru.hoteldolphin.dolphin.controllers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.hoteldolphin.dolphin.entities.Guests;
import ru.hoteldolphin.dolphin.services.GuestsService;

import java.util.*;

@Controller
@Getter
@Setter
public class AdminControllers {
    @Autowired
    private GuestsService guestsService;
    private List<Guests> recentList;
    private Integer recentAmount;
    private String typeOfPage;

    @GetMapping("/moderate")
    public String getModeratingPage(Model model) {
        List<Guests> notModedGuests = guestsService.findNotModedGuests();
        if (!(notModedGuests.isEmpty())) {
            model.addAttribute("notModedGuests", notModedGuests);
            recentList = notModedGuests;
            typeOfPage = "moderate";
        }
        return "moderate";
    }

    @GetMapping("/showModedGuests")
    public String getModedGuestsPage(Model model) {
        List<Guests> allModedGuests = guestsService.findModedGuests();
        if (!(allModedGuests.isEmpty())) {
            model.addAttribute("allModedGuests", allModedGuests);
            recentList = allModedGuests;
            typeOfPage = "allModed";
        }
        return "allModedGuests";
    }

    @GetMapping("/blackList")
    public String getBlackList(Model model) {
        List<Guests> blockedGuests = guestsService.findBlockedGuests();
        if (!(blockedGuests.isEmpty())) {
            model.addAttribute("blockedGuests", blockedGuests);
            recentList = blockedGuests;
            typeOfPage = "blackList";
        }
        return "blackList";
    }

    @PostMapping("/sortGuests")
    public String sortGuests(@RequestParam("sortingType") String sortingType, Model model) {
        String url = null;
        List<Guests> resultGuests = new ArrayList<>();
        switch (sortingType) {
            case "ascIn":
                resultGuests = Guests.sortByIn(recentList);
                break;
            case "descIn":
                resultGuests = Guests.sortByIn(recentList);
                Collections.reverse(resultGuests);
                break;
            case "ascOut":
                resultGuests = Guests.sortByOut(recentList);
                break;
            case "descOut":
                resultGuests = Guests.sortByOut(recentList);
                Collections.reverse(resultGuests);
                break;
            case "ascName":
                resultGuests = Guests.sortByName(recentList);
                break;
            case "descName":
                resultGuests = Guests.sortByName(recentList);
                Collections.reverse(resultGuests);
                break;
            case "ascPeoples":
                resultGuests = Guests.sortByPeoples(recentList);
                break;
            case "descPeoples":
                resultGuests = Guests.sortByPeoples(recentList);
                Collections.reverse(resultGuests);
                break;
            case "ascRooms":
                resultGuests = Guests.sortByRooms(recentList);
                break;
            case "descRooms":
                resultGuests = Guests.sortByRooms(recentList);
                Collections.reverse(resultGuests);
                break;
            case "ascNights":
                resultGuests = Guests.sortByNights(recentList);
                break;
            case "descNights":
                resultGuests = Guests.sortByNights(recentList);
                Collections.reverse(resultGuests);
                break;
        }
        switch (typeOfPage) {
            case "moderate":
                model.addAttribute("notModedGuests", resultGuests);
                url = "moderate";
                break;
            case "allModed":
                model.addAttribute("allModedGuests", resultGuests);
                url = "allModedGuests";
                break;
            case "blackList":
                model.addAttribute("blockedGuests", resultGuests);
                url = "blackList";
                break;
            case "resOfSearch":
                model.addAttribute("searchedGuests", resultGuests);
                model.addAttribute("amountG", recentAmount);
                url = "resultOfSearch";
        }
        return url;
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

    @PostMapping("/editGuestsAmountOfPeoples/{id}")
    public String editAmountOfPeoples(@PathVariable("id") Long id, @RequestParam("amount") Integer amountOfPeoples) {
        guestsService.setGuestsAmountOfPeoples(id, amountOfPeoples);
        return "redirect:/moderate";
    }

    @PostMapping("/editGuestsAmountOfRooms/{id}")
    public String editAmountOfRooms(@PathVariable("id") Long id, @RequestParam("amount") Integer amountOfRooms) {
        guestsService.setGuestsAmountOfRooms(id, amountOfRooms);
        return "redirect:/moderate";
    }

    @PostMapping("/editGuestsAmountOfNights/{id}")
    public String editAmountOfNights(@PathVariable("id") Long id, @RequestParam("amount") Integer amountOfNights) {
        guestsService.setGuestsAmountOfNights(id, amountOfNights);
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
    public String confirmGuest(@PathVariable("id") Long id, @RequestParam("booked") Character booked) {
        guestsService.setGuestsBooked(id, booked);
        String url = null;
        switch (typeOfPage) {
            case "allModed":
                url = "redirect:/showModedGuests";
                break;
            default:
                url = "redirect:/moderate";
                break;
        }
        return url;
    }

    @PostMapping("/blockGuest/{id}")
    public String blockGuest(@PathVariable("id") Long id, @RequestParam("blocked") Character blocked) {
        guestsService.setGuestsBlock(id, blocked);
        String url = null;
        switch (typeOfPage) {
            case "allModed":
                url = "redirect:/showModedGuests";
                break;
            case "moderate":
            case "resOfSearch":
                url = "redirect:/moderate";
                break;
            case "blackList":
                url = "redirect:/blackList";
                break;
        }
        return url;
    }

    @PostMapping("/findByName")
    public String findGuestsByName(@RequestParam("name") String name, Model model) {
        List<Guests> searchedGuests = guestsService.findGuestsByName(name);
        if (!(searchedGuests.isEmpty())) {
            Integer amountOfGuests = searchedGuests.size();
            model.addAttribute("searchedGuests", searchedGuests);
            model.addAttribute("amountG", amountOfGuests);
            recentList = searchedGuests;
            recentAmount = amountOfGuests;
            typeOfPage = "resOfSearch";
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
            recentList = searchedGuests;
            recentAmount = amountOfGuests;
            typeOfPage = "resOfSearch";
        }
        return "resultOfSearch";
    }

    @PostMapping("/findByAmountOfPeoples")
    public String findGuestsByAmountOfPeoples(@RequestParam("amount") Integer amountOfPeoples, Model model) {
        List<Guests> searchedGuests = guestsService.findGuestsByPeoplesAmount(amountOfPeoples);
        if (!(searchedGuests.isEmpty())) {
            Integer amountOfGuests = searchedGuests.size();
            model.addAttribute("searchedGuests", searchedGuests);
            model.addAttribute("amountG", amountOfGuests);
            recentList = searchedGuests;
            recentAmount = amountOfGuests;
            typeOfPage = "resOfSearch";
        }
        return "resultOfSearch";
    }

    @PostMapping("/findByAmountOfRooms")
    public String findGuestsByAmountOfRooms(@RequestParam("amount") Integer amountOfRooms, Model model) {
        List<Guests> searchedGuests = guestsService.findGuestsByRoomsAmount(amountOfRooms);
        if (!(searchedGuests.isEmpty())) {
            Integer amountOfGuests = searchedGuests.size();
            model.addAttribute("searchedGuests", searchedGuests);
            model.addAttribute("amountG", amountOfGuests);
            recentList = searchedGuests;
            recentAmount = amountOfGuests;
            typeOfPage = "resOfSearch";
        }
        return "resultOfSearch";
    }

    @PostMapping("/findByAmountOfNights")
    public String findGuestsByAmountOfNights(@RequestParam("amount") Integer amountOfNights, Model model) {
        List<Guests> searchedGuests = guestsService.findGuestsByNightsAmount(amountOfNights);
        if (!(searchedGuests.isEmpty())) {
            Integer amountOfGuests = searchedGuests.size();
            model.addAttribute("searchedGuests", searchedGuests);
            model.addAttribute("amountG", amountOfGuests);
            recentList = searchedGuests;
            recentAmount = amountOfGuests;
            typeOfPage = "resOfSearch";
        }
        return "resultOfSearch";
    }

    @PostMapping("/findAllInInterval")
    public String findAllGuestsInInterval(@RequestParam("checkIn") String checkIn, @RequestParam("checkOut") String checkOut, Model model) {
        List<Guests> searchedGuests = guestsService.findAllInTimeInterval(checkIn, checkOut);
        if (!(searchedGuests.isEmpty())) {
            Integer amountOfGuests = searchedGuests.size();
            model.addAttribute("searchedGuests", searchedGuests);
            model.addAttribute("amountG", amountOfGuests);
            recentList = searchedGuests;
            recentAmount = amountOfGuests;
            typeOfPage = "resOfSearch";
        }
        return "resultOfSearch";
    }

    @PostMapping("/saveHistoryInDoc")
    public String saveHistory(@RequestParam("type") String type, Model model) {
        List<Guests> guests = new ArrayList<>();
        if (type.equals("history")) {
            guests = guestsService.findModedGuests();
            model.addAttribute("history", "history");
        } else if (type.equals("blackList")) {
            guests = guestsService.findBlockedGuests();
            model.addAttribute("blackList", "BL");
        }
        List<String> allGuests = guestsService.convertEntitiesToStrings(guests);
        String status = guestsService.writeToFile(allGuests);
        model.addAttribute("status", status);
        return "statusOfDownloading";
    }
}