package kuprianowicz.michal.DynatraceTask.advice;

import kuprianowicz.michal.DynatraceTask.exception.BadQuotationsProvidedException;
import kuprianowicz.michal.DynatraceTask.exception.ExchangeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;



@RestControllerAdvice
public class MyExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ExchangeNotFoundException.class)
    public Map<String,String> handleExchangeException(ExchangeNotFoundException ex)
    {
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("errorMessage",ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BadQuotationsProvidedException.class)
    public Map<String,String> handleQuotationsException(BadQuotationsProvidedException ex)
    {
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("errorMessage",ex.getMessage());
        return errorMap;
    }

    /*@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpClientErrorException.class)
    public Map<String, String> handleInvalidArgument(HttpClientErrorException ex)
    {
        Map<String,String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(),error.getDefaultMessage());

        });
        return errorMap;
    }*/


}
