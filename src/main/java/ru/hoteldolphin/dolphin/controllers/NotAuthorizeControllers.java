package ru.hoteldolphin.dolphin.controllers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.hoteldolphin.dolphin.entities.Guests;
import ru.hoteldolphin.dolphin.services.GuestsService;

@Controller
@Getter
@Setter
public class NotAuthorizeControllers {
    @Autowired
    private GuestsService guestsService;

    @RequestMapping(value = "/mainPage", method = RequestMethod.GET)
    public String getMainPage() {
        return "mainPage";
    }

    @RequestMapping(value = "/rooms", method = RequestMethod.GET)
    public String getRoomsPage() {
        return "rooms";
    }

    @RequestMapping(value = "/gallery", method = RequestMethod.GET)
    public String getGalleryPage() {
        return "gallery";
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public String getContactsPage() {
        return "contacts";
    }

    @RequestMapping(value = "/services", method = RequestMethod.GET)
    public String getServicesPage() {
        return "services";
    }

    @RequestMapping(value = "/bookPage", method = RequestMethod.GET)
    public String getBookPage() {
        return "book";
    }

    @RequestMapping(value = "/makeBook", method = RequestMethod.POST)
    public String makeBook(@RequestParam("name") String name, @RequestParam("amount") Integer amount,
                           @RequestParam("phone") String phone,
                           @RequestParam("checkIn") String checkIn, @RequestParam("checkOut") String checkOut) {
        Guests newGuest = new Guests(name, amount, phone, checkIn, checkOut, "N");
        guestsService.save(newGuest);
        return "bookOnModerating";
    }

    @RequestMapping(value = "/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout, Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "login";
    }
}