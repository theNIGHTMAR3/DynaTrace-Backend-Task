package kuprianowicz.michal.DynatraceTask.exchange.model;

import lombok.*;

import java.util.List;


/**
 *  Model for Exchange taken from api.nbp.pl.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Exchange {

    /**
     * table from which data was taken
     */
    private Character table;

    /**
     * full name of currency in polish
     */
    private String currency;

    /**
     * short code fot the currency
     */
    private String code;

    /**
     * Rates containing exchange values of the currency
     */
    private List<Rate> rates;

}
