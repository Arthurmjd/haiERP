package net.factoryopen.core.common.exception;

public class CustomerUnavailableException extends FactoryException{

    public CustomerUnavailableException() {
        super();
    }

    public CustomerUnavailableException(String message) {
        super(message);
    }

    public CustomerUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerUnavailableException(Throwable cause) {
        super(cause);
    }

    protected CustomerUnavailableException(String message, Throwable cause,
                                           boolean enableSuppression,
                                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
