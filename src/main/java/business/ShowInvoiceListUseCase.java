package business;

import java.util.List;
import java.util.ArrayList;
import persistence.InvoiceDAO;
import persistence.InvoiceDTO;
import persistence.InvoiceDAOGateway;

public class ShowInvoiceListUseCase {
    private InvoiceDAOGateway invoiceDAO = new InvoiceDAO();
    
    public ShowInvoiceListUseCase() {}
    public ShowInvoiceListUseCase(InvoiceDAOGateway invoiceDAO) { this.invoiceDAO = invoiceDAO; }
    
    public List<InvoiceDisplayData> executeForUI() {
        List<InvoiceDisplayData> data = new ArrayList<>();
        for (InvoiceDTO dto : invoiceDAO.getAll()) {
            boolean isHourly = "h".equalsIgnoreCase(dto.getType()) || "hourly".equalsIgnoreCase(dto.getType());
            String hour = isHourly && dto.getHour() > 0 ? String.valueOf(dto.getHour()) : "";
            String day = !isHourly && dto.getDay() > 0 ? String.valueOf(dto.getDay()) : "";
            int quantity = dto.getHour() > 0 ? dto.getHour() : dto.getDay();
            
            data.add(new InvoiceDisplayData(
                String.valueOf(dto.getId()), dto.getCustomer(), String.valueOf(dto.getDate()),
                dto.getRoom_id(), dto.getType(), String.valueOf(dto.getUnitPrice()),
                hour, day, String.format("%.2f", dto.getUnitPrice() * quantity)
            ));
        }
        return data;
    }
    
    public static class InvoiceDisplayData {
        private String id, customer, date, room, type, unitPrice, hour, day, total;
        
        public InvoiceDisplayData(String id, String customer, String date, String room, 
                                  String type, String unitPrice, String hour, String day, String total) {
            this.id = id; this.customer = customer; this.date = date; this.room = room;
            this.type = type; this.unitPrice = unitPrice; this.hour = hour; this.day = day; this.total = total;
        }
        
        public String getId() { return id; }
        public String getCustomer() { return customer; }
        public String getDate() { return date; }
        public String getRoom() { return room; }
        public String getType() { return type; }
        public String getUnitPrice() { return unitPrice; }
        public String getHour() { return hour; }
        public String getDay() { return day; }
        public String getTotal() { return total; }
    }
}
