package net.thumbtack.school.buscompany.controller.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.school.buscompany.dto.response.account.InfoAdministratorDtoResponse;
import net.thumbtack.school.buscompany.dto.response.account.InfoClientDtoResponse;
import net.thumbtack.school.buscompany.helper.AccountHelper;
import net.thumbtack.school.buscompany.helper.dto.response.account.InfoAdminDtoResponseHelper;
import net.thumbtack.school.buscompany.helper.dto.response.account.InfoClientDtoResponseHelper;
import net.thumbtack.school.buscompany.service.DebugService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class TestIntegrationAccountController {
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
    public void getInfo_notAuth() throws Exception {
        mvc.perform(get("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getInfo_admin() throws Exception {
        String javaSessionId = AccountHelper.registrationAdmin(mvc, mapper);
        InfoAdministratorDtoResponse response = InfoAdminDtoResponseHelper.get();

        mvc.perform(get("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(new Cookie("JAVASESSIONID", javaSessionId)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)));
    }

    @Test
    public void getInfo_client() throws Exception {
        String javaSessionId = AccountHelper.registrationClient(mvc, mapper);
        InfoClientDtoResponse response = InfoClientDtoResponseHelper.get();

        mvc.perform(get("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(new Cookie("JAVASESSIONID", javaSessionId)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)));
    }

    @Test
    void deleteAccount() {
    }
}