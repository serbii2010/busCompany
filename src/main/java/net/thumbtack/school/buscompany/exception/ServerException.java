package net.thumbtack.school.buscompany.exception;

public class ServerException extends Exception{
    private final ServerErrorCode errorCode;

    public ServerException(ServerErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ServerErrorCode getErrorCode() {
        return this.errorCode;
    }
}
