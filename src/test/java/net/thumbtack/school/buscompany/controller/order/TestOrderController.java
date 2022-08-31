package net.thumbtack.school.buscompany.controller.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.school.buscompany.controller.GlobalErrorHandler;
import net.thumbtack.school.buscompany.dto.request.order.OrderDtoRequest;
import net.thumbtack.school.buscompany.dto.request.order.PassengerDtoRequest;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.helper.AccountHelper;
import net.thumbtack.school.buscompany.helper.DateTripHelper;
import net.thumbtack.school.buscompany.helper.OrderHelper;
import net.thumbtack.school.buscompany.helper.TripHelper;
import net.thumbtack.school.buscompany.model.Order;
import net.thumbtack.school.buscompany.model.Trip;
import net.thumbtack.school.buscompany.model.account.Admin;
import net.thumbtack.school.buscompany.model.account.Client;
import net.thumbtack.school.buscompany.service.account.AccountService;
import net.thumbtack.school.buscompany.service.order.OrderService;
import net.thumbtack.school.buscompany.service.trip.TripService;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {OrderController.class, AccountHelper.class, DateTripHelper.class, OrderHelper.class, TripHelper.class})
class TestOrderController {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private TripHelper tripHelper;
    @Autowired
    private AccountHelper accountHelper;
    @Autowired
    private DateTripHelper dateTripHelper;
    @Autowired
    private OrderHelper orderHelper;

    @MockBean
    private OrderService orderService;
    @MockBean
    private AccountService accountService;
    @MockBean
    private TripService tripService;

    private Admin admin;
    private Client client;
    private Cookie cookie;
    private Trip trip;
    private Order order;

    @BeforeEach
    public void init() throws Exception {
        accountHelper.init();
        tripHelper.init();
        orderHelper.init();
        dateTripHelper.init();
        trip = tripHelper.getTrip();
        order = orderHelper.getOrder();
        admin = accountHelper.getAdmin();
        client = accountHelper.getClient();
        cookie = accountHelper.getCookie();

    }

    @Test
    void testCreateOrder() throws Exception {
        List<PassengerDtoRequest> passengers = new ArrayList<>();
        passengers.add(new PassengerDtoRequest("имя", "фамилия", "номер паспорта"));
        passengers.add(new PassengerDtoRequest("имя2", "фамилия2", "номер паспорта2"));
        OrderDtoRequest request = new OrderDtoRequest(
                1,
                "2021-12-12",
                passengers
        );
        Mockito.when(accountService.getAuthAccount(cookie.getValue())).thenReturn(client);
        Mockito.when(tripService.findById("1")).thenReturn(trip);
        Mockito.when(tripService.findDateTrip("1", "2021-12-12")).thenReturn(dateTripHelper.getDateTrip(trip));
        MvcResult result = mvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());
    }

    @Test
    void testCreateOrder_badByAdmin() throws Exception {
        List<PassengerDtoRequest> passengers = new ArrayList<>();
        passengers.add(new PassengerDtoRequest("имя", "фамилия", "номер паспорта"));
        passengers.add(new PassengerDtoRequest("имя2", "фамилия2", "номер паспорта2"));
        OrderDtoRequest request = new OrderDtoRequest(
                1,
                "2021-12-12",
                passengers
        );
        Mockito.doThrow(new ServerException(ServerErrorCode.ACTION_FORBIDDEN)).when(accountService).checkClient(cookie.getValue());
        Mockito.when(tripService.findById("1")).thenReturn(trip);
        Mockito.when(tripService.findDateTrip("1", "2021-12-12")).thenReturn(dateTripHelper.getDateTrip(trip));
        MvcResult result = mvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        GlobalErrorHandler.MyError errorResponse = mapper.readValue(responseContent, GlobalErrorHandler.MyError.class);
        ServerErrorCode errorCode = ServerErrorCode.valueOf(errorResponse.getErrors().get(0).getErrorCode());
        assertAll(
                "head",
                () -> assertEquals(result.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value()),
                () -> assertEquals(ServerErrorCode.ACTION_FORBIDDEN, errorCode)
        );
    }

    @Test
    void testCreateOrder_badDateTrip() throws Exception {
        List<PassengerDtoRequest> passengers = new ArrayList<>();
        passengers.add(new PassengerDtoRequest("имя", "фамилия", "номер паспорта"));
        passengers.add(new PassengerDtoRequest("имя2", "фамилия2", "номер паспорта2"));
        OrderDtoRequest request = new OrderDtoRequest(
                1,
                "2022-12-12",
                passengers
        );

        Mockito.when(tripService.findById("1")).thenReturn(trip);
        Mockito.when(tripService.findDateTrip("1", "2021-12-12")).thenReturn(dateTripHelper.getDateTrip(trip));
        MvcResult result = mvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        GlobalErrorHandler.MyError errorResponse = mapper.readValue(responseContent, GlobalErrorHandler.MyError.class);
        String errorCode = errorResponse.getErrors().get(0).getErrorCode();
        assertAll(
                "head",
                () -> assertEquals(result.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value()),
                () -> assertEquals("DateOrderInDatesTrip", errorCode)
        );
    }
}