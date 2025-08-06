package business;

import java.util.Date;

/**
 * Search criteria for invoice filtering
 */
public class SearchCriteria {
    private String customerName;
    private String roomId;
    private String type;
    private Date fromDate;
    private Date toDate;
    private Double minAmount;
    private Double maxAmount;
    
    // Constructors
    public SearchCriteria() {}
    
    // Getters and Setters
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    
    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public Date getFromDate() { return fromDate; }
    public void setFromDate(Date fromDate) { this.fromDate = fromDate; }
    
    public Date getToDate() { return toDate; }
    public void setToDate(Date toDate) { this.toDate = toDate; }
    
    public Double getMinAmount() { return minAmount; }
    public void setMinAmount(Double minAmount) { this.minAmount = minAmount; }
    
    public Double getMaxAmount() { return maxAmount; }
    public void setMaxAmount(Double maxAmount) { this.maxAmount = maxAmount; }
    
    @Override
    public String toString() {
        return "SearchCriteria{" +
                "customerName='" + customerName + '\'' +
                ", roomId='" + roomId + '\'' +
                ", type='" + type + '\'' +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", minAmount=" + minAmount +
                ", maxAmount=" + maxAmount +
                '}';
    }
}
