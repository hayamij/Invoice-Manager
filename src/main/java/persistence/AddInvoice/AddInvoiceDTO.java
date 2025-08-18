package persistence.AddInvoice;

import java.util.Date;

public class AddInvoiceDTO {
    public Date date;
    public String customer;
    public String room_id;
    public double unitPrice;
    public int hour;
    public int day;
    public String type;

    // Constructor
    public AddInvoiceDTO() {}

    public AddInvoiceDTO(Date date, String customer, String room_id, 
                         double unitPrice, int hour, int day, String type) {
        this.date = date;
        this.customer = customer;
        this.room_id = room_id;
        this.unitPrice = unitPrice;
        this.hour = hour;
        this.day = day;
        this.type = type;
    }

    // Getters
    public Date getDate() { return date; }
    public String getCustomer() { return customer; }
    public String getRoom_id() { return room_id; }
    public double getUnitPrice() { return unitPrice; }
    public int getHour() { return hour; }
    public int getDay() { return day; }
    public String getType() { return type; }

}
