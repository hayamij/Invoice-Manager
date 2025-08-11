package presentation;

public class InvoiceViewItem {
    public int stt;
    public String id;
    public String date;
    public String customer;
    public String room_id;
    public String type;
    public double total;


    // PropertyValueFactory needs getters (seriously)
    public int getStt() { return stt; }
    public String getId() { return id; }
    public String getDate() { return date; }
    public String getCustomer() { return customer; }
    public String getRoom_id() { return room_id; }
    public String getType() { return type; }
    public double getTotal() { return total; }
}
