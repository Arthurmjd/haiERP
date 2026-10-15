package net.factoryopen.core.common.exception;

public class DateUnavailableException extends FactoryException{

    public DateUnavailableException() {
        super();
    }

    public DateUnavailableException(String message) {
        super(message);
    }

    public DateUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public DateUnavailableException(Throwable cause) {
        super(cause);
    }

    protected DateUnavailableException(String message, Throwable cause,
                                       boolean enableSuppression,
                                       boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
