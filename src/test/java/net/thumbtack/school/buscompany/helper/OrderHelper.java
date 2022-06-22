package net.thumbtack.school.buscompany.helper;

import lombok.Getter;
import net.thumbtack.school.buscompany.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;

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
}
