package ru.hoteldolphin.dolphin;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.hoteldolphin.dolphin.controllers.AdminControllers;
import ru.hoteldolphin.dolphin.entities.Guests;
import ru.hoteldolphin.dolphin.services.GuestsService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminControllers.class)
@ContextConfiguration(classes = DolphinApplication.class)
@Setter
@Getter
public class AdminControllersTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GuestsService guestsService;
    private Guests firstGuest;
    private Guests secondGuest;
    private Guests thirdGuest;
    private Guests fourthGuest;
    private List<Guests> notModedList;
    private List<Guests> modedList;
    private List<Guests> blackList;
    private List<String> guestsStrings;
    private Integer amountOfModed;
    private Integer amountOfNotModed;

    @BeforeEach
    public void createTestDatas() {
        notModedList = new ArrayList<>();
        modedList = new ArrayList<>();
        blackList = new ArrayList<>();
        guestsStrings = new ArrayList<>();
        firstGuest = new Guests("First", "88888888888", 2, 1, Timestamp.valueOf("2001-01-01 12:00:00"),
                Timestamp.valueOf("2002-02-02 12:00:00"), 4, "Some information", 'N', 'N');
        notModedList.add(firstGuest);
        secondGuest = new Guests("First", "88888888888", 2, 1, Timestamp.valueOf("2001-01-01 12:00:00"),
                Timestamp.valueOf("2002-02-02 12:00:00"), 4, "Some information", 'N', 'N');
        notModedList.add(secondGuest);
        thirdGuest = new Guests("Second", "89999999999", 6, 2, Timestamp.valueOf("2022-11-11 12:00:00"),
                Timestamp.valueOf("2022-12-12 12:00:00"), 8, "Some information", 'Y', 'Y');
        modedList.add(thirdGuest);
        blackList.add(thirdGuest);
        fourthGuest = new Guests("Second", "89999999999", 6, 2, Timestamp.valueOf("2022-11-11 12:00:00"),
                Timestamp.valueOf("2022-12-12 12:00:00"), 8, "Some information", 'Y', 'Y');
        modedList.add(fourthGuest);
        blackList.add(fourthGuest);
        amountOfModed = modedList.size();
        amountOfNotModed = notModedList.size();
        for (int i = 0; i < 2; i++) {
            guestsStrings.add("Second,89999999999,6,2,2022-11-11 12:00:00,2022-12-12 12:00:00,8,SomeInformation,Y");
        }
    }

    @Test
    public void getModeratingPageTest() throws Exception {
        given(this.guestsService.findNotModedGuests()).willReturn(notModedList);
        this.mockMvc.perform(get("/moderate"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("notModedGuests", notModedList));
    }

    @Test
    public void getModedGuestsPageTest() throws Exception {
        given(this.guestsService.findModedGuests()).willReturn(modedList);
        this.mockMvc.perform(get("/showModedGuests"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("allModedGuests", modedList));
    }

    @Test
    public void getBlackListTest() throws Exception {
        given(this.guestsService.findBlockedGuests()).willReturn(blackList);
        this.mockMvc.perform(get("/blackList"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("blockedGuests", blackList));
    }

    @Test
    public void findGuestsByNameTest() throws Exception {
        given(this.guestsService.findGuestsByName("Second")).willReturn(modedList);
        this.mockMvc.perform(post("/findByName")
                        .with(csrf())
                        .param("name", "Second"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("searchedGuests", modedList))
                .andExpect(model().attribute("amountG", amountOfModed));
    }

    @Test
    public void findGuestsByPhoneTest() throws Exception {
        given(this.guestsService.findGuestsByPhone("89999999999")).willReturn(modedList);
        this.mockMvc.perform(post("/findByPhone")
                        .with(csrf())
                        .param("phone", "89999999999"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("searchedGuests", modedList))
                .andExpect(model().attribute("amountG", amountOfModed));
    }

    @Test
    public void findGuestsByAmountOfPeoplesTest() throws Exception {
        given(this.guestsService.findGuestsByPeoplesAmount(6)).willReturn(modedList);
        this.mockMvc.perform(post("/findByAmountOfPeoples")
                        .with(csrf())
                        .param("amount", "6"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("searchedGuests", modedList))
                .andExpect(model().attribute("amountG", amountOfModed));
    }

    @Test
    public void findByAmountOfRoomsTest() throws Exception {
        given(this.guestsService.findGuestsByRoomsAmount(1)).willReturn(notModedList);
        this.mockMvc.perform(post("/findByAmountOfRooms")
                        .with(csrf())
                        .param("amount", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("searchedGuests", notModedList))
                .andExpect(model().attribute("amountG", amountOfNotModed));
    }

    @Test
    public void findGuestsByAmountOfNightsTest() throws Exception {
        given(guestsService.findGuestsByNightsAmount(4)).willReturn(notModedList);
        this.mockMvc.perform(post("/findByAmountOfNights")
                        .with(csrf())
                        .param("amount", "4"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("searchedGuests", notModedList))
                .andExpect(model().attribute("amountG", amountOfNotModed));
    }

    @Test
    public void findAllGuestsInIntervalTest() throws Exception {
        given(guestsService.findAllInTimeInterval("2001-01-01T12:00", "2002-02-02T12:00"))
                .willReturn(notModedList);
        this.mockMvc.perform(post("/findAllInInterval")
                        .with(csrf())
                        .param("checkIn", "2001-01-01T12:00")
                        .param("checkOut", "2002-02-02T12:00"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("searchedGuests", notModedList))
                .andExpect(model().attribute("amountG", amountOfNotModed));
    }

    @Test
    public void saveHistoryTest() throws Exception {
        given(guestsService.findModedGuests()).willReturn(modedList);
        given(guestsService.convertEntitiesToStrings(modedList)).willReturn(guestsStrings);
        given(guestsService.writeToFile(guestsStrings)).willReturn("Успех");
        this.mockMvc.perform(post("/saveHistoryInDoc")
                        .with(csrf())
                        .param("type", "history"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("status", "Успех"));
    }
}