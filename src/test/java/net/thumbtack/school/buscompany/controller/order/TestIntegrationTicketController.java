package net.thumbtack.school.buscompany.controller.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.school.buscompany.controller.GlobalErrorHandler;
import net.thumbtack.school.buscompany.dto.request.order.TicketDtoRequest;
import net.thumbtack.school.buscompany.dto.response.order.TicketDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.helper.*;
import net.thumbtack.school.buscompany.helper.dto.request.order.TicketDtoRequestHelper;
import net.thumbtack.school.buscompany.helper.dto.response.ErrorDtoResponseHelper;
import net.thumbtack.school.buscompany.helper.dto.response.order.TicketDtoResponseHelper;
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
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class TestIntegrationTicketController {
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
    private OrderHelper orderHelper;
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
        orderHelper.generateDefaultOrder(cookieClient, mvc, mapper);
    }

    @Test
    public void getFreePlaces() throws Exception {
        List<Integer> response = TicketDtoResponseHelper.getFreePlaces();

        mvc.perform(get("/api/places/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieClient))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    public void insertTicket() throws Exception {
        TicketDtoRequest request = TicketDtoRequestHelper.getInsert();
        TicketDtoResponse response = TicketDtoResponseHelper.getInsert();

        mvc.perform(post("/api/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieClient)
                .content(mapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));

        List<Integer> responsePlace = TicketDtoResponseHelper.getFreePlaceWithout4();
        mvc.perform(get("/api/places/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieClient))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(responsePlace)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    public void insertTicket_takenPlace() throws Exception {
        TicketDtoRequest request = TicketDtoRequestHelper.getInsert();
        TicketDtoResponse response = TicketDtoResponseHelper.getInsert();

        mvc.perform(post("/api/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieClient)
                .content(mapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));

        GlobalErrorHandler.MyError error = ErrorDtoResponseHelper.getDtoResponseError(ServerErrorCode.PLACE_TAKEN);

        mvc.perform(post("/api/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieClient)
                .content(mapper.writeValueAsBytes(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapper.writeValueAsString(error)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    public void insertTicket_reinsertPlace() throws Exception {
        TicketDtoRequest request = TicketDtoRequestHelper.getInsert();
        List<Integer> response = TicketDtoResponseHelper.getFreePlaceWithout30();

        mvc.perform(post("/api/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieClient)
                .content(mapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));

        TicketDtoRequest request30 = TicketDtoRequestHelper.getInsertPlace30();
        mvc.perform(post("/api/places/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieClient)
                .content(mapper.writeValueAsBytes(request30)))
                .andExpect(status().isOk())
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));

        mvc.perform(get("/api/places/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieClient))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));

    }
}
