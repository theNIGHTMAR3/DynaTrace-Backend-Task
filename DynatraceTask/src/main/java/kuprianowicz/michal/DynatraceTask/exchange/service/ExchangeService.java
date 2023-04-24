package kuprianowicz.michal.DynatraceTask.exchange.service;

import kuprianowicz.michal.DynatraceTask.exception.BadInputException;
import kuprianowicz.michal.DynatraceTask.exception.ExchangeNotFoundException;
import kuprianowicz.michal.DynatraceTask.exchange.model.Exchange;
import kuprianowicz.michal.DynatraceTask.exchange.model.Rate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


@Service
public class ExchangeService {


    /**
     * Finds exchange from api.nbp.pl with given code and effective date of the exchange rate. Rate contains average exchange value.
     * @param code          currency code
     * @param effectiveDate date of the exchange rate formatted YYYY-MM-DD
     * @return              float rate value of found exchange
     * @throws ExchangeNotFoundException    throws exception when exchange wasn't found
     * @throws BadInputException            throws exception when date is incorrect
     */
    public float getExchangeByCodeAndDate(String code, String effectiveDate) throws ExchangeNotFoundException, BadInputException {
        RestTemplate restTemplate = new RestTemplate();

        try{
            // parse date to its correctness
            LocalDate providedDate = LocalDate.parse(effectiveDate);
            LocalDate today = LocalDate.now();
            LocalDate firstExchange = LocalDate.parse("2002-01-02");

            // check if date is in the future
            if(providedDate.isAfter(today))
                throw new BadInputException("");
            // check if date is before 2002-01-02
            if(providedDate.isBefore(firstExchange))
                throw new BadInputException("");
        }
        catch(Exception e)
        {
            // throw 500 when date is incorrect
            throw new BadInputException("Provided date is incorrect");
        }

        // url to correct api.nbp.pl request
        String url = String.format("https://api.nbp.pl/api/exchangerates/rates/a/%s/%s/?format=json",code,effectiveDate);


        try{
            // return parsed request to Exchange
            Exchange result = restTemplate.getForObject(url, Exchange.class);
            return result.getRates().get(0).getMid().orElseThrow();
        }
        catch(Exception e)
        {
            // throw 404 when exchange wasn't found
            throw new ExchangeNotFoundException("Exchange not found with code: " + code+" and at date: "+effectiveDate);
        }
    }


    /**
     * Finds exchange from api.nbp.pl with given code and with as many rates as number of provided quotations. Rates contain average exchange value.
     * @param code          currency code
     * @param quotations    number of last quotations N (N <= 255)
     * @return              exchange with N rates from api.nbp.pl
     * @throws BadInputException   throws when provided amount of last quotations is incorrect
     * @throws ExchangeNotFoundException        throws exception when exchange wasn't found
     */
    public Exchange getExchangeByCodeWithQuotations(String code, int quotations) throws BadInputException, ExchangeNotFoundException {

        // check if number of quotations is correct
        if(quotations <=0 || quotations>255)
            throw new BadInputException("Incorrect amount of quotations provided");

        RestTemplate restTemplate = new RestTemplate();

        // url to correct api.nbp.pl request
        String url = String.format("http://api.nbp.pl/api/exchangerates/rates/a/%s/last/%d/?format=json",code,quotations);

        try{
            // return parsed request to Exchange
            return restTemplate.getForObject(url, Exchange.class);
        }
        catch(Exception e)
        {
            // throw 404 when exchange wasn't found
            throw new ExchangeNotFoundException("Exchange not found with code: " + code);
        }

    }

    /**
     * Finds exchange from api.nbp.pl with given code and with as many rates as number of provided quotations. Rates contain bid and ask values.
     * @param code          currency code
     * @param quotations    number of last quotations N (N <= 255)
     * @return              exchange with N rates from api.nbp.pl
     * @throws BadInputException            throws when provided input is invalid
     * @throws ExchangeNotFoundException    throws exception when exchange wasn't found
     */
    public Exchange getBidAskExchangeByCodeWithQuotations(String code, int quotations) throws BadInputException, ExchangeNotFoundException {
        // check if number of quotations is correct
        if(quotations <=0 || quotations>255)
            throw new BadInputException("Incorrect amount of quotations provided");

        RestTemplate restTemplate = new RestTemplate();

        // url to correct api.nbp.pl request
        String url = String.format("http://api.nbp.pl/api/exchangerates/rates/c/%s/last/%d/?format=json",code,quotations);

        try{
            // return parsed request to Exchange
            return restTemplate.getForObject(url, Exchange.class);
        }
        catch(Exception e)
        {
            // throw 404 when exchange wasn't found
            throw new ExchangeNotFoundException("Exchange not found with code: " + code);
        }

    }


    /**
     * Finds maximum and minimum values of rates from given Exchange.
     * @param exchange  provided exchange with rates
     * @return          returns map with minimum and maximum average values
     */
    public Map<String, Float> findMinMaxRate(Exchange exchange)
    {
        float min = Float.POSITIVE_INFINITY;
        float max = 0.0f;

        for (Rate rate : exchange.getRates())
        {
            float mid = rate.getMid().orElseThrow();
            if(mid<min)
            {
                min = mid;
            }
            if(mid>max)
            {
                max = mid;
            }
        }

        Map<String, Float> result = new HashMap<>();

        result.put("minimum average value",min);
        result.put("maximum average value",max);

        return result;
    }


    /**
     * Finds Rate with the biggest difference between bid and ask values from given Exchange.
     * @param exchange  provided exchange with rates
     * @return          returns float as the biggest difference between bid and ask values
     */
    public float findMajorDifference(Exchange exchange)
    {
        float difference = 0.0f;

        for (Rate rate : exchange.getRates())
        {
            float bid = rate.getBid().orElseThrow();
            float ask = rate.getAsk().orElseThrow();
            float diffTemp = Math.abs(bid-ask);

            if(diffTemp>difference)
            {
                difference=diffTemp;
            }
        }

        return difference;
    }
}
