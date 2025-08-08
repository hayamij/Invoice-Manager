package business;

import java.util.Date;

public abstract class Invoice {
    protected String id;
    protected Date date;
    protected String customer;
    protected String room_id;
    protected double unitPrice;

    protected Invoice(String id, Date date, String customer, String room_id, double unitPrice) {
        this.id = id;
        this.date = date;
        this.customer = customer;
        this.room_id = room_id;
        this.unitPrice = unitPrice;
    }
    
    public Invoice() {
        // Constructor rỗng cho Invoice
    }
    
    // Public methods để Presentation layer có thể truy cập
    public abstract double calculateTotal(double unitPrice, int hour);
    public abstract String type();

    // Getters and Setters
    public String getId() { return id; }
    public Date getDate() { return date; }
    public String getCustomer() { return customer; }
    public String getRoomId() { return room_id; }
    public double getUnitPrice() { return unitPrice; }

    public void setId(String id) { this.id = id; }
    public void setDate(Date date) { this.date = date; }
    public void setCustomer(String customer) { this.customer = customer; }
    public void setRoomId(String room_id) { this.room_id = room_id; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
}
