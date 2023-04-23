package kuprianowicz.michal.DynatraceTask.exchange;

import java.util.Date;

public class BuyAskRate {

    private String no;


    private Date effectiveDate;

    private float bid;
    private float ask;

    public BuyAskRate(String no, Date effectiveDate, float bid, float ask) {
        //DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //String stringDate= formatter.format(effectiveDate);

        this.no = no;
        this.effectiveDate = effectiveDate;
        this.bid = bid;
        this.ask = ask;
    }
}
