package business;

import java.util.List;
import java.util.stream.Collectors;

import persistence.InvoiceDAO;
import persistence.InvoiceDAOGateway;
import persistence.InvoiceDTO;
import persistence.databaseKey;
import presentation.InvoiceListItem;

public class InvoiceListControl {
    
    private InvoiceDAOGateway invoiceDAOGateway;
    
    // Constructor for Dependency Injection (PROPER DIP)
    public InvoiceListControl(InvoiceDAOGateway invoiceDAOGateway) {
        this.invoiceDAOGateway = invoiceDAOGateway;
    }
    
    // Deprecated constructor - should be removed
    @Deprecated
    public InvoiceListControl() {
        // VI PHẠM DIP - chỉ để backward compatibility
        this.invoiceDAOGateway = new InvoiceDAO(new databaseKey());
    }
    
    /**
     * Lấy tất cả invoice dưới dạng presentation model (cho UI)
     * Không expose business entities ra presentation layer
     */
    public List<InvoiceListItem> getAllInvoiceItems() {
        try {
            List<InvoiceDTO> dtoList = invoiceDAOGateway.getAll();
            return convertDTOsToInvoiceListItems(dtoList);
        } catch (Exception e) {
            System.err.println("Error loading invoice items: " + e.getMessage());
            return List.of(); // Return empty list on error
        }
    }
    
    /**
     * Convert DTOs từ persistence layer sang presentation models
     */
    private List<InvoiceListItem> convertDTOsToInvoiceListItems(List<InvoiceDTO> dtoList) {
        return dtoList.stream()
            .map(this::convertDTOToInvoiceListItem)
            .collect(Collectors.toList());
    }
    
    /**
     * Convert single DTO to InvoiceListItem (presentation model)
     */
    private InvoiceListItem convertDTOToInvoiceListItem(InvoiceDTO dto) {
        InvoiceListItem item = new InvoiceListItem();
        item.id = dto.id;
        item.date = dto.date.toString();
        item.customer = dto.customer;
        item.room_id = dto.room_id;
        item.type = dto.type;
        item.unitPrice = String.valueOf(dto.unitPrice);
        item.hour = dto.hour;
        item.day = dto.day;
        
        // Calculate total price based on type
        if ("hourly".equals(dto.type)) {
            item.totalPrice = dto.unitPrice * dto.hour;
        } else if ("daily".equals(dto.type)) {
            item.totalPrice = dto.unitPrice * dto.day;
        } else {
            item.totalPrice = 0.0;
        }
        
        return item;
    }

    /**
     * Lấy tất cả invoice từ database và convert sang business objects
     */
    public List<Invoice> getAllInvoices() {
        try {
            List<InvoiceDTO> dtoList = invoiceDAOGateway.getAll();
            return convertDTOsToBusinessObjects(dtoList);
        } catch (Exception e) {
            System.err.println("Error loading invoices: " + e.getMessage());
            return List.of(); // Return empty list on error
        }
    }
    
    /**
     * Convert DTOs từ persistence layer sang business objects using Factory Pattern
     */
    private List<Invoice> convertDTOsToBusinessObjects(List<InvoiceDTO> dtoList) {
        return dtoList.stream()
            .map(InvoiceFactory::createInvoice)  // ✅ Sử dụng Factory Pattern
            .collect(Collectors.toList());
    }
    
    /**
     * Đóng connection khi không dùng nữa
     */
    public void closeConnection() {
        if (invoiceDAOGateway != null) {
            // TODO: Add closeConnection() method to InvoiceDAOGateway if needed
            // invoiceDAOGateway.closeConnection();
        }
    }
    
    /**
     * Tính tổng doanh thu
     */
    public double getTotalRevenue() {
        return getAllInvoices().stream()
            .mapToDouble(Invoice::calculateTotal)
            .sum();
    }
    
    /**
     * Đếm số lượng invoice
     */
    public int getTotalInvoiceCount() {
        return getAllInvoices().size();
    }
}
