/**
 * Class representing server work exception.
 */
public class UserError extends Exception {

    /**
     * Constructor with specified string
     *
     * @param message string
     */
    public UserError(String message) {
        super(message);
    }

    /**
     * Constructor with specified string and exception
     *
     * @param message string
     * @param e       error covered
     */
    public UserError(String message, Throwable e) {
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

