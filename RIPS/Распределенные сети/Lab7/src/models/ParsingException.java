package models;

/**
 * Exception class for parsing errors.
 */
public class ParsingException extends Exception {

    public ParsingException(String message) {
        super(message);
    }

    public ParsingException(String message, Throwable e) {
        super(message, e);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }

}
