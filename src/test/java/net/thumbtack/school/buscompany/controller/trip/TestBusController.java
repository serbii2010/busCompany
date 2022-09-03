package net.thumbtack.school.buscompany.controller.trip;

import net.thumbtack.school.buscompany.helper.AccountHelper;
import net.thumbtack.school.buscompany.helper.DateTripHelper;
import net.thumbtack.school.buscompany.helper.TripHelper;
import net.thumbtack.school.buscompany.model.Bus;
import net.thumbtack.school.buscompany.service.account.AccountService;
import net.thumbtack.school.buscompany.service.trip.BusService;
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
import java.text.ParseException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {BusController.class, AccountHelper.class, TripHelper.class, DateTripHelper.class})
class TestBusController {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private AccountHelper accountHelper;
    @Autowired
    private TripHelper tripHelper;

    @MockBean
    private BusService busService;
    @MockBean
    private AccountService accountService;

    private Cookie cookie;
    private Bus bus;

    @BeforeEach
    public void init() throws ParseException {
        accountHelper.init();
        tripHelper.init();
        cookie = accountHelper.getCookie();
        bus = tripHelper.getBus();
    }

    @Test
    void testGetBuses() throws Exception {
        mvc.perform(get("/api/buses")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andExpect(status().isOk());

        Mockito.verify(busService).getBuses(cookie.getValue());
    }

    @Test
    void testGetBuses_badCookie() throws Exception {
        mvc.perform(get("/api/buses")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}