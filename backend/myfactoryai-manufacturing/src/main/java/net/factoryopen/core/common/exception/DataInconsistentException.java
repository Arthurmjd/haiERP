package net.factoryopen.core.common.exception;

public class DataInconsistentException extends FactoryException{

    public DataInconsistentException() {
        super();
    }

    public DataInconsistentException(String message) {
        super(message);
    }

    public DataInconsistentException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataInconsistentException(Throwable cause) {
        super(cause);
    }

    protected DataInconsistentException(String message, Throwable cause,
                                        boolean enableSuppression,
                                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
