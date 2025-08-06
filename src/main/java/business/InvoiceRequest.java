package business;

/**
 * Request object for invoice creation/update
 */
public class InvoiceRequest {
    private String customer;
    private String roomId;
    private double unitPrice;
    private String type; // "hourly" or "daily"
    private int quantity; // hour or day based on type
    
    // Constructors
    public InvoiceRequest() {}
    
    public InvoiceRequest(String customer, String roomId, double unitPrice, String type, int quantity) {
        this.customer = customer;
        this.roomId = roomId;
        this.unitPrice = unitPrice;
        this.type = type;
        this.quantity = quantity;
    }
    
    // Getters and Setters
    public String getCustomer() { return customer; }
    public void setCustomer(String customer) { this.customer = customer; }
    
    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
    
    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    
    @Override
    public String toString() {
        return "InvoiceRequest{" +
                "customer='" + customer + '\'' +
                ", roomId='" + roomId + '\'' +
                ", unitPrice=" + unitPrice +
                ", type='" + type + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
