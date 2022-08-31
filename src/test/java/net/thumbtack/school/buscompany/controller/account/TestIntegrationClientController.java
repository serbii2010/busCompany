package net.thumbtack.school.buscompany.controller.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.school.buscompany.dto.request.account.EditClientDtoRequest;
import net.thumbtack.school.buscompany.dto.request.account.RegistrationClientDtoRequest;
import net.thumbtack.school.buscompany.dto.response.account.EditClientDtoResponse;
import net.thumbtack.school.buscompany.dto.response.account.RegistrationClientDtoResponse;
import net.thumbtack.school.buscompany.helper.AccountHelper;
import net.thumbtack.school.buscompany.helper.dto.request.account.RegistrationClientDtoRequestHelper;
import net.thumbtack.school.buscompany.helper.dto.request.account.UpdateClientDtoRequestHelper;
import net.thumbtack.school.buscompany.helper.dto.response.account.RegistrationClientDtoResponseHelper;
import net.thumbtack.school.buscompany.helper.dto.response.account.UpdateClientDtoResponseHelper;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class TestIntegrationClientController {
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
    public void insertClient() throws Exception {
        RegistrationClientDtoRequest request = RegistrationClientDtoRequestHelper.get();
        RegistrationClientDtoResponse response = RegistrationClientDtoResponseHelper.get();

        mvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)))
                .andExpect(cookie().exists("JAVASESSIONID"));
    }

    @Test
    public void updateClient() throws Exception {
        String javaSessionId = AccountHelper.registrationClient(mvc, mapper);

        EditClientDtoRequest request = UpdateClientDtoRequestHelper.get();
        EditClientDtoResponse response = UpdateClientDtoResponseHelper.get();

        mvc.perform(put("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(new Cookie("JAVASESSIONID", javaSessionId)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)));
    }
}
