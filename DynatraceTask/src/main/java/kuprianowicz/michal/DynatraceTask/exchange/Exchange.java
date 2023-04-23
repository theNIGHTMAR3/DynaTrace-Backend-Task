package kuprianowicz.michal.DynatraceTask.exchange;

import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Exchange {

    private Character table;

    private String currency;

    private String code;

    private List<Rate> rates;

}
