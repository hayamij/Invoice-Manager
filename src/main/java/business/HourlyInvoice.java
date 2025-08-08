package business;


public class HourlyInvoice extends Invoice {
    private int hour;

    public HourlyInvoice() {
        
    }
    // Getter for hour - cần thiết cho UI binding
    public int getHour() {
        return hour;
    }
    
    // Setter for hour
    public void setHour(int hour) {
        this.hour = hour;
    }
    
    @Override
    public double calculateTotal(double unitPrice, int hour) {
        if (hour <= 0) {
            return 0;
        } else if (hour >= 24 && hour <= 30) {
            return 24 * unitPrice;
        } else if (hour > 30) {
            return -1;
        } else {
            return hour * unitPrice;
        }
    }
    
    @Override
    public String type() {
        return "Hourly Invoice";
    }
}
