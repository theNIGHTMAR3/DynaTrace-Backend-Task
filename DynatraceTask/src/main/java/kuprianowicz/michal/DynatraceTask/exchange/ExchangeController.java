package kuprianowicz.michal.DynatraceTask.exchange;


import kuprianowicz.michal.DynatraceTask.exception.BadQuotationsProvidedException;
import kuprianowicz.michal.DynatraceTask.exception.ExchangeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="api/exchange")
public class ExchangeController {

    private final ExchangeService exchangeService;

    @Autowired
    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    /*@GetMapping("/{code}/{effectiveDate}")
    public Optional<Exchange> getExchangeByCodeAndDate(@PathVariable("code") String code, @PathVariable("effectiveDate") String effectiveDate)
    {
        return (exchangeService.getExchangeByCodeAndDate(code, effectiveDate));
    }*/

    @GetMapping("/{code}/{effectiveDate}")
    public ResponseEntity<Exchange> getExchangeByCodeAndDate(@PathVariable("code") String code, @PathVariable("effectiveDate") String effectiveDate) throws ExchangeNotFoundException {
        Exchange result = exchangeService.getExchangeByCodeAndDate(code, effectiveDate);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/last/{code}/{quotations}")
    public Exchange getExchangeByCodeWithQuotationsReturnMid(@PathVariable("code") String code,@PathVariable("quotations") int quotations) throws BadQuotationsProvidedException, ExchangeNotFoundException {
        Exchange result = exchangeService.getExchangeByCodeWithQuotations(code,quotations);
        return exchangeService.findMinMaxRate(result);
    }

    @GetMapping("/buy-ask/{code}/{quotations}")
    public Exchange getExchangeByCodeWithQuotationsReturnBuyAsk(@PathVariable("code") String code,@PathVariable("quotations") int quotations) throws BadQuotationsProvidedException, ExchangeNotFoundException {
        Exchange result = exchangeService.getBidAskExchangeByCodeWithQuotations(code,quotations);
        return exchangeService.findMajorDifference(result);
    }


}
