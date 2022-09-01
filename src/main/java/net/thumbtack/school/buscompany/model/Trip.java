package net.thumbtack.school.buscompany.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Trip {
    private int id;
    private Bus bus;
    private Station fromStation;
    private Station toStation;
    private LocalTime start;
    // REVU нет, здесь int
    // с LocalTime Вы создадите себе проблемы, если, например, продолжительность 25 часов
    // можно взять
    // https://docs.oracle.com/javase/8/docs/api/java/time/Period.html
    // но для него нет хэндлера при записи в БД и придется вручную преобразовывать
    private LocalTime duration;
    private int price;
    private boolean approved;
    private Schedule schedule;
    private List<DateTrip> dates;
}
