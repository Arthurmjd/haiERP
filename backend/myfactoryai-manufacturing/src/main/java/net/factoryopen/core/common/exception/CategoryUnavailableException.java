package net.factoryopen.core.common.exception;

public class CategoryUnavailableException extends FactoryException{

    public CategoryUnavailableException() {
        super();
    }

    public CategoryUnavailableException(String message) {
        super(message);
    }

    public CategoryUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryUnavailableException(Throwable cause) {
        super(cause);
    }

    protected CategoryUnavailableException(String message, Throwable cause,
                                           boolean enableSuppression,
                                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
