package net.factoryopen.core.common.exception;

public class RecordNotLegalException extends FactoryException{

    public RecordNotLegalException() {
        super();
    }

    public RecordNotLegalException(String message) {
        super(message);
    }

    public RecordNotLegalException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecordNotLegalException(Throwable cause) {
        super(cause);
    }

    protected RecordNotLegalException(String message, Throwable cause,
                                      boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
