package business;

import java.util.Date;

public class HourlyInvoice extends Invoice {
    private int hour;

    public HourlyInvoice(String id, Date date, String customer, String room_id, double unitPrice, int hour) {
        super(id, date, customer, room_id, unitPrice);
        this.hour = hour;
    }
    @Override
    protected double calculateTotal() {
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
    protected String type() {
        return "Hourly Invoice";
    }
}
