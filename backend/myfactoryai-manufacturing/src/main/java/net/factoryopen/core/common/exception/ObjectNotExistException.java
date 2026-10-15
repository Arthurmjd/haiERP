package net.factoryopen.core.common.exception;

public class ObjectNotExistException extends FactoryException{

    public ObjectNotExistException() {
        super();
    }

    public ObjectNotExistException(String message) {
        super(message);
    }

    public ObjectNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectNotExistException(Throwable cause) {
        super(cause);
    }

    protected ObjectNotExistException(String message, Throwable cause,
                                      boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
