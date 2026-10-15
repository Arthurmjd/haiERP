package net.factoryopen.core.common.exception;

public class FormatterConvertException extends FactoryException{

    public FormatterConvertException() {
        super();
    }

    public FormatterConvertException(String message) {
        super(message);
    }

    public FormatterConvertException(String message, Throwable cause) {
        super(message, cause);
    }

    public FormatterConvertException(Throwable cause) {
        super(cause);
    }

    protected FormatterConvertException(String message, Throwable cause,
                                        boolean enableSuppression,
                                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
