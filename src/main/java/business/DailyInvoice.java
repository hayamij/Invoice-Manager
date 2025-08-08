package business;

public class DailyInvoice extends Invoice {
    private int day;

    public DailyInvoice() {

    }

    // Getter for day - cần thiết cho UI binding
    public int getDay() {
        return day;
    }
    
    // Setter for day
    public void setDay(int day) {
        this.day = day;
    }
    
    @Override
    public double calculateTotal(double unitPrice, int day) {
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
