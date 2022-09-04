package net.thumbtack.school.buscompany.controller.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.school.buscompany.controller.GlobalErrorHandler;
import net.thumbtack.school.buscompany.dto.request.order.TicketDtoRequest;
import net.thumbtack.school.buscompany.dto.response.order.TicketDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.helper.*;
import net.thumbtack.school.buscompany.helper.dto.request.order.TicketDtoRequestHelper;
import net.thumbtack.school.buscompany.helper.dto.response.ErrorDtoResponseHelper;
import net.thumbtack.school.buscompany.helper.dto.response.order.TicketDtoResponseHelper;
import net.thumbtack.school.buscompany.model.Order;
import net.thumbtack.school.buscompany.model.Passenger;
import net.thumbtack.school.buscompany.model.Place;
import net.thumbtack.school.buscompany.model.account.Client;
import net.thumbtack.school.buscompany.service.account.AccountService;
import net.thumbtack.school.buscompany.service.order.OrderService;
import net.thumbtack.school.buscompany.service.order.TicketService;
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

import javax.servlet.http.Cookie;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {TicketController.class, AccountHelper.class, TripHelper.class, OrderHelper.class, TicketHelper.class, DateTripHelper.class})
class TestTicketController {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private AccountHelper accountHelper;
    @Autowired
    private OrderHelper orderHelper;
    @Autowired
    private TicketHelper ticketHelper;

    @MockBean
    private AccountService accountService;
    @MockBean
    private OrderService orderService;
    @MockBean
    private TicketService ticketService;

    private Client client;
    private Cookie cookie;
    private Order order;
    private Passenger passenger;
    private Place place;

    @BeforeEach
    public void init() throws Exception {
        accountHelper.init();
        orderHelper.init();
        ticketHelper.init();
        client = accountHelper.getClient();
        cookie = accountHelper.getCookie();
        order = orderHelper.getOrder();
        passenger = ticketHelper.getPassenger();
        place = ticketHelper.getPlace();
    }

    @Test
    void testGetFreePlaces() throws Exception {
        Mockito.when(ticketService.getFreePlace(cookie.getValue(), "1")).thenReturn(TicketDtoResponseHelper.getFreePlaces());
        List<Integer> response = TicketDtoResponseHelper.getFreePlaces();

        mvc.perform(get("/api/places/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)));
    }

    @Test
    void testGetFreePlaces_badCookie() throws Exception {
        mvc.perform(get("/api/places/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetFreePlaces_badId() throws Exception {
        Mockito.when(ticketService.getFreePlace(cookie.getValue(), "1")).thenThrow(new ServerException(ServerErrorCode.ORDER_NOT_FOUND));
        mvc.perform(get("/api/places/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testInsertTicket() throws Exception {
        TicketDtoRequest request = TicketDtoRequestHelper.getInsert();
        TicketDtoResponse response = TicketDtoResponseHelper.getInsert();
        Mockito.when(ticketService.insertTicket("sessionId", request)).thenReturn(response);

        mvc.perform(post("/api/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(new Cookie("JAVASESSIONID", "sessionId")))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)));
    }

    @Test
    void testInsertTicket_badOrder() throws Exception {
        TicketDtoRequest request = TicketDtoRequestHelper.getInsert();
        GlobalErrorHandler.MyError response = ErrorDtoResponseHelper.getDtoResponseError(ServerErrorCode.ORDER_NOT_FOUND);
        Mockito.when(ticketService.insertTicket(cookie.getValue(), request)).thenThrow(new ServerException(ServerErrorCode.ORDER_NOT_FOUND));

        mvc.perform(post("/api/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapper.writeValueAsString(response)));
    }

    @Test
    void testInsertTicket_badPassenger() throws Exception {
        TicketDtoRequest request = TicketDtoRequestHelper.getInsert();
        GlobalErrorHandler.MyError response = ErrorDtoResponseHelper.getDtoResponseError(ServerErrorCode.PASSENGER_NOT_FOUND);
        Mockito.when(ticketService.insertTicket(cookie.getValue(), request)).thenThrow(new ServerException(ServerErrorCode.PASSENGER_NOT_FOUND));

        mvc.perform(post("/api/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapper.writeValueAsString(response)));
    }

    @Test
    void testInsertTicket_placeNotFound() throws Exception {
        TicketDtoRequest request = TicketDtoRequestHelper.getInsert();
        GlobalErrorHandler.MyError response = ErrorDtoResponseHelper.getDtoResponseError(ServerErrorCode.PLACE_NOT_FOUND);
        Mockito.when(ticketService.insertTicket(cookie.getValue(), request)).thenThrow(new ServerException(ServerErrorCode.PLACE_NOT_FOUND));

        mvc.perform(post("/api/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapper.writeValueAsString(response)));
    }

    @Test
    void testInsertTicket_placeTaken() throws Exception {
        TicketDtoRequest request = TicketDtoRequestHelper.getInsert();
        GlobalErrorHandler.MyError response = ErrorDtoResponseHelper.getDtoResponseError(ServerErrorCode.PLACE_TAKEN);
        Mockito.when(ticketService.insertTicket(cookie.getValue(), request)).thenThrow(new ServerException(ServerErrorCode.PLACE_TAKEN));

        mvc.perform(post("/api/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapper.writeValueAsString(response)));
    }
}