package kuprianowicz.michal.DynatraceTask.exchange.model;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;


/**
 *  Model for exchange Rate taken from api.nbp.pl.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Rate {

    /**
     * number of the Rate
     */
    private String no;

    /**
     * publication date of the Rate
     */
    private LocalDate effectiveDate;

    /**
     * currency's average exchange value
     */
    private Optional<Float> mid;

    /**
     * currency's buy value
     */
    private Optional<Float> bid;

    /**
     * currency's sell value
     */
    private Optional<Float> ask;

}
