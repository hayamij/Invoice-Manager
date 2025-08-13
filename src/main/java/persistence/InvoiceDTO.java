package persistence;

import java.util.Date;

/**
 * Data Transfer Object for Invoice
 * Áp dụng Encapsulation với validation
 */
public class InvoiceDTO {
    public String id;
    public Date date;
    public String customer;
    public String room_id;
    public Double unitPrice;
    public int hour;
    public int day;
    public String type;
}
