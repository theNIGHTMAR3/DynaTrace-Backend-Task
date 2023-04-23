package kuprianowicz.michal.DynatraceTask.exchange;

import lombok.*;

import java.util.Date;
import java.util.Optional;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Rate {

    private Optional<String> type;
    private String no;

    private String effectiveDate;
    private Optional<Float> mid;
    private Optional<Float> bid;
    private Optional<Float> ask;

}
