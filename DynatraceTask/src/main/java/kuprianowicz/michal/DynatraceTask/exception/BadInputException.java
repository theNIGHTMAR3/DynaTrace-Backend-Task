package kuprianowicz.michal.DynatraceTask.exception;

public class BadInputException extends Exception{

    /**
     * Custom Exception for handling bad input data
     * @param message message to display
     */
    public BadInputException(String message)
    {
        super(message);
    }
}
