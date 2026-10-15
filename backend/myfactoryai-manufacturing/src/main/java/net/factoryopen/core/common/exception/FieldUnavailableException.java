package net.factoryopen.core.common.exception;

public class FieldUnavailableException extends FactoryException{

    public FieldUnavailableException() {
        super();
    }

    public FieldUnavailableException(String message) {
        super(message);
    }

    public FieldUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public FieldUnavailableException(Throwable cause) {
        super(cause);
    }

    protected FieldUnavailableException(String message, Throwable cause,
                                        boolean enableSuppression,
                                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
