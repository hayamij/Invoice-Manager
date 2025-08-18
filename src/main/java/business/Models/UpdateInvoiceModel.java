package business.Models;

import java.util.Date;

public class UpdateInvoiceModel {
    public String id;
    public String customer;
    public String room_id;
    public double unitPrice;
    public Date date;
    public int hour; // For hourly invoices
    public int day; // For daily invoices
    public String type; // "Hourly Invoice" or "Daily Invoice"
}
