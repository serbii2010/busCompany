package net.thumbtack.school.buscompany.controller;

import net.thumbtack.school.buscompany.helper.AccountHelper;
import net.thumbtack.school.buscompany.helper.SettingsHelper;
import net.thumbtack.school.buscompany.service.SettingsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {SettingsController.class, AccountHelper.class, SettingsHelper.class})
class TestSettingsController {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private AccountHelper accountHelper;
    @MockBean
    private SettingsService settingsService;

    @BeforeEach
    public void init() {
        accountHelper.init();
    }

    @Test
    public void testGetSettings_admin() throws Exception {
        mvc.perform(get("/api/settings")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(accountHelper.getCookie()))
                .andExpect(status().isOk());

        Mockito.verify(settingsService).getSettings(accountHelper.getCookie().getValue());
    }
}