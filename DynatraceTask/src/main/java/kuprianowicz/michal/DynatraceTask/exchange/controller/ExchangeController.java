package kuprianowicz.michal.DynatraceTask.exchange.controller;


import kuprianowicz.michal.DynatraceTask.exception.BadInputException;
import kuprianowicz.michal.DynatraceTask.exception.ExchangeNotFoundException;
import kuprianowicz.michal.DynatraceTask.exchange.service.ExchangeService;
import kuprianowicz.michal.DynatraceTask.exchange.model.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path="api/exchanges")
public class ExchangeController {


    /**
     * Service for managing exchanges
     */
    private final ExchangeService exchangeService;


    /**
     * @param exchangeService   Service for managing exchanges
     */
    @Autowired
    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    /**
     * @param code              currency code
     * @param effectiveDate     date of currency rate
     * @return                  float of average exchange rate
     * @throws ExchangeNotFoundException    throws when provided input is invalid
     * @throws BadInputException            throws exception when exchange wasn't found
     */
    @GetMapping("/{code}/{effectiveDate}")
    public ResponseEntity<Float> getExchangeByCodeAndDate(@PathVariable("code") String code, @PathVariable("effectiveDate") String effectiveDate) throws ExchangeNotFoundException, BadInputException {
        float result = exchangeService.getExchangeByCodeAndDate(code, effectiveDate);
        return ResponseEntity.ok(result);
    }


    /**
     * @param code          currency code
     * @param quotations    number of last quotations N (N <= 255)
     * @return              Map<String,Float> containing min and max exchange rate
     * @throws BadInputException            throws when provided input is invalid
     * @throws ExchangeNotFoundException    throws exception when exchange wasn't found
     */
    @GetMapping("/{code}/last/{quotations}")
    public Map<String,Float> getExchangeByCodeWithQuotationsReturnMid(@PathVariable("code") String code, @PathVariable("quotations") int quotations) throws BadInputException, ExchangeNotFoundException {
        Exchange result = exchangeService.getExchangeByCodeWithQuotations(code,quotations);
        return exchangeService.findMinMaxRate(result);
    }


    /**
     * @param code          currency code
     * @param quotations    number of last quotations N (N <= 255)
     * @return              float of the biggest difference between bid and ask rates
     * @throws BadInputException            throws when provided input is invalid
     * @throws ExchangeNotFoundException    throws exception when exchange wasn't found
     */
    @GetMapping("/{code}/bid-ask/{quotations}")
    public float getExchangeByCodeWithQuotationsReturnBuyAsk(@PathVariable("code") String code,@PathVariable("quotations") int quotations) throws BadInputException, ExchangeNotFoundException {
        Exchange result = exchangeService.getBidAskExchangeByCodeWithQuotations(code,quotations);
        return exchangeService.findMajorDifference(result);
    }


}
