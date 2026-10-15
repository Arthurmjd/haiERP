package net.factoryopen.core.common.exception;

public class DataNotExistException extends FactoryException{

    public DataNotExistException() {
        super();
    }

    public DataNotExistException(String message) {
        super(message);
    }

    public DataNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataNotExistException(Throwable cause) {
        super(cause);
    }

    protected DataNotExistException(String message, Throwable cause,
                                    boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
