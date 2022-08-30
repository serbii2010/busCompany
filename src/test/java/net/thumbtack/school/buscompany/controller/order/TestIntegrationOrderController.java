package net.thumbtack.school.buscompany.controller.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.school.buscompany.dto.request.order.OrderDtoRequest;
import net.thumbtack.school.buscompany.dto.response.account.EmptyDtoResponse;
import net.thumbtack.school.buscompany.dto.response.order.OrderDtoResponse;
import net.thumbtack.school.buscompany.helper.*;
import net.thumbtack.school.buscompany.helper.dto.request.order.OrderDtoRequestHelper;
import net.thumbtack.school.buscompany.helper.dto.response.EmptyResponseHelper;
import net.thumbtack.school.buscompany.helper.dto.response.order.OrderDtoResponseHelper;
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
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    }

    @Disabled
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

    @Disabled
    @Test
    public void filter_byClient() throws Exception {
        orderHelper.generateDefaultOrder(cookieClient, mvc, mapper);
        List<OrderDtoResponse> responses = OrderDtoResponseHelper.getResponseListAll();

        mvc.perform(get("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieClient))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(responses)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Disabled
    @Test
    public void filter_byClientFromStation() throws Exception {
        orderHelper.generateDefaultOrder(cookieClient, mvc, mapper);
        List<OrderDtoResponse> responses = OrderDtoResponseHelper.getResponseListAll();

        mvc.perform(get("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("fromStation", "Omsk")
                .cookie(cookieClient))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(responses)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Disabled
    @Test
    public void filter_byClientToStation() throws Exception {
        orderHelper.generateDefaultOrder(cookieClient, mvc, mapper);
        List<OrderDtoResponse> responses = OrderDtoResponseHelper.getResponseListAll();

        mvc.perform(get("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("toStation", "Новосибирск")
                .cookie(cookieClient))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(responses)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Disabled
    @Test
    public void filter_byClientBusName() throws Exception {
        orderHelper.generateDefaultOrder(cookieClient, mvc, mapper);
        List<OrderDtoResponse> responses = OrderDtoResponseHelper.getResponseListTwo();

        mvc.perform(get("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("busName", "Пазик")
                .cookie(cookieClient))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(responses)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Disabled
    @Test
    public void filter_byClientFromDate() throws Exception {
        orderHelper.generateDefaultOrder(cookieClient, mvc, mapper);
        List<OrderDtoResponse> responses = OrderDtoResponseHelper.getResponseListTwo();

        mvc.perform(get("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("fromDate", "2023-01-10")
                .cookie(cookieClient))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(responses)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Disabled
    @Test
    public void filter_byClientToDate() throws Exception {
        orderHelper.generateDefaultOrder(cookieClient, mvc, mapper);
        List<OrderDtoResponse> responses = OrderDtoResponseHelper.getResponseListFirst();

        mvc.perform(get("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("toDate", "2023-01-10")
                .cookie(cookieClient))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(responses)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Disabled
    @Test
    public void filter_byClientAllFilter() throws Exception {
        orderHelper.generateDefaultOrder(cookieClient, mvc, mapper);
        List<OrderDtoResponse> responses = OrderDtoResponseHelper.getResponseListTwo();

        mvc.perform(get("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("fromStation", "Omsk")
                .param("toStation", "Новосибирск")
                .param("busName", "Пазик")
                .param("fromDate", "2023-01-10")
                .param("toDate", "2023-01-19")
                .cookie(cookieClient))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(responses)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Disabled
    @Test
    public void filter_byClientAllFilterIgnoreClientId() throws Exception {
        orderHelper.generateDefaultOrder(cookieClient, mvc, mapper);
        List<OrderDtoResponse> responses = OrderDtoResponseHelper.getResponseListTwo();

        mvc.perform(get("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("fromStation", "Omsk")
                .param("toStation", "Новосибирск")
                .param("busName", "Пазик")
                .param("fromDate", "2023-01-10")
                .param("toDate", "2023-01-19")
                .param("clientId", "1234")
                .cookie(cookieClient))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(responses)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Disabled
    @Test
    public void filter_byAdminAllFilter() throws Exception {
        orderHelper.generateDefaultOrder(cookieClient, mvc, mapper);
        List<OrderDtoResponse> responses = OrderDtoResponseHelper.getResponseListTwo();

        mvc.perform(get("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("fromStation", "Omsk")
                .param("toStation", "Новосибирск")
                .param("busName", "Пазик")
                .param("fromDate", "2023-01-10")
                .param("toDate", "2023-01-19")
                .param("clientId", "1")
                .cookie(cookieAdmin))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(responses)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Disabled
    @Test
    public void filter_byAdminAllFilterClientNotFound() throws Exception {
        orderHelper.generateDefaultOrder(cookieClient, mvc, mapper);

        mvc.perform(get("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("fromStation", "Omsk")
                .param("toStation", "Новосибирск")
                .param("busName", "Пазик")
                .param("fromDate", "2023-01-10")
                .param("toDate", "2023-01-19")
                .param("clientId", "12")
                .cookie(cookieAdmin))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(new ArrayList<>())))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Disabled
    @Test
    public void deleteOrder() throws Exception {
        orderHelper.generateDefaultOrder(cookieClient, mvc, mapper);
        EmptyDtoResponse response = EmptyResponseHelper.get();

        mvc.perform(delete("/api/orders/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieClient))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));

        List<OrderDtoResponse> responses = OrderDtoResponseHelper.getResponseListFirst();
        mvc.perform(get("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieClient))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(responses)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Disabled
    @Test
    public void deleteOrder_byAdmin() throws Exception {
        orderHelper.generateDefaultOrder(cookieClient, mvc, mapper);

        mvc.perform(delete("/api/orders/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieAdmin))
                .andExpect(status().isBadRequest())
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }
}