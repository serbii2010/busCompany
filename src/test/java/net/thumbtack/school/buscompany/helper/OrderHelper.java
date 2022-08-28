package net.thumbtack.school.buscompany.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import net.thumbtack.school.buscompany.dto.request.order.OrderDtoRequest;
import net.thumbtack.school.buscompany.helper.dto.request.order.OrderDtoRequestHelper;
import net.thumbtack.school.buscompany.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Getter
@Component
public class OrderHelper {
    @Autowired
    private TripHelper tripHelper;
    @Autowired
    private AccountHelper accountHelper;
    @Autowired
    private DateTripHelper dateTripHelper;

    private Order order;

    public void init() {
        dateTripHelper.init();
        tripHelper.init();
        order = new Order(1, dateTripHelper.getDateTrip(tripHelper.getTrip()), accountHelper.getClient(), null);
    }

    public void generateDefaultOrder(Cookie cookieClient, MockMvc mvc, ObjectMapper mapper) throws Exception {
        OrderDtoRequest request = OrderDtoRequestHelper.getDtoInsert();

        mvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookieClient));
    }
}
