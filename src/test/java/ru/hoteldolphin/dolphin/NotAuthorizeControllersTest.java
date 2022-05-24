package ru.hoteldolphin.dolphin;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.hoteldolphin.dolphin.controllers.NotAuthorizeControllers;
import ru.hoteldolphin.dolphin.entities.Guests;
import ru.hoteldolphin.dolphin.services.GuestsService;

import java.sql.Timestamp;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotAuthorizeControllers.class)
@ContextConfiguration(classes = DolphinApplication.class)
@Getter
@Setter
public class NotAuthorizeControllersTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GuestsService guestsService;

    @Test
    public void getMainPageTest() throws Exception {
        this.mockMvc.perform(get("/mainPage")).andExpect(status().isOk());
    }

    @Test
    public void getRoomsPageTest() throws Exception {
        this.mockMvc.perform(get("/rooms")).andExpect(status().isOk());
    }

    @Test
    public void getGalleryPageTest() throws Exception {
        this.mockMvc.perform(get("/gallery")).andExpect(status().isOk());
    }

    @Test
    public void getContactsPageTest() throws Exception {
        this.mockMvc.perform(get("/contacts")).andExpect(status().isOk());
    }

    @Test
    public void getServicesPageTest() throws Exception {
        this.mockMvc.perform(get("/services")).andExpect(status().isOk());
    }

    @Test
    public void getBookPageTest() throws Exception {
        this.mockMvc.perform(get("/bookPage")).andExpect(status().isOk());
    }

    @Test
    public void makeBookTest() throws Exception {
        Guests newGuest = new Guests("First", "88888888888", 2, 1,
                Timestamp.valueOf("2001-01-01 12:00:00"), Timestamp.valueOf("2002-02-02 12:00:00"), 4,
                "Some Information", 'N', 'N');
        this.guestsService.save(newGuest);
        this.mockMvc.perform(post("/makeBook")
                .with(csrf())
                .param("name", "First")
                .param("phone", "88888888888")
                .param("amountOfPeoples", "2")
                .param("amountOfRooms", "1")
                .param("checkIn", "2001-01-01T12:00")
                .param("checkOut", "2002-02-02T12:00")
                .param("amountOfNights", "4")
                .param("info", "Some information")).andExpect(status().isOk());
    }
}