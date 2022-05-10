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
        Guests newGuest = new Guests("First", 2, "88888888888", "01.01.01", "02.02.02", "N");
        this.guestsService.save(newGuest);
        this.mockMvc.perform(post("/makeBook")
                .with(csrf())
                .param("name", "First")
                .param("amount", "2")
                .param("phone", "88888888888")
                .param("checkIn", "01.01.01")
                .param("checkOut", "02.02.02")).andExpect(status().isOk());
    }
}