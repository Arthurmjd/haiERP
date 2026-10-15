package net.factoryopen.core.common.exception;

public class BomUnavailableException extends FactoryException{

    public BomUnavailableException() {
        super();
    }

    public BomUnavailableException(String message) {
        super(message);
    }

    public BomUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public BomUnavailableException(Throwable cause) {
        super(cause);
    }

    protected BomUnavailableException(String message, Throwable cause,
                                      boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
