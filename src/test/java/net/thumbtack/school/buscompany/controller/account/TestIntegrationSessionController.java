package net.thumbtack.school.buscompany.controller.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.school.buscompany.dto.request.account.LoginDtoRequest;
import net.thumbtack.school.buscompany.helper.AccountHelper;
import net.thumbtack.school.buscompany.helper.dto.request.account.LoginAdminDtoRequestHelper;
import net.thumbtack.school.buscompany.helper.dto.request.account.LoginClientDtoRequestHelper;
import net.thumbtack.school.buscompany.service.DebugService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class TestIntegrationSessionController {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private DebugService debugService;
    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void init() throws Exception {
        debugService.clear();
    }

    @Test
    void login_admin() throws Exception {
        AccountHelper.registrationAdmin("admin", mvc, mapper);
        LoginDtoRequest request = LoginAdminDtoRequestHelper.get();

        mvc.perform(post("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("JAVASESSIONID"));
    }

    @Test
    void login_adminNotFound() throws Exception {
        LoginDtoRequest request = LoginAdminDtoRequestHelper.get();

        mvc.perform(post("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    void login_client() throws Exception {
        AccountHelper.registrationClient(mvc, mapper);
        LoginDtoRequest request = LoginClientDtoRequestHelper.get();

        mvc.perform(post("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("JAVASESSIONID"));
    }

    @Test
    void login_NotFound() throws Exception {
        LoginDtoRequest request = LoginClientDtoRequestHelper.get();

        mvc.perform(post("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    void logout_client() throws Exception {
        AccountHelper.registrationClient(mvc, mapper);
        String javaSessionId = AccountHelper.loginClient(mvc, mapper);

        mvc.perform(delete("/api/session/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(new Cookie("JAVASESSIONID", javaSessionId)))
                .andExpect(status().isOk())
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    void logout_admin() throws Exception {
        AccountHelper.registrationAdmin("admin", mvc, mapper);
        String javaSessionId = AccountHelper.loginAdmin(mvc, mapper);

        mvc.perform(delete("/api/session/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(new Cookie("JAVASESSIONID", javaSessionId)))
                .andExpect(status().isOk())
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }
}