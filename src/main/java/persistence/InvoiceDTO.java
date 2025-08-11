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

    // Constructor
    // public InvoiceDTO() {}
    
    // public InvoiceDTO(String id, Date date, String customer, String room_id, 
    //                  double unitPrice, int hour, int day, String type) {
    //     this.id = id;
    //     this.date = date;
    //     this.customer = customer;
    //     this.room_id = room_id;
    //     this.unitPrice = unitPrice;
    //     this.hour = hour;
    //     this.day = day;
    //     this.type = type;
    // }
    
    // // Getters
    // public String getId() { return id; }
    // public Date getDate() { return date; }
    // public String getCustomer() { return customer; }
    // public String getRoom_id() { return room_id; }
    // public double getUnitPrice() { return unitPrice; }
    // public int getHour() { return hour; }
    // public int getDay() { return day; }
    // public String getType() { return type; }
    
    // // Setters with validation
    // public void setId(String id) {
    //     if (id == null || id.trim().isEmpty()) {
    //         throw new IllegalArgumentException("ID cannot be null or empty");
    //     }
    //     this.id = id;
    // }
    
    // public void setDate(Date date) {
    //     this.date = date;
    // }
    
    // public void setCustomer(String customer) {
    //     if (customer == null || customer.trim().isEmpty()) {
    //         throw new IllegalArgumentException("Customer cannot be null or empty");
    //     }
    //     this.customer = customer;
    // }
    
    // public void setRoom_id(String room_id) {
    //     this.room_id = room_id;
    // }
    
    // public void setUnitPrice(double unitPrice) {
    //     if (unitPrice < 0) {
    //         throw new IllegalArgumentException("Unit price cannot be negative");
    //     }
    //     this.unitPrice = unitPrice;
    // }
    
    // public void setHour(int hour) {
    //     if (hour < 0) {
    //         throw new IllegalArgumentException("Hour cannot be negative");
    //     }
    //     this.hour = hour;
    // }
    
    // public void setDay(int day) {
    //     if (day < 0) {
    //         throw new IllegalArgumentException("Day cannot be negative");
    //     }
    //     this.day = day;
    // }
    
    // public void setType(String type) {
    //     if (type == null || (!type.equals("hourly") && !type.equals("daily"))) {
    //         throw new IllegalArgumentException("Type must be 'hourly' or 'daily'");
    //     }
    //     this.type = type;
    // }
}
