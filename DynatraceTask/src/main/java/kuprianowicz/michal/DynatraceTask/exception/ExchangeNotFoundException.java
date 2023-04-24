package kuprianowicz.michal.DynatraceTask.exception;

public class ExchangeNotFoundException  extends Exception{

    /**
     * Custom Exception for handling NotFound(404) errors
     * @param message message to display
     */
    public ExchangeNotFoundException(String message)
    {
        super(message);
    }

}
