package net.thumbtack.school.buscompany.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.school.buscompany.dto.response.SettingsDtoResponse;
import net.thumbtack.school.buscompany.helper.AccountHelper;
import net.thumbtack.school.buscompany.helper.SettingsHelper;
import net.thumbtack.school.buscompany.service.account.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.Cookie;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {SettingsController.class, AccountHelper.class, SettingsHelper.class})
class TestSettingsController {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private AccountHelper accountHelper;
    @Autowired
    private SettingsHelper settingsHelper;
    @MockBean
    private AccountService accountService;

    private SettingsDtoResponse response;
    private Cookie cookie;

    @BeforeEach
    public void init() {
        accountHelper.init();
        cookie = accountHelper.getCookie();
        settingsHelper.init();
        response = settingsHelper.getSettingsDtoResponse();
    }

    @Test
    public void testGetSettings_admin() throws Exception {
        Mockito.when(accountService.isAdmin(cookie.getValue())).thenReturn(true);
        MvcResult result = mvc.perform(get("/api/settings")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andReturn();
        assertAll(
                "assert",
                () -> assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus()),
                () -> assertEquals(result.getResponse().getContentAsString(), mapper.writeValueAsString(response))
        );
    }

    @Test
    public void testGetSettings_user() throws Exception {
        Mockito.when(accountService.isAdmin(cookie.getValue())).thenReturn(false);
        Mockito.when(accountService.isClient(cookie.getValue())).thenReturn(true);
        MvcResult result = mvc.perform(get("/api/settings")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andReturn();
        assertAll(
                "assert",
                () -> assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus()),
                () -> assertEquals(result.getResponse().getContentAsString(), mapper.writeValueAsString(response))
        );
    }

    @Test
    public void testGetSettings_notAuth() throws Exception {
        Mockito.when(accountService.isAdmin(cookie.getValue())).thenReturn(false);
        Mockito.when(accountService.isClient(cookie.getValue())).thenReturn(false);
        MvcResult result = mvc.perform(get("/api/settings")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        assertAll(
                "assert",
                () -> assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus()),
                () -> assertEquals(result.getResponse().getContentAsString(), mapper.writeValueAsString(response))
        );
    }

    @Test
    public void testGetSettings_badCookie() throws Exception {
        Mockito.when(accountService.isAdmin(cookie.getValue())).thenReturn(false);
        Mockito.when(accountService.isClient(cookie.getValue())).thenReturn(false);
        MvcResult result = mvc.perform(get("/api/settings")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andReturn();
        assertAll(
                "assert",
                () -> assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus()),
                () -> assertEquals(result.getResponse().getContentAsString(), mapper.writeValueAsString(response))
        );
    }
}