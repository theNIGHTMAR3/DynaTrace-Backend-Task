package kuprianowicz.michal.DynatraceTask.exchange;

import kuprianowicz.michal.DynatraceTask.exception.BadQuotationsProvidedException;
import kuprianowicz.michal.DynatraceTask.exception.ExchangeNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;


@Service
public class ExchangeService {


    /**
     * Finds exchange from api.nbp.pl with given code and effective date of the exchange rate. Rate contains average exchange value.
     * @param code          currency code
     * @param effectiveDate date of the exchange rate formatted YYYY-MM-DD
     * @return              exchange with one rate from api.nbp.pl
     * @throws ExchangeNotFoundException throws exception when exchange wasn't found
     */
    public Exchange getExchangeByCodeAndDate(String code, String effectiveDate) throws ExchangeNotFoundException {
        RestTemplate restTemplate = new RestTemplate();

        // url to correct api.nbp.pl request
        String url = String.format("https://api.nbp.pl/api/exchangerates/rates/a/%s/%s/?format=json",code,effectiveDate);


        try{
            // return parsed request to Exchange
            return restTemplate.getForObject(url, Exchange.class);
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
     * @throws BadQuotationsProvidedException   throws when provided amount of last quotations is incorrect
     * @throws ExchangeNotFoundException        throws exception when exchange wasn't found
     */
    public Exchange getExchangeByCodeWithQuotations(String code, int quotations) throws BadQuotationsProvidedException, ExchangeNotFoundException {

        // check if number of quotations is correct
        if(quotations <=0 || quotations>255)
            throw new BadQuotationsProvidedException("Incorrect amount of quotations provided");

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
     * @throws BadQuotationsProvidedException   throws when provided amount of last quotations is incorrect
     * @throws ExchangeNotFoundException        throws exception when exchange wasn't found
     */
    public Exchange getBidAskExchangeByCodeWithQuotations(String code, int quotations) throws BadQuotationsProvidedException, ExchangeNotFoundException {
        // check if number of quotations is correct
        if(quotations <=0 || quotations>255)
            throw new BadQuotationsProvidedException("Incorrect amount of quotations provided");

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
     * @return          returns exchange with 2 rates, minimum and maximum average values
     */
    public Exchange findMinMaxRate(Exchange exchange)
    {
        float min = 100.0f;
        float max = 0.0f;
        Rate minRate = null;
        Rate maxRate = null;

        for (Rate rate : exchange.getRates())
        {
            float mid = rate.getMid().orElseThrow();
            if(mid<min)
            {
                minRate=rate;
                min = mid;
            }
            if(mid>max)
            {
                maxRate=rate;
                max = mid;
            }
        }
        minRate.setType(Optional.of("minimum average value"));
        maxRate.setType(Optional.of("maximum average value"));

        exchange.setRates(List.of(minRate,maxRate));


        return exchange;
    }


    /**
     * Finds Rate with the biggest difference between bid and ask values from given Exchange.
     * @param exchange  provided exchange with rates
     * @return          returns exchange with only 1 rates which has the biggest difference between bid and ask values
     */
    public Exchange findMajorDifference(Exchange exchange)
    {
        float difference = 00.0f;
        Rate major = null;
        for (Rate rate : exchange.getRates())
        {
            float bid = rate.getBid().orElseThrow();
            float ask = rate.getAsk().orElseThrow();
            float diffTemp = Math.abs(bid-ask);

            if(diffTemp>difference)
            {
                difference=diffTemp;
                major = rate;
            }
        }

        major.setType(Optional.of("major difference between the bid and ask rate"));

        exchange.setRates(List.of(major));

        return exchange;
    }


}
