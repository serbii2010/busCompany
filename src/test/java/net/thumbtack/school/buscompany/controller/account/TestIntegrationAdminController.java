package net.thumbtack.school.buscompany.controller.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.school.buscompany.dto.request.account.EditAdministratorDtoRequest;
import net.thumbtack.school.buscompany.dto.request.account.RegistrationAdminDtoRequest;
import net.thumbtack.school.buscompany.dto.response.account.EditAdministratorDtoResponse;
import net.thumbtack.school.buscompany.dto.response.account.RegistrationAdminDtoResponse;
import net.thumbtack.school.buscompany.helper.AccountHelper;
import net.thumbtack.school.buscompany.helper.dto.request.account.RegistrationAdminDtoRequestHelper;
import net.thumbtack.school.buscompany.helper.dto.request.account.UpdateAdminDtoRequestHelper;
import net.thumbtack.school.buscompany.helper.dto.response.account.RegistrationAdminDtoResponseHelper;
import net.thumbtack.school.buscompany.helper.dto.response.account.UpdateAdminDtoResponseHelper;
import net.thumbtack.school.buscompany.service.DebugService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class TestIntegrationAdminController {
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
    public void insertAdmin() throws Exception {
        RegistrationAdminDtoRequest request = RegistrationAdminDtoRequestHelper.get("admin");
        RegistrationAdminDtoResponse response = RegistrationAdminDtoResponseHelper.get();

        mvc.perform(post("/api/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)))
                .andExpect(cookie().exists("JAVASESSIONID"));

    }

    @Disabled
    @Test
    public void updateAdmin() throws Exception {
        String javaSessionId = AccountHelper.registrationAdmin("admin", mvc, mapper);

        EditAdministratorDtoRequest request = UpdateAdminDtoRequestHelper.get();
        EditAdministratorDtoResponse response = UpdateAdminDtoResponseHelper.get();
        mvc.perform(put("/api/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(new Cookie("JAVASESSIONID", javaSessionId)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)));
    }

}