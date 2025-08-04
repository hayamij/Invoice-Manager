package persistence;

import java.math.BigDecimal;
import java.util.Date;

public class InvoiceDTO {
    public String id;
    public Date date;
    public String customer;
    public String room_id;
    public BigDecimal unitPrice;
    public double total;
    public int hour;
    public int day;
    public String type;
}
