package net.thumbtack.school.buscompany.helper;

import lombok.Getter;
import lombok.Setter;
import net.thumbtack.school.buscompany.model.Passenger;
import net.thumbtack.school.buscompany.model.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Component
public class TicketHelper {
    @Autowired
    private TripHelper tripHelper;
    @Autowired
    private DateTripHelper dateTripHelper;

    private Passenger passenger;
    private Place place;
    private Place freePlace;

    public static List<Integer> getFreePlaces() {
        return new ArrayList<>(Arrays.asList(
                1,
                2,
                3,
                4,
                5,
                6,
                7,
                8,
                9,
                10,
                11,
                12,
                13,
                14,
                15,
                16,
                17,
                18,
                19,
                20,
                21,
                22,
                23,
                24,
                25,
                26,
                27,
                28,
                29,
                30,
                31,
                32,
                33,
                34,
                35,
                36,
                37,
                38,
                39,
                40
        ));
    }

    public void init() throws ParseException {
        passenger = new Passenger(1, "имя", "фамилия", "passport");
        place = new Place(1, 2, passenger, dateTripHelper.getDateTrip(tripHelper.getTrip()));
        freePlace = new Place(1, 2, null, dateTripHelper.getDateTrip(tripHelper.getTrip()));
    }
}
