package kuprianowicz.michal.DynatraceTask.advice;

import kuprianowicz.michal.DynatraceTask.exception.BadInputException;
import kuprianowicz.michal.DynatraceTask.exception.ExchangeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;



@RestControllerAdvice
public class MyExceptionHandler {

    /**
     * Custom Exception handler for ExchangeNotFoundException
     * @param ex    Custom ExchangeNotFoundException
     * @return      Map<String,String> displaying error message
     *
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ExchangeNotFoundException.class)
    public Map<String,String> handleExchangeException(ExchangeNotFoundException ex)
    {
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("errorMessage",ex.getMessage());
        return errorMap;
    }

    /**
     * Custom Exception handler for BadInputException
     * @param ex    Custom BadInputException
     * @return      Map<String,String> displaying error message
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadInputException.class)
    public Map<String,String> handleQuotationsException(BadInputException ex)
    {
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("errorMessage",ex.getMessage());
        return errorMap;
    }
}
