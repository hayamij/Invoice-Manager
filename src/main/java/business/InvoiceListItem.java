package business;

/**
 * Business Model cho Invoice List Item
 * Áp dụng Encapsulation và validation
 */
public class InvoiceListItem {
    private String id;
    private String date;
    private String customer;
    private String roomId;
    private String type;
    private String unitPrice;
    private int hour;
    private int day;
    private double totalPrice;
    
    // Constructor
    public InvoiceListItem() {}
    
    public InvoiceListItem(String id, String date, String customer, String roomId, 
                          String type, String unitPrice, int hour, int day, double totalPrice) {
        this.id = id;
        this.date = date;
        this.customer = customer;
        this.roomId = roomId;
        this.type = type;
        this.unitPrice = unitPrice;
        this.hour = hour;
        this.day = day;
        this.totalPrice = totalPrice;
    }
    
    // Getters
    public String getId() { return id; }
    public String getDate() { return date; }
    public String getCustomer() { return customer; }
    public String getRoomId() { return roomId; }
    public String getType() { return type; }
    public String getUnitPrice() { return unitPrice; }
    public int getHour() { return hour; }
    public int getDay() { return day; }
    public double getTotalPrice() { return totalPrice; }
    
    // Setters with validation
    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        this.id = id;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public void setCustomer(String customer) {
        if (customer == null || customer.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer cannot be null or empty");
        }
        this.customer = customer;
    }
    
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    
    public void setType(String type) {
        if (type == null || (!type.equals("hourly") && !type.equals("daily"))) {
            throw new IllegalArgumentException("Type must be 'hourly' or 'daily'");
        }
        this.type = type;
    }
    
    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public void setHour(int hour) {
        if (hour < 0) {
            throw new IllegalArgumentException("Hour cannot be negative");
        }
        this.hour = hour;
    }
    
    public void setDay(int day) {
        if (day < 0) {
            throw new IllegalArgumentException("Day cannot be negative");
        }
        this.day = day;
    }
    
    public void setTotalPrice(double totalPrice) {
        if (totalPrice < 0) {
            throw new IllegalArgumentException("Total price cannot be negative");
        }
        this.totalPrice = totalPrice;
    }
}
