package business;

import java.util.Date;

public class DailyInvoice extends Invoice {
    private double day;

    public DailyInvoice() {

    }

    public DailyInvoice(String id, Date date, String customer, String room_id, double unitPrice, double day) {
        super(id, date, customer, room_id, unitPrice);
        this.day = day;
    }

    // Getter for day - cần thiết cho UI binding
    public double getDay() {
        return day;
    }
    
    // Setter for day
    public void setDay(double day) {
        this.day = day;
    }
    
    @Override
    public double calculateTotal() {
        if (day <=0){
            return 0;
        } else if (day > 7) {
            return 7 * unitPrice + (day - 7) * unitPrice * 0.8; // 20% discount for days beyond 7
        } else {
            return day * unitPrice;
        }
    }
    
    @Override
    public String type() {
        return "Daily Invoice";
    }
}
