package net.thumbtack.school.buscompany.controller.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.school.buscompany.dto.request.order.OrderDtoRequest;
import net.thumbtack.school.buscompany.dto.response.order.OrderDtoResponse;
import net.thumbtack.school.buscompany.helper.AccountHelper;
import net.thumbtack.school.buscompany.helper.BusHelper;
import net.thumbtack.school.buscompany.helper.StationHelper;
import net.thumbtack.school.buscompany.helper.TripHelper;
import net.thumbtack.school.buscompany.helper.dto.request.order.OrderDtoRequestHelper;
import net.thumbtack.school.buscompany.helper.dto.response.order.OrderDtoResponseHelper;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class TestIntegrationOrderController {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private DebugService debugService;
    @Autowired
    private BusHelper busHelper;
    @Autowired
    private StationHelper stationHelper;
    @Autowired
    private TripHelper tripHelper;
    @Autowired
    private ObjectMapper mapper;

    private Cookie cookieAdmin;
    private Cookie cookieClient;

    @BeforeEach
    public void init() throws Exception {
        debugService.clear();
        busHelper.generateDefaultBuses();
        stationHelper.generateDefaultStation();
        cookieAdmin = new Cookie("JAVASESSIONID", AccountHelper.registrationAdmin("admin", mvc, mapper));
        cookieClient = new Cookie("JAVASESSIONID", AccountHelper.registrationClient(mvc, mapper));
        tripHelper.generateDefaultTrip(cookieAdmin, mvc, mapper);

    }

    @Test
    public void createOrder() throws Exception {
        OrderDtoRequest request = OrderDtoRequestHelper.getDtoInsert();
        OrderDtoResponse response = OrderDtoResponseHelper.getResponse();

        mvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookieClient))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }
}