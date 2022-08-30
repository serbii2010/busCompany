package net.thumbtack.school.buscompany.controller.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.school.buscompany.dto.request.order.TicketDtoRequest;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.helper.*;
import net.thumbtack.school.buscompany.model.Order;
import net.thumbtack.school.buscompany.model.Passenger;
import net.thumbtack.school.buscompany.model.Place;
import net.thumbtack.school.buscompany.model.account.Client;
import net.thumbtack.school.buscompany.service.account.AccountService;
import net.thumbtack.school.buscompany.service.order.OrderService;
import net.thumbtack.school.buscompany.service.order.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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

    @Disabled
    @Test
    void testGetFreePlaces() throws Exception {
        Mockito.when(accountService.getAuthAccount(cookie.getValue())).thenReturn(client);
        Mockito.when(orderService.findById("1")).thenReturn(order);
        Mockito.when(orderService.getFreePlaces(order)).thenReturn(Arrays.asList(1,2,3));
        MvcResult result = mvc.perform(get("/api/places/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals("[1,2,3]", result.getResponse().getContentAsString());
    }

    @Test
    void testGetFreePlaces_badCookie() throws Exception {
        Mockito.when(accountService.getAuthAccount(cookie.getValue())).thenReturn(client);
        Mockito.when(orderService.findById("1")).thenReturn(order);
        Mockito.when(orderService.getFreePlaces(order)).thenReturn(Arrays.asList(1,2,3));
        MvcResult result = mvc.perform(get("/api/places/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Disabled
    @Test
    void testGetFreePlaces_badId() throws Exception {
        Mockito.when(accountService.getAuthAccount(cookie.getValue())).thenReturn(client);
        Mockito.when(orderService.findById("1")).thenThrow(new ServerException(ServerErrorCode.ORDER_NOT_FOUND));
        Mockito.when(orderService.getFreePlaces(order)).thenReturn(Arrays.asList(1,2,3));
        MvcResult result = mvc.perform(get("/api/places/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void testGetFreePlaces_byAdmin() throws Exception {
        Mockito.when(accountService.getAuthAccount(cookie.getValue())).thenReturn(client);
        Mockito.doThrow(new ServerException(ServerErrorCode.ACTION_FORBIDDEN)).when(accountService).checkClient(cookie.getValue());
        Mockito.when(orderService.findById("1")).thenReturn(order);
        Mockito.when(orderService.getFreePlaces(order)).thenReturn(Arrays.asList(1,2,3));
        MvcResult result = mvc.perform(get("/api/places/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void testInsertTicket() throws Exception {
        TicketDtoRequest request = new TicketDtoRequest("1", "имя", "фамилия", "passport", "2");
        Mockito.when(ticketService.getPassenger(request.getFirstName(), request.getLastName(), request.getPassport())).thenReturn(passenger);
        Mockito.when(orderService.findById("1")).thenReturn(order);
        Mockito.when(ticketService.findPlace(2, order.getDateTrip())).thenReturn(place);

        MvcResult result = mvc.perform(post("/api/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Disabled
    @Test
    void testInsertTicket_badOrder() throws Exception {
        TicketDtoRequest request = new TicketDtoRequest("1", "имя", "фамилия", "passport", "2");
        Mockito.when(ticketService.getPassenger(request.getFirstName(), request.getLastName(), request.getPassport())).thenReturn(passenger);
        Mockito.when(orderService.findById("1")).thenThrow(new ServerException(ServerErrorCode.ORDER_NOT_FOUND));
        Mockito.when(ticketService.findPlace(2, order.getDateTrip())).thenReturn(place);

        MvcResult result = mvc.perform(post("/api/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Disabled
    @Test
    void testInsertTicket_badPassenger() throws Exception {
        TicketDtoRequest request = new TicketDtoRequest("1", "имя", "фамилия", "passport", "2");
        Mockito.when(ticketService.getPassenger(request.getFirstName(), request.getLastName(), request.getPassport()))
                .thenThrow(new ServerException(ServerErrorCode.PASSENGER_NOT_FOUND));
        Mockito.when(orderService.findById("1")).thenReturn(order);
        Mockito.when(ticketService.findPlace(2, order.getDateTrip())).thenReturn(place);

        MvcResult result = mvc.perform(post("/api/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Disabled
    @Test
    void testInsertTicket_placeNotFound() throws Exception {
        TicketDtoRequest request = new TicketDtoRequest("1", "имя", "фамилия", "passport", "2");
        Mockito.when(ticketService.getPassenger(request.getFirstName(), request.getLastName(), request.getPassport())).thenReturn(passenger);
        Mockito.when(orderService.findById("1")).thenReturn(order);
        Mockito.when(ticketService.findPlace(2, order.getDateTrip())).thenThrow(new ServerException(ServerErrorCode.PLACE_NOT_FOUND));

        MvcResult result = mvc.perform(post("/api/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Disabled
    @Test
    void testInsertTicket_placeTaken() throws Exception {
        TicketDtoRequest request = new TicketDtoRequest("1", "имя", "фамилия", "passport", "2");
        Mockito.when(ticketService.getPassenger(request.getFirstName(), request.getLastName(), request.getPassport())).thenReturn(passenger);
        Mockito.when(orderService.findById("1")).thenReturn(order);
        Mockito.when(ticketService.findPlace(2, order.getDateTrip())).thenThrow(new ServerException(ServerErrorCode.PLACE_TAKEN));

        MvcResult result = mvc.perform(post("/api/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }
}