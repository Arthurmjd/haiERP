package net.factoryopen.core.common.exception;

public class QuantityUnavailableException extends FactoryException{

    public QuantityUnavailableException() {
        super();
    }

    public QuantityUnavailableException(String message) {
        super(message);
    }

    public QuantityUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuantityUnavailableException(Throwable cause) {
        super(cause);
    }

    protected QuantityUnavailableException(String message, Throwable cause,
                                           boolean enableSuppression,
                                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
