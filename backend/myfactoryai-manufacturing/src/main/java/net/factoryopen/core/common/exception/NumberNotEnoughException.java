package net.factoryopen.core.common.exception;

public class NumberNotEnoughException extends FactoryException{

    public NumberNotEnoughException() {
        super();
    }

    public NumberNotEnoughException(String message) {
        super(message);
    }

    public NumberNotEnoughException(String message, Throwable cause) {
        super(message, cause);
    }

    public NumberNotEnoughException(Throwable cause) {
        super(cause);
    }

    protected NumberNotEnoughException(String message, Throwable cause,
                                       boolean enableSuppression,
                                       boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
