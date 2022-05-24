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

import java.sql.Timestamp;
import java.util.List;

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
    public String makeBook(@RequestParam("name") String name, @RequestParam("phone") String phone,
                           @RequestParam("amountOfPeoples") Integer amountOfPeoples,
                           @RequestParam("amountOfRooms") Integer amountOfRooms,
                           @RequestParam("checkIn") String checkIn, @RequestParam("checkOut") String checkOut,
                           @RequestParam("info") String info, Model model) {
        String inf = info.replace(",", " ");
        String chIn = checkIn.replace('T', ' ') + ":00";
        String chOut = checkOut.replace('T', ' ') + ":00";
        Timestamp in = Timestamp.valueOf(chIn);
        Timestamp out = Timestamp.valueOf(chOut);
        Integer amountOfNights = guestsService.calculateAmountOfNight(in, out);
        Guests newGuest = new Guests(name, phone, amountOfPeoples, amountOfRooms, in, out, amountOfNights, inf, 'N', 'N');
        List<Guests> existGuests = guestsService.findByNameAndPhone(name, phone);
        if (existGuests != null) {
            boolean blocked = false;
            for (Guests existGuest : existGuests) {
                if (existGuest.getBlackList() == 'Y') {
                    blocked = true;
                    break;
                }
            }
            if (blocked) {
                model.addAttribute("blocked", "Y");
            } else {
                guestsService.save(newGuest);
                model.addAttribute("success", "Y");
            }
        } else {
            guestsService.save(newGuest);
            model.addAttribute("success", "Y");
        }
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