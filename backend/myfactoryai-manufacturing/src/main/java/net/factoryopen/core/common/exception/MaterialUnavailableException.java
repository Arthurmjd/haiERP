package net.factoryopen.core.common.exception;

public class MaterialUnavailableException extends FactoryException{

    public MaterialUnavailableException() {
        super();
    }

    public MaterialUnavailableException(String message) {
        super(message);
    }

    public MaterialUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public MaterialUnavailableException(Throwable cause) {
        super(cause);
    }

    protected MaterialUnavailableException(String message, Throwable cause,
                                           boolean enableSuppression,
                                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
