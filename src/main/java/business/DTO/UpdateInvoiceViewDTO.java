package business.DTO;

import java.util.Date;

public class UpdateInvoiceViewDTO {
    public String id;
    public String customer;
    public String room_id;
    public Double unitPrice;
    public Date date;
    public int hour; // For hourly invoices
    public int day; // For daily invoices
    public String type; // "Hourly Invoice" or "Daily Invoice"
}
