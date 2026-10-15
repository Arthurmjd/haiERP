package net.factoryopen.core.common.exception;

public class ObjectInUsingException extends FactoryException{

    public ObjectInUsingException() {
        super();
    }

    public ObjectInUsingException(String message) {
        super(message);
    }

    public ObjectInUsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectInUsingException(Throwable cause) {
        super(cause);
    }

    protected ObjectInUsingException(String message, Throwable cause,
                                     boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
