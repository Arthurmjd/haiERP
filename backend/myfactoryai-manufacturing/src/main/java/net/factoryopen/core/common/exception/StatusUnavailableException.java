package net.factoryopen.core.common.exception;

public class StatusUnavailableException extends FactoryException{

    public StatusUnavailableException() {
        super();
    }

    public StatusUnavailableException(String message) {
        super(message);
    }

    public StatusUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public StatusUnavailableException(Throwable cause) {
        super(cause);
    }

    protected StatusUnavailableException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
