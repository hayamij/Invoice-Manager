package business;

import java.util.List;
import java.util.ArrayList;
import persistence.InvoiceDAO;
import persistence.InvoiceDTO;
import persistence.InvoiceDAOGateway;

public class SearchInvoiceUseCase {
    private InvoiceDAOGateway invoiceDAO = new InvoiceDAO();
    
    public SearchInvoiceUseCase() {}
    public SearchInvoiceUseCase(InvoiceDAOGateway invoiceDAO) { this.invoiceDAO = invoiceDAO; }
    
    public List<InvoiceSearchResult> executeForUI(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) searchTerm = "";
        String term = searchTerm.toLowerCase().trim();
        
        List<InvoiceSearchResult> data = new ArrayList<>();
        for (InvoiceDTO dto : invoiceDAO.getAll()) {
            if (term.isEmpty() ||
                String.valueOf(dto.getId()).contains(term) ||
                dto.getCustomer().toLowerCase().contains(term) ||
                dto.getRoom_id().toLowerCase().contains(term) ||
                dto.getType().toLowerCase().contains(term) ||
                String.valueOf(dto.getUnitPrice()).contains(term)) {
                
                boolean isHourly = "h".equalsIgnoreCase(dto.getType()) || "hourly".equalsIgnoreCase(dto.getType());
                String hour = isHourly && dto.getHour() > 0 ? String.valueOf(dto.getHour()) : "";
                String day = !isHourly && dto.getDay() > 0 ? String.valueOf(dto.getDay()) : "";
                int quantity = dto.getHour() > 0 ? dto.getHour() : dto.getDay();
                
                String matchReason = term.isEmpty() ? "All records" :
                    String.valueOf(dto.getId()).contains(term) ? "ID match" :
                    dto.getCustomer().toLowerCase().contains(term) ? "Customer match" :
                    dto.getRoom_id().toLowerCase().contains(term) ? "Room match" :
                    dto.getType().toLowerCase().contains(term) ? "Type match" : "Price match";
                
                data.add(new InvoiceSearchResult(
                    String.valueOf(dto.getId()), dto.getCustomer(), String.valueOf(dto.getDate()),
                    dto.getRoom_id(), dto.getType(), String.valueOf(dto.getUnitPrice()),
                    hour, day, String.format("%.2f", dto.getUnitPrice() * quantity), matchReason
                ));
            }
        }
        return data;
    }
    
    public static class InvoiceSearchResult {
        private String id, customer, date, room, type, unitPrice, hour, day, total, matchReason;
        
        public InvoiceSearchResult(String id, String customer, String date, String room, 
                                   String type, String unitPrice, String hour, String day, 
                                   String total, String matchReason) {
            this.id = id; this.customer = customer; this.date = date; this.room = room;
            this.type = type; this.unitPrice = unitPrice; this.hour = hour; this.day = day; 
            this.total = total; this.matchReason = matchReason;
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
        public String getMatchReason() { return matchReason; }
    }
}