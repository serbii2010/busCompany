package net.thumbtack.school.buscompany.exception;

public enum ServerErrorCode {
    USER_NOT_FOUND("USER_NOT_FOUND"),
    USER_NOT_AUTHORIZATION("USER_NOT_AUTHORIZATION"),
    BAD_PASSWORD("BAD_PASSWORD");


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