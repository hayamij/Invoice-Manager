package business.Entities;

import java.util.Date;

public class HourlyInvoice extends Invoice {
    private double hour;

    public HourlyInvoice() {
        
    }
    // Setter for hour
    public void setHour(double hour) {
        this.hour = hour;
    }
    
    @Override
    public double calculateTotal() {
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
