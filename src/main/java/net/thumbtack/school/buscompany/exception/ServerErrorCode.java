package net.thumbtack.school.buscompany.exception;

public enum ServerErrorCode {
    USER_NOT_FOUND("USER_NOT_FOUND"),
    USER_NOT_AUTHORIZATION("USER_NOT_AUTHORIZATION"),
    ACTION_FORBIDDEN("ACTION_FORBIDDEN"),
    BAD_FIELD_COOKIE("BAD_FIELD_COOKIE"),
    BAD_PASSWORD("BAD_PASSWORD"),
    STATION_NOT_FOUND("STATION_NOT_FOUND"),
    BUS_NOT_FOUND("BUS_NOT_FOUND"),
    SCHEDULE_NOT_FOUND("SCHEDULE_NOT_FOUND"),
    TRIP_NOT_FOUND("TRIP_NOT_FOUND"),
    ORDER_NOT_FOUND("ORDER_NOT_FOUND"),
    PASSENGER_NOT_FOUND("PASSENGER_NOT_FOUND"),
    PASSENGER_IN_ORDER_NOT_FOUND("PASSENGER_IN_ORDER_NOT_FOUND"),
    DATE_TRIP_NOT_FOUND("DATE_TRIP_NOT_FOUND"),
    PLACE_NOT_FOUND("PLACE_NOT_FOUND"),
    PLACE_TAKEN("PLACE_TAKEN"),
    DATABASE_ERROR("DATABASE_ERROR"),
    USER_TYPE_ERROR("USER_TYPE_ERROR"),
    LOGIN_NOT_UNIQUE("UniqueLogin"),
    INTERNAL_ERROR("INTERNAL_ERROR"),
    DATES_TRIP_DUPLICATED("DATES_TRIP_DUPLICATED");


    private String error;

    ServerErrorCode(String errorString) {
        this.error = errorString;
    }

    public String getErrorString() {
        return error;
    }

    public void setErrorString(String errorString) {
        this.error = errorString;
    }
}