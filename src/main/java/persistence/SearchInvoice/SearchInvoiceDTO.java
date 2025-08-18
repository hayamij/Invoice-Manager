package persistence.SearchInvoice;

import java.util.Date;

public class SearchInvoiceDTO {
    public String id;
    public Date date;
    public String customer;
    public String room_id;
    public Double unitPrice;
    public int hour;
    public int day;
    public String type;

    // Constructor
    public SearchInvoiceDTO() {}
    
    // Getters
    public String getId() { return id; }
    public Date getDate() { return date; }
    public String getCustomer() { return customer; }
    public String getRoom_id() { return room_id; }
    public double getUnitPrice() { return unitPrice; }
    public int getHour() { return hour; }
    public int getDay() { return day; }
    public String getType() { return type; }
    // Setters
    public void setId(String id) { this.id = id; }
    public void setDate(Date date) { this.date = date; }
    public void setCustomer(String customer) { this.customer = customer; }
    public void setRoom_id(String room_id) { this.room_id = room_id; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    public void setHour(int hour) { this.hour = hour; }
    public void setDay(int day) { this.day = day; }
    public void setType(String type) { this.type = type; }
}
