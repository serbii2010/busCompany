package net.thumbtack.school.buscompany.controller.trip;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.school.buscompany.helper.AccountHelper;
import net.thumbtack.school.buscompany.helper.BusHelper;
import net.thumbtack.school.buscompany.helper.dto.response.trip.BusesDtoResponseHelper;
import net.thumbtack.school.buscompany.model.Bus;
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
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class TestIntegrationBusController {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private DebugService debugService;
    @Autowired
    private BusHelper busHelper;

    @Autowired
    private ObjectMapper mapper;

    private String javaSessionId;

    @BeforeEach
    public void init() throws Exception {
        debugService.clear();
        javaSessionId = AccountHelper.registrationAdmin("admin", mvc, mapper);
    }

    @Test
    public void getBuses_empty() throws Exception {
        mvc.perform(get("/api/buses")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(new Cookie("JAVASESSIONID", javaSessionId)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(new ArrayList<Bus>())));
    }

    @Test
    public void getBuses() throws Exception {
        busHelper.generateDefaultBuses();
        mvc.perform(get("/api/buses")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(new Cookie("JAVASESSIONID", javaSessionId)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(BusesDtoResponseHelper.get())));
    }
}