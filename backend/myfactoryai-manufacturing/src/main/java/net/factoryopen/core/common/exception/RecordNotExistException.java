package net.factoryopen.core.common.exception;

public class RecordNotExistException extends FactoryException{

    public RecordNotExistException() {
        super();
    }

    public RecordNotExistException(String message) {
        super(message);
    }

    public RecordNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecordNotExistException(Throwable cause) {
        super(cause);
    }

    protected RecordNotExistException(String message, Throwable cause,
                                      boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
