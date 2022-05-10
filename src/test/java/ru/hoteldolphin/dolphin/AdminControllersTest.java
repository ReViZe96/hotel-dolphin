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

    @BeforeEach
    public void createTestDatas() {
        notModedList = new ArrayList<>();
        modedList = new ArrayList<>();
        firstGuest = (new Guests("First", 2, "88888888888", "01.01.01", "02.02.02", "N"));
        notModedList.add(firstGuest);
        secondGuest = (new Guests("First", 2, "88888888888", "01.01.01", "02.02.02", "N"));
        notModedList.add(secondGuest);
        thirdGuest = (new Guests("Second", 6, "89999999999", "11.11.2022", "12.12.2022", "Y"));
        modedList.add(thirdGuest);
        fourthGuest = (new Guests("Second", 6, "89999999999", "11.11.2022", "12.12.2022", "Y"));
        modedList.add(fourthGuest);
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

    /* Не подходит, т.к. БД изначально пустая --> ни один Guest не будет найден по id.
    Придумать другой тест
    @Test
    public void editNameTest() throws Exception {
        this.guestsService.setGuestsName(1L, "Fifth");
        this.mockMvc.perform(post("/editGuestsName/{id}", "1")
                        .with(csrf())
                        .param("name", "Fifth"))
                .andExpect(status().isOk());
    }  */

    @Test
    public void findGuestsByNameTest() throws Exception {
        given(this.guestsService.findGuestsByName("Second")).willReturn(modedList);
        Integer amount = modedList.size();
        this.mockMvc.perform(post("/findByName")
                        .with(csrf())
                        .param("name", "Second"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("searchedGuests", modedList))
                .andExpect(model().attribute("amountG", amount));
    }

    @Test
    public void findGuestsByPhoneTest() throws Exception {
        given(this.guestsService.findGuestsByPhone("89999999999")).willReturn(modedList);
        Integer amount = modedList.size();
        this.mockMvc.perform(post("/findByPhone")
                        .with(csrf())
                        .param("phone", "89999999999"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("searchedGuests", modedList))
                .andExpect(model().attribute("amountG", amount));
    }

    @Test
    public void findGuestsByAmountTest() throws Exception {
        given(this.guestsService.findGuestsByAmount(6)).willReturn(modedList);
        Integer amount = modedList.size();
        this.mockMvc.perform(post("/findByAmount")
                        .with(csrf())
                        .param("amount", "6"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("searchedGuests", modedList))
                .andExpect(model().attribute("amountG", amount));
    }

    @Test
    public void findGuestsByCheckInTest() throws Exception {
        given(this.guestsService.findGuestsByCheckIn("11.11.2022")).willReturn(modedList);
        Integer amount = modedList.size();
        this.mockMvc.perform(post("/findByCheckIn")
                        .with(csrf())
                        .param("checkIn", "11.11.2022"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("searchedGuests", modedList))
                .andExpect(model().attribute("amountG", amount));
    }

    @Test
    public void findGuestsByCheckOutTest() throws Exception {
        given(this.guestsService.findGuestsByCheckOut("12.12.2022")).willReturn(modedList);
        Integer amount = modedList.size();
        this.mockMvc.perform(post("/findByCheckOut")
                        .with(csrf())
                        .param("checkOut", "12.12.2022"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("searchedGuests", modedList))
                .andExpect(model().attribute("amountG", amount));
    }
}